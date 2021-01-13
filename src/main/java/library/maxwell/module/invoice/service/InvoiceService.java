package library.maxwell.module.invoice.service;


import library.maxwell.config.security.auth.UserPrincipal;
import library.maxwell.module.invoice.dto.StatusMessageDto;
import library.maxwell.module.invoice.entity.InvoiceEntity;
import library.maxwell.module.user.entity.UserEntity;

public interface InvoiceService {

	StatusMessageDto<?> getById(Integer invoiceId);
	StatusMessageDto<?> getAll();
	
	StatusMessageDto<?> getAll(UserPrincipal userPrincipal, String param);
	InvoiceEntity addInvoice(Double grandTotal, String typeInvoice, String statausInvoice, UserEntity userEntity);
	
	StatusMessageDto<?> pay(UserPrincipal userPrincipal, Integer invoiceId);
}
