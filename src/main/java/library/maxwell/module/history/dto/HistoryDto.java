package library.maxwell.module.history.dto;

import library.maxwell.module.user.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HistoryDto {

    private UserEntity userEntity;
    private LocalDateTime borrowedDate;
    private Integer borrowedBookId;
    private Boolean status = true;
    private String title;
    private String description;

}
