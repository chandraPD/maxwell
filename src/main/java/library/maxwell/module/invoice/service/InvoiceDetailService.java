package library.maxwell.module.invoice.service;

import library.maxwell.module.book.entity.BorrowedBookEntity;
import library.maxwell.module.invoice.dto.InvoiceDetailDto;
import library.maxwell.module.invoice.dto.StatusMessageDto;
import library.maxwell.module.invoice.entity.InvoiceDetailEntity;
import library.maxwell.module.invoice.entity.InvoiceEntity;

import java.util.List;

public interface InvoiceDetailService {
	
	StatusMessageDto<?> getByInvoiceId(Integer InvoiceId);
	
	InvoiceDetailEntity addInvoiceDetail(InvoiceEntity invoiceEntity, BorrowedBookEntity borrowedBookEntity);
	InvoiceDetailEntity addInvoiceDetails(InvoiceEntity invoiceEntity, List<InvoiceDetailDto> invoiceDetailDtos);
}
