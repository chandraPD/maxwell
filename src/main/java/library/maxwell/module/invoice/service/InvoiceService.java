package library.maxwell.module.invoice.service;


import library.maxwell.module.invoice.dto.InvoiceDto;
import library.maxwell.module.invoice.dto.StatusMessageDto;
import library.maxwell.module.invoice.entity.InvoiceEntity;

import java.util.List;

public interface InvoiceService {
	
	StatusMessageDto<?> saveMulti(InvoiceDto dto);
	StatusMessageDto<?> getById(Integer invoiceId);
	StatusMessageDto<List<InvoiceEntity>> getAll();
}
