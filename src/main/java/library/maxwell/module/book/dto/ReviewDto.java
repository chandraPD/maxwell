package library.maxwell.module.book.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDto {
	private Integer Id;
	private Double rate;
	private String comment;
	private Integer bookId;
	private Integer userId;
	private boolean status;
	private String firstname;
	private String lastname;

}
