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

import java.util.Base64;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

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
        userBalanceEntity.setUserEntity(userEntity);
        userDetailRepository.save(userDetailEntity);
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
        UserBalanceEntity userBalanceEntity = userBalanceRepository.findByUserEntityUserId(findUser.getUserId());

        userInfo.setEmail(findUser.getEmail());
        userInfo.setActiveRole(findUser.getActiveRole());
        userInfo.setActiveBalance(userBalanceEntity.getNominal());

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.generateToken(authentication);
        return new JwtAuthenticationResponse(jwt, userInfo);
    }

    @Override
    public UserEntity getProfiles(UserPrincipal userPrincipal) {
        //Get current logged in user
    	System.out.println(userPrincipal.getId());
        System.out.println(userPrincipal.getAuthorities());
        System.out.println(userPrincipal.getEmail());
        UserEntity userEntity = userRepository.findByEmail(userPrincipal.getEmail())
                .get();
        return userEntity;
    }

	@Override
	public Optional<UserEntity> getId(UserPrincipal userPrincipal) {
		Optional<UserEntity> userEntity = userRepository.findById(userPrincipal.getId());
		return userEntity;
	}

    @Override
    public UpdateProfileDto updateProfile(UserPrincipal userPrincipal, UpdateProfileDto profileDto) {

        UserEntity user = userRepository.findById(userPrincipal.getId()).get();
        UserDetailEntity userDetail = userDetailRepository.findByUserEntityUserId(user.getUserId());

        //Upload image
        try {
            //Covert Base64 to bytes
            byte[] profileImage = Base64.getMimeDecoder().decode(profileDto.getImg());

            Map uploadResult = cloudinary.upload(profileImage,
                    ObjectUtils.asMap("resourcetype", "auto"));

            userDetail.setImg(uploadResult.get("url").toString());

            userDetail.setFirstName(profileDto.getFirstName());
            userDetail.setLastName(profileDto.getLastName());
            userDetail.setAddress(profileDto.getAddress());
            userDetail.setPhoneNumber(profileDto.getPhoneNumber());
            userDetail.setDateOfBirth(profileDto.getDateOfBirth());

            userDetailRepository.save(userDetail);
            profileDto.setImg(userDetail.getImg());

        } catch (Exception e) {
            e.getMessage();
        }
        return profileDto;
    }
}
