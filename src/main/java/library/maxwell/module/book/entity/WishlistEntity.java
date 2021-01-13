package library.maxwell.module.book.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import library.maxwell.module.user.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "wishlist")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WishlistEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "wishlist_id")
	private Integer wishlistId;

	@ManyToOne
	@JoinColumn(name = "book_id", referencedColumnName = "book_id")
	private BookEntity bookEntity;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private UserEntity userEntity;

	@Column(name = "status", unique = false, length = 20)
	private Boolean status = true;

}
