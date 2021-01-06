package library.maxwell.module.book.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "book_detail")
@Data
public class BookDetailEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "book_detail_id")
	private Integer bookDetailId;
	
	@Column(name = "book_detail_code", unique = true, length = 10)
	private String bookDetailCode;
	
	@ManyToOne
	@JoinColumn(name = "book_id", referencedColumnName = "book_id")
	private BookEntity bookEntity;
	
	@Column(name = "type_of_damage", length = 20)
	private String typeOfDamage;
	
	@Column(name = "desc_of_damage", length = 500)
	private String descOfDamage;
	
	@Column(name = "status")
	private Boolean status = true;
	
}
