package library.maxwell.module.invoice.dto;

import java.time.LocalDateTime;

import library.maxwell.module.book.entity.BorrowedBookEntity;
import lombok.Data;

@Data
public class InvoiceDetailDto {
	
	private Integer invoiceDetailId;
	private String title;
	private Integer invoiceId;
	private BorrowedBookEntity borrowedBookEntity;
	private LocalDateTime borrowedDate;
	private LocalDateTime threshold;
	private Double grandTotal;
	private String type;
	private Long late;
	
}
