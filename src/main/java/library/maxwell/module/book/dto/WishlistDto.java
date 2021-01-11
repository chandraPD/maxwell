package library.maxwell.module.book.dto;

import library.maxwell.module.book.entity.BookEntity;
import library.maxwell.module.book.entity.WishlistEntity;
import library.maxwell.module.user.entity.UserEntity;
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
