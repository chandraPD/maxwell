package library.maxwell.module.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDetailDto {

    private String email;

    private String firstName;

    private String lastName;

    private String address;

    private String img;

    private String phoneNumber;

    private Date dateOfBirth;

}
