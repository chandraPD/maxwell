package library.maxwell.module.user.service;

import library.maxwell.config.security.auth.UserPrincipal;
import library.maxwell.module.user.dto.*;
import org.springframework.stereotype.Component;

@Component
public interface UserService {

    RegistrationDto createNewUser(RegistrationDto registrationDto);

    JwtAuthenticationResponse authenticateUser(LoginDto loginDto);

    UserDetailDto getProfiles(UserPrincipal userPrincipal);

    UpdateProfileDto updateProfile(UserPrincipal userPrincipal, UpdateProfileDto updateProfileDto);
}
