package library.maxwell.module.donate.dto;


import library.maxwell.module.user.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DonateDto {

    private String email;
    private String name;
    private String donationType;
    private Integer totalBook;
    private String statusDonate;

}
