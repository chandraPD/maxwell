package library.maxwell.module.invoice.service;


import library.maxwell.config.security.auth.UserPrincipal;
import library.maxwell.module.invoice.dto.InvoiceDto;
import library.maxwell.module.invoice.dto.StatusMessageDto;

public interface InvoiceService {

	StatusMessageDto<?> getById(Integer invoiceId);
	StatusMessageDto<?> getAll();
	StatusMessageDto<?> getAll(UserPrincipal userPrincipal);
}
