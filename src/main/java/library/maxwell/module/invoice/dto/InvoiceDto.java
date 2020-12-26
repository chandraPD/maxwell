package library.maxwell.module.invoice.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class InvoiceDto {

	private Integer invoiceId;
	private Integer checkedBy;
	private String noInvoice;
	private LocalDateTime invoiceDate;
	private LocalDateTime paymentDate;
	private Double grandTotal;
	private String statusInvoice;
	private boolean status;
}
