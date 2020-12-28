package library.maxwell.module.book.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookDetailDto {
	private Integer bookDetailId;
	private Integer bookId;
	private String typeOfDamage;
	private String descOfDamage;
	private Boolean status;
}
