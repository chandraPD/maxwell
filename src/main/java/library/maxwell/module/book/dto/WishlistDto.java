package library.maxwell.module.book.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WishlistDto {
	private Integer wishlistId;
	private Integer bookId;
	private Integer userId;
	private boolean status;
}
