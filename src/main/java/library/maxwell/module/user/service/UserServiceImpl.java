package library.maxwell.module.user.service;

import com.cloudinary.utils.ObjectUtils;
import library.maxwell.config.CloudinaryConfig;
import library.maxwell.config.security.auth.JwtTokenProvider;
import library.maxwell.config.security.auth.UserPrincipal;
import library.maxwell.module.topup.entity.UserBalanceEntity;
import library.maxwell.module.topup.repository.UserBalanceRepository;
import library.maxwell.module.user.dto.*;
import library.maxwell.module.user.entity.LevelEntity;
import library.maxwell.module.user.entity.LevelName;
import library.maxwell.module.user.entity.UserDetailEntity;
import library.maxwell.module.user.entity.UserEntity;
import library.maxwell.module.user.repository.LevelRepository;
import library.maxwell.module.user.repository.UserDetailRepository;
import library.maxwell.module.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
    private UserRepository userRepository;

    @Autowired
    private UserDetailRepository userDetailRepository;

    @Autowired
    private UserBalanceRepository userBalanceRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private LevelRepository levelRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    JwtTokenProvider tokenProvider;

    @Autowired
    private CloudinaryConfig cloudinary;

    @Autowired
    private EmailService emailService;

    public void saveUser(UserEntity user) {
        userRepository.save(user);
    }

    @Override
    public RegistrationDto createNewUser(RegistrationDto registrationDto) {
        UserEntity userEntity = new UserEntity();
        UserDetailEntity userDetailEntity = new UserDetailEntity();
        UserBalanceEntity userBalanceEntity = new UserBalanceEntity();

        //Lookup by registered email
        Boolean existByEmail = userRepository.existsByEmail(registrationDto.getEmail());

        if (existByEmail) {
           return null;
        }

        if (!(registrationDto.getPassword().equals(registrationDto.getConfirmPassword()))) {
            registrationDto = null;
            return registrationDto;
        }

        //Set encode password
        String encodedPassword = passwordEncoder.encode(registrationDto.getPassword());
        userEntity.setPassword(encodedPassword);
        userEntity.setEmail(registrationDto.getEmail());
        userEntity.setActiveRole(String.valueOf(LevelName.ROLE_USER));

       LevelEntity levelEntity = levelRepository.findByName(LevelName.ROLE_USER)
               .get();
       userEntity.setRoles(Collections.singleton(levelEntity));

       //Save user
        saveUser(userEntity);
        //Set initial detail and balance entity
        userDetailEntity.setUserEntity(userEntity);
        userDetailEntity.setImg("https://www.oneworldplayproject.com/wp-content/uploads/2016/03/avatar-1024x1024.jpg");
        userDetailEntity.setFirstName(registrationDto.getFirstName());
        userDetailEntity.setLastName(registrationDto.getLastName());

        userBalanceEntity.setUserEntity(userEntity);
        userDetailRepository.save(userDetailEntity);
        userBalanceEntity.setNominal((double) 0);
        userBalanceRepository.save(userBalanceEntity);

        return registrationDto;
    }

    @Override
    public JwtAuthenticationResponse authenticateUser(LoginDto loginDto) {
        JwtAuthenticationResponse response = new JwtAuthenticationResponse();

        //Lookup by registered email
        Boolean existByEmail = userRepository.existsByEmail(loginDto.getEmail());
        UserEntity findUser = userRepository.findByEmail(loginDto.getEmail())
                .get();

        if (!existByEmail) {
            response = null;
            return response;
        }

        String rawPassword = loginDto.getPassword();
        String encodedPassword = findUser.getPassword();
        boolean isPasswordMatch = passwordEncoder.matches(rawPassword, encodedPassword);

        if (!isPasswordMatch) {
            response = null;
            return response;
        }
        
        

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getEmail(),
                        loginDto.getPassword()
                )
        );

        //Set user info
        UserInfoDto userInfo = new UserInfoDto();
        userInfo.setEmail(findUser.getEmail());
        userInfo.setActiveRole(findUser.getActiveRole());

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.generateToken(authentication);
        return new JwtAuthenticationResponse(jwt, userInfo);
    }

    @Override
	public UserDetailDto getProfiles(UserPrincipal userPrincipal) {
		UserEntity userEntity = userRepository.findById(userPrincipal.getId()).get();
		UserDetailEntity userDetail = userDetailRepository.findByUserEntityUserId(userPrincipal.getId());

		UserDetailDto userDetailDto = new UserDetailDto();

		userDetailDto.setEmail(userEntity.getEmail());
		userDetailDto.setFirstName(userDetail.getFirstName());
		userDetailDto.setLastName(userDetail.getLastName());
		userDetailDto.setAddress(userDetail.getAddress());
		userDetailDto.setImg(userDetail.getImg());
		userDetailDto.setPhoneNumber(userDetail.getPhoneNumber());
		userDetailDto.setDateOfBirth(userDetail.getDateOfBirth());

		return userDetailDto;
	}

    
    
	@Override
	public Optional<UserEntity> getId(UserPrincipal userPrincipal) {	
		Optional<UserEntity> userEntity= userRepository.findById(userPrincipal.getId());
		return userEntity;
	}

	@Override
	public List<UserEntity> getUser(Integer id) {
		List<UserEntity> userEntities=userRepository.findUser(id);
		return userEntities;
	}
	
	

	@Override
    public UpdateProfileDto updateProfile(UserPrincipal userPrincipal, UpdateProfileDto profileDto) {

        UserEntity user = userRepository.findById(userPrincipal.getId()).get();
        UserDetailEntity userDetail = userDetailRepository.findByUserEntityUserId(user.getUserId());

        System.out.println(profileDto.getImg());
        //Upload image
        try {
            //Covert Base64 to bytes
            byte[] profileImage = Base64.getMimeDecoder().decode(profileDto.getImg());

            Map uploadResult = cloudinary.upload(profileImage,
                    ObjectUtils.asMap("resourcetype", "auto"));

            userDetail.setImg(uploadResult.get("url").toString());

        } catch (Exception e) {
            e.getMessage();
        }

        userDetail.setFirstName(profileDto.getFirstName());
        userDetail.setLastName(profileDto.getLastName());
        userDetail.setAddress(profileDto.getAddress());
        userDetail.setPhoneNumber(profileDto.getPhoneNumber());
        userDetail.setDateOfBirth(profileDto.getDateOfBirth());

        userDetailRepository.save(userDetail);
        profileDto.setImg(userDetail.getImg());

        return profileDto;
    }

	@Override
	public String getRole(Integer id) {
		String userEntity=userRepository.findActiveRoleByUserId(id).getActiveRole();
		return userEntity;
	}

	@Override
	public String getName(Integer id) {
		String userString=userDetailRepository.findFirst(id);
		String userString2=userDetailRepository.findLast(id);
		String nama=userString+" "+userString2;
		return nama;
	}
	
	@Override
	public String getName2(Integer id) {
		String userString=userDetailRepository.findFirst(id);
		String userString2=userDetailRepository.findLast(id);
		String nama=userString+" "+userString2;
		return nama;
	}

    @Override
    public List<UserManageDto> getUserManagement(UserPrincipal userPrincipal) {
        List<UserEntity> userEntities = userRepository.findAll();

        List<UserManageDto> userManageDtos = new ArrayList<>();

        for (UserEntity userEntity : userEntities) {
            UserManageDto userManageDto = new UserManageDto();
            UserDetailEntity userDetailEntity = userDetailRepository.findByUserEntityUserId(userEntity.getUserId());

            userManageDto.setId(userEntity.getUserId());
            userManageDto.setFullName(userDetailEntity.getFirstName() + " " + userDetailEntity.getLastName());
            userManageDto.setImg(userDetailEntity.getImg());
            userManageDto.setEmail(userEntity.getEmail());
            if (userEntity.getStatus()) {
                userManageDto.setStatus("active");
            } else {
                userManageDto.setStatus("inactive");
            }
            userManageDto.setActiveRole(userEntity.getActiveRole());
            userManageDto.setRegisteredRoles(userEntity.getRoles());

            userManageDtos.add(userManageDto);
        }

        return userManageDtos;
    }

    @Override
    public AddRoleDto addRoleUser(UserPrincipal userPrincipal, Integer id, AddRoleDto role) {

        LevelEntity levelEntity = levelRepository.findByName(LevelName.valueOf(role.getRole()))
                .get();

        UserEntity userEntity = userRepository.findByUserId(id);

        Set<LevelEntity> roles = userEntity.getRoles();
        roles.add(levelEntity);

        userEntity.setRoles(roles);

        saveUser(userEntity);

        return role;

    }

    @Override
    public AddRoleDto changeRoleUser(UserPrincipal userPrincipal, Integer id, AddRoleDto role) {

        LevelEntity levelEntity = levelRepository.findByName(LevelName.valueOf(role.getRole()))
                .get();
        UserEntity userEntity = userRepository.findByUserId(id);

        Set<LevelEntity> roles = userEntity.getRoles();

        if(roles.contains(levelEntity)) {
            userEntity.setActiveRole(levelEntity.getName().toString());
        } else {
            return null;
        }

        saveUser(userEntity);

        return role;
    }

    @Override
    public ChangePasswordDto changePassword(UserPrincipal userPrincipal, ChangePasswordDto changePasswordDto) {

        UserEntity loggedInUser = userRepository.findByUserId(userPrincipal.getId());

        String oldPassword = changePasswordDto.getOldPassword();
        Boolean isPasswordMatch = passwordEncoder.matches(oldPassword, loggedInUser.getPassword());

        if (isPasswordMatch) {
            String encodedPassword = passwordEncoder.encode(changePasswordDto.getNewPassword());
            loggedInUser.setPassword(encodedPassword);

            saveUser(loggedInUser);

            return changePasswordDto;
        }

        return null;

    }

    @Override
    public ForgotPasswordDto forgotPassword(ForgotPasswordDto forgotPasswordDto) {
        UserEntity userEntity = userRepository.findByEmail(forgotPasswordDto.getEmail())
                .orElseThrow(() -> new RuntimeException());

        UUID randomUUID = UUID.randomUUID();
        String generatedPassword = (randomUUID.toString()).substring(0,11).replace("-", "");

        String encodedPassword = passwordEncoder.encode(generatedPassword);
        userEntity.setPassword(encodedPassword);
        saveUser(userEntity);

        //Send email
        emailService.sendMail(forgotPasswordDto.getEmail(),
                "New Password for Maxwell Library",
                "Please login with your new password : " + generatedPassword);

        return forgotPasswordDto;
    }


}
