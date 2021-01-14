package library.maxwell.module.user.service;

import library.maxwell.config.security.auth.UserPrincipal;
import library.maxwell.module.user.dto.*;
import library.maxwell.module.user.entity.UserEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public interface UserService {

    RegistrationDto createNewUser(RegistrationDto registrationDto);

    JwtAuthenticationResponse authenticateUser(LoginDto loginDto);    
    
    Optional<UserEntity> getId(UserPrincipal userPrincipal);
    
    List<UserEntity> getUser(Integer id);
    
    UserDetailDto getProfiles(UserPrincipal userPrincipal);

    UpdateProfileDto updateProfile(UserPrincipal userPrincipal, UpdateProfileDto updateProfileDto);
    
    String getRole(Integer id);
    
    String getName(Integer id);

    List<UserManageDto> getUserManagement(UserPrincipal userPrincipal);

	String getName2(Integer id);

    AddRoleDto addRoleUser(UserPrincipal userPrincipal, Integer id, AddRoleDto role);

    AddRoleDto changeRoleUser(UserPrincipal userPrincipal, Integer id, AddRoleDto role);

    ChangePasswordDto changePassword(UserPrincipal userPrincipal, ChangePasswordDto changePasswordDto);

    ForgotPasswordDto forgotPassword(ForgotPasswordDto forgotPasswordDto);

}
