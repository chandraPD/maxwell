package library.maxwell.module.invoice.dto;

import lombok.Data;

@Data
public class InvoiceDetailDto {
	
	private Integer invoiceDetailId;
	private String title;
	private String borrowedDate;
	private String treshold;
	private Double grandTotal;
	private String type;
	private Long late;
	
}
