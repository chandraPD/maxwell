package library.maxwell.module.invoice.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import library.maxwell.module.invoice.dto.InvoiceDetailDto;
import library.maxwell.module.invoice.dto.StatusMessageDto;
import library.maxwell.module.invoice.entity.InvoiceDetailEntity;
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
		
		List<InvoiceDetailDto> InvoiceDetails = new ArrayList<InvoiceDetailDto>();
		
		for (InvoiceDetailEntity row : invoiceDetailEntity) {
			
			InvoiceDetailDto invoiceDetailDto = new InvoiceDetailDto();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy hh:mm");
			
			String borrowerDate = row.getBorrowedBookEntity().getBorrowedDate().format(formatter);
			String dueOn = row.getBorrowedBookEntity().getThreshold().format(formatter);
			Duration duration = Duration.between(row.getBorrowedBookEntity().getBorrowedDate(), row.getBorrowedBookEntity().getThreshold());
			
			Long diffDays = duration.toDays();
			
			invoiceDetailDto.setInvoiceDetailId(row.getInvoiceDetailId());
			invoiceDetailDto.setBorrowedDate(borrowerDate);;
			invoiceDetailDto.setGrandTotal(row.getTotal());
			invoiceDetailDto.setTitle(row.getBorrowedBookEntity().getBookDetailEntity().getBookEntity().getTitle());
			invoiceDetailDto.setTreshold(dueOn);
			invoiceDetailDto.setLate(diffDays);
			
			InvoiceDetails.add(invoiceDetailDto);
		}
		
		if(invoiceDetailEntity != null) {
			result.setStatus(HttpStatus.OK.value());
			result.setMessage("Data ditemukan");
			result.setData(InvoiceDetails);
		}else {
			result.setMessage("Data belum ada");
			result.setStatus(HttpStatus.BAD_GATEWAY.value());
			result.setData(null);
		}
		
		return result;
	}

}
