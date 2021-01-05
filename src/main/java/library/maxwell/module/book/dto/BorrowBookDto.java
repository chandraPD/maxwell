package library.maxwell.module.book.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BorrowBookDto {
	private Integer borrowedBookId;
	private String borrowedBookCode;
	private String borrowedDate;
	private String title;
	private String descOfDamage;
	private Double dp;
	private Double grandTotal;
	private String returnedDate;
	private String statusBook;
	private String threshold;
	private Integer bookDetailId;
	private String givenBy;
	private String takenBy;
	private String borrower;
}
