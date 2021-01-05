package library.maxwell.module.book.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import library.maxwell.module.user.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "borrowed_book")
@Data
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"borrowedDate"}, allowGetters = true)
public class BorrowedBookEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "borrowed_book_id")
	private Integer borrowedBookId;

	@ManyToOne
	@JoinColumn(name = "user_id", referencedColumnName = "user_id")
	private UserEntity userIdEntity;

	@ManyToOne
	@JoinColumn(name = "book_detail_id", referencedColumnName = "book_detail_id")
	private BookDetailEntity bookDetailEntity;

	@CreatedDate
	@Column(name = "borrowed_date")
	private LocalDateTime borrowedDate;

	@Column(name = "threshold")
	private LocalDateTime threshold;

	@Column(name = "returned_date")
	private LocalDateTime returnedDate;

	@Column(name = "desc_of_damage", length = 500)
	private String descOfDamage;

	@Column(name = "status_book", length = 50)
	private String statusBook;

	@ManyToOne
	@JoinColumn(name = "given_by", referencedColumnName = "user_id")
	private UserEntity givenByEntity;

	@ManyToOne
	@JoinColumn(name = "taken_by", referencedColumnName = "user_id")
	private UserEntity takenByEntity;

	@Column(name = "dp")
	private Double dp;

	@Column(name = "grand_total")
	private Double grandTotal;

	@Column(name = "status")
	private Boolean status = true;
	
	@Column(name = "borrowed_book_code", length = 10, unique= true)
	private String borrowedBookCode;
}
