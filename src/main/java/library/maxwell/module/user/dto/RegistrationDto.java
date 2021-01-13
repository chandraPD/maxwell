package library.maxwell.module.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationDto {

    private String firstName;

    private String lastName;

	private String email;

    private String password;

    private String confirmPassword;

}
