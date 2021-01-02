package library.maxwell.module.invoice.service;

import library.maxwell.module.invoice.dto.StatusMessageDto;

public interface InvoiceDetailService {
	
	StatusMessageDto<?> getByInvoiceId(Integer InvoiceId);
	
	
}
