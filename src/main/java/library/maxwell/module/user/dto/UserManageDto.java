package library.maxwell.module.user.dto;

import library.maxwell.module.user.entity.LevelEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserManageDto {

    private Integer id;

    private String fullName;

    private String img;

    private String email;

    private String status;

    private String activeRole;

    private Set<LevelEntity> registeredRoles;
}
