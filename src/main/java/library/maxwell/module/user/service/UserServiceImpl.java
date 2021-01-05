package library.maxwell.module.user.service;

import library.maxwell.config.security.auth.JwtTokenProvider;
import library.maxwell.config.security.auth.UserPrincipal;
import library.maxwell.module.user.dto.JwtAuthenticationResponse;
import library.maxwell.module.user.dto.LoginDto;
import library.maxwell.module.user.dto.RegistrationDto;
import library.maxwell.module.user.entity.LevelEntity;
import library.maxwell.module.user.entity.LevelName;
import library.maxwell.module.user.entity.UserEntity;
import library.maxwell.module.user.repository.LevelRepository;
import library.maxwell.module.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private LevelRepository levelRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    JwtTokenProvider tokenProvider;

    public void saveUser(UserEntity user) {
        userRepository.save(user);
    }

    @Override
    public RegistrationDto createNewUser(RegistrationDto registrationDto) {
        UserEntity userEntity = new UserEntity();

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

       LevelEntity levelEntity = levelRepository.findByName(LevelName.ROLE_USER)
               .get();
       userEntity.setRoles(Collections.singleton(levelEntity));

       //Save user
        saveUser(userEntity);

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

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.generateToken(authentication);
        return new JwtAuthenticationResponse(jwt);
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
		Optional<UserEntity> userEntity= userRepository.findById(userPrincipal.getId());
		return userEntity;
	}

	
}
