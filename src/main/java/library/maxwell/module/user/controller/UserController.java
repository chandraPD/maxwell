package library.maxwell.module.user.controller;

import library.maxwell.config.security.auth.CurrentUser;
import library.maxwell.config.security.auth.UserPrincipal;
import library.maxwell.module.user.dto.*;
import library.maxwell.module.user.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserServiceImpl userService;
    
    private StatusMessageDto result = new StatusMessageDto();

    //Regis new user
    @PostMapping("/auth/register")
    public ResponseEntity<?> createNewUser(@RequestBody RegistrationDto registrationDto) {

        try {
            RegistrationDto registeredUser = userService.createNewUser(registrationDto);
            result.setStatus(200);
            result.setMessages("registration success");
            result.setData(registeredUser);

            if (registeredUser == null) {
                result.setStatus(HttpStatus.BAD_REQUEST.value());
                result.setMessages("either email is taken or password is not valid");
                result.setData(null);

                return ResponseEntity.badRequest().body(result);
            }

            return ResponseEntity.ok(result);

        } catch (Exception e) {
            StatusMessageDto error = new StatusMessageDto(500,e.getMessage(), null);
            return ResponseEntity.status(500).body(error);
        }

    }

    //Login user
    @PostMapping("/auth/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginDto loginDto) {

        try {

            JwtAuthenticationResponse token = userService.authenticateUser(loginDto);

            result.setStatus(200);
            result.setMessages("login success");
            result.setData(token);

            if (token == null) {
                result.setStatus(HttpStatus.BAD_REQUEST.value());
                result.setMessages("either account not found or password is not valid");
                result.setData(null);

                return ResponseEntity.badRequest().body(result);
            }

            return ResponseEntity.ok(result);

        } catch (Exception e) {
            StatusMessageDto error = new StatusMessageDto(500,e.getMessage(), null);
            return ResponseEntity.status(500).body(error);
        }

    }

    //Check profiles
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @GetMapping("/profile")
    public ResponseEntity<?> getProfiles(@CurrentUser UserPrincipal userPrincipal) {
        UserDetailDto user = userService.getProfiles(userPrincipal);
        return ResponseEntity.ok(user);
    }

    //Update profile
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @PostMapping("/profile")
    public ResponseEntity<?> updateProfile(@CurrentUser UserPrincipal userPrincipal,
                                           @RequestBody UpdateProfileDto updateProfileDto) {
        UpdateProfileDto updatedProfile = userService.updateProfile(userPrincipal, updateProfileDto);

        result.setStatus(200);
        result.setMessages("profile updated");
        result.setData(updatedProfile);

        return ResponseEntity.ok(result);
    }
}
