package library.maxwell.module.user.service;

import library.maxwell.config.security.auth.UserPrincipal;
import library.maxwell.module.user.dto.JwtAuthenticationResponse;
import library.maxwell.module.user.dto.LoginDto;
import library.maxwell.module.user.dto.RegistrationDto;
import library.maxwell.module.user.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public interface UserService {

    RegistrationDto createNewUser(RegistrationDto registrationDto);

    JwtAuthenticationResponse authenticateUser(LoginDto loginDto);

    UserEntity getProfiles(UserPrincipal userPrincipal);
}
