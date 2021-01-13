package library.maxwell.module.book.dto;

import library.maxwell.module.book.entity.AuthorEntity;
import library.maxwell.module.book.entity.CategoryEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookDto2 {
	private Integer bookId;
	private String bookCode;
	private CategoryEntity categoryEntity;
	private String title;
	private String description;
	private String imgBanner;
	private String imgDetail;
	private Integer qty;
	private String createdAt;
	private String updatedAt;
	private String createdBy;
	private String updatedBy;
	private String publishDate;
	private String statusBook;
	private AuthorEntity authorEntity;
}
