package library.maxwell.module.user.service;

import library.maxwell.config.security.auth.UserPrincipal;
import library.maxwell.module.user.dto.JwtAuthenticationResponse;
import library.maxwell.module.user.dto.LoginDto;
import library.maxwell.module.user.dto.RegistrationDto;
import library.maxwell.module.user.entity.UserEntity;

import java.util.List;
import java.util.Optional;

import library.maxwell.module.user.dto.*;
import org.springframework.stereotype.Component;

@Component
public interface UserService {

    RegistrationDto createNewUser(RegistrationDto registrationDto);

    JwtAuthenticationResponse authenticateUser(LoginDto loginDto);    
    
    Optional<UserEntity> getId(UserPrincipal userPrincipal);
    
    List<UserEntity> getUser(Integer id);
    
    UserDetailDto getProfiles(UserPrincipal userPrincipal);

    UpdateProfileDto updateProfile(UserPrincipal userPrincipal, UpdateProfileDto updateProfileDto);
    
    String getRole(Integer id);
}
