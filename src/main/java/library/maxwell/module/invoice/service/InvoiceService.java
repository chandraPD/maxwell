package library.maxwell.module.invoice.service;


import library.maxwell.module.invoice.dto.InvoiceDto;
import library.maxwell.module.invoice.dto.StatusMessageDto;

public interface InvoiceService {
	
	StatusMessageDto<?> saveMulti(InvoiceDto dto);
	StatusMessageDto<?> getById(Integer invoiceId);
	StatusMessageDto<?> getAll();
}
