package library.maxwell.module.invoice.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InvoiceDto {

	private Integer invoiceId;
	private String checkedBy;
	private String noInvoice;
	private LocalDateTime invoiceDate;
	private LocalDateTime paymentDate;
	private LocalDateTime threshold;
	private Double grandTotal;
	private String typeInvoice;
	private String statusInvoice;
	private String borrower;
	private String address;
	private String phone;
	private String email;
	private boolean status;
	
}
