package library.maxwell.module.invoice.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import library.maxwell.module.book.entity.BorrowedBookEntity;
import library.maxwell.module.invoice.dto.InvoiceDetailDto;
import library.maxwell.module.invoice.dto.StatusMessageDto;
import library.maxwell.module.invoice.entity.InvoiceDetailEntity;
import library.maxwell.module.invoice.entity.InvoiceEntity;
import library.maxwell.module.invoice.repository.InvoiceDetailRepository;

@Service
@Transactional
public class InvoiceDetailServiceImpl implements InvoiceDetailService{

	@Autowired
	private InvoiceDetailRepository invoiceDetailRepository;
	
	@Override
	public StatusMessageDto<?> getByInvoiceId(Integer InvoiceId) {
		// TODO Auto-generated method stub
		StatusMessageDto<List<?>> result = new StatusMessageDto<>();
		List<InvoiceDetailEntity> invoiceDetailEntity = invoiceDetailRepository.getByInvoiceId(InvoiceId);
		
		List<InvoiceDetailDto> InvoiceDetails = new ArrayList<>();
		
		for (InvoiceDetailEntity row : invoiceDetailEntity) {
			
			InvoiceDetailDto invoiceDetailDto = new InvoiceDetailDto();
			
			LocalDateTime borrowerDate = row.getBorrowedBookEntity().getBorrowedDate();
			LocalDateTime dueOn = row.getBorrowedBookEntity().getThreshold();
			Duration duration = Duration.between(borrowerDate, dueOn);
			Long diffDays = duration.toDays();
			
			invoiceDetailDto.setInvoiceDetailId(row.getInvoiceDetailId());
			invoiceDetailDto.setBorrowedDate(borrowerDate);
			invoiceDetailDto.setGrandTotal(row.getTotal());
			invoiceDetailDto.setTitle(row.getBorrowedBookEntity().getBookDetailEntity().getBookEntity().getTitle());
			invoiceDetailDto.setThreshold(dueOn);
			invoiceDetailDto.setLate(diffDays);
			invoiceDetailDto.setType(row.getType());
			
			InvoiceDetails.add(invoiceDetailDto);
		}

		result.setStatus(HttpStatus.OK.value());
		result.setMessage("Data ditemukan");
		result.setData(InvoiceDetails);

		return result;
	}

	@Override
	public InvoiceDetailEntity addInvoiceDetail(InvoiceEntity invoiceEntity, BorrowedBookEntity borrowedBookEntity) {
		// TODO Auto-generated method stub
		
		InvoiceDetailEntity invoiceDetailEntity = new InvoiceDetailEntity();
		invoiceDetailEntity.setBorrowedBookEntity(borrowedBookEntity);
		invoiceDetailEntity.setInvoiceEntity(invoiceEntity);
		invoiceDetailEntity.setTotal((double)5000);
		invoiceDetailEntity.setType("DP");
		
		invoiceDetailRepository.save(invoiceDetailEntity);
		return invoiceDetailEntity;
	}

	@Override
	public InvoiceDetailEntity addInvoiceDetails(InvoiceEntity invoiceEntity, List<InvoiceDetailDto> invoiceDetailDtos) {
		for (InvoiceDetailDto e: invoiceDetailDtos) {
			InvoiceDetailEntity invoiceDetailEntity = new InvoiceDetailEntity();
			invoiceDetailEntity.setBorrowedBookEntity(e.getBorrowedBookEntity());
			invoiceDetailEntity.setInvoiceEntity(invoiceEntity);
			invoiceDetailEntity.setTotal(e.getGrandTotal());
			invoiceDetailEntity.setType(e.getType());
			invoiceDetailRepository.save(invoiceDetailEntity);
		}
		return null;
	}

}
