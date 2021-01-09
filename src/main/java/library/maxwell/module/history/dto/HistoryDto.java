package library.maxwell.module.history.dto;

import library.maxwell.module.book.entity.BookDetailEntity;
import library.maxwell.module.book.entity.CategoryEntity;
import library.maxwell.module.user.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HistoryDto {

    private Integer historyId;
    private UserEntity userIdEntity;
    private BookDetailEntity bookDetailEntity;
    private LocalDateTime returnedDate;
    private LocalDateTime borrowedDate;
    private String statusBook;
    private CategoryEntity categoryEntity;
    private String title;
    private String description;
    private String imgBanner;

}
