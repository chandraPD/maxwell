package library.maxwell.module.invoice.dto;

import java.time.LocalDateTime;

import library.maxwell.module.book.entity.BorrowedBookEntity;
import lombok.Data;

@Data
public class InvoiceDetailDto {
	
	private Integer invoiceDetailId;
	private String title;
	private String borrowedBookCode;
	private String bookDetailCode;
	private Integer invoiceId;
	private BorrowedBookEntity borrowedBookEntity;
	private LocalDateTime borrowedDate;
	private LocalDateTime returnDate;
	private LocalDateTime threshold;
	private Double total;
	private String type;
	private Long late;
	
}
