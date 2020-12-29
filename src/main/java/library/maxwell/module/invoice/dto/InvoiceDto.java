package library.maxwell.module.invoice.dto;

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
	private Integer checkedBy;
	private String noInvoice;
	private String invoiceDate;
	private String paymentDate;
	private String treshold;
	private Double grandTotal;
	private String statusInvoice;
	private String borrower;
	private String address;
	private String phone;
	private String email;
	private boolean status;
	
}
