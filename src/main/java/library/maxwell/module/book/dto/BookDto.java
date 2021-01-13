package library.maxwell.module.book.dto;

import java.sql.Date;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookDto {
	private Integer bookId;
	private String bookCode;
	private Integer categoryId;
	private String title;
	private String description;
	private String imgBanner;
	private String imgDetail;
	private Integer qty;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	private Integer createdBy;
	private Integer updatedBy;
	private Date publishDate;
	private Integer authorId;
	private Boolean status;
}
