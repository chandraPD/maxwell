package library.maxwell.module.invoice.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import library.maxwell.module.invoice.dto.InvoiceDto;
import library.maxwell.module.invoice.dto.InvoiceDto.InvoiceDtoBuilder;
import library.maxwell.module.invoice.dto.StatusMessageDto;
import library.maxwell.module.invoice.entity.InvoiceEntity;
import library.maxwell.module.invoice.repository.InvoiceRepository;

@Service
@Transactional
public class InvoiceServiceImpl implements InvoiceService {

	@Autowired
	private InvoiceRepository invoiceRepository;

	@Override
	public StatusMessageDto<?> getAll() {

		List<InvoiceEntity> invoiceEntity = invoiceRepository.getdataAll();
		return Optional.ofNullable(invoiceEntity)
				.map(invoiceEntities -> StatusMessageDto.success("Data Invoice telah ditemukan", invoiceEntities))
				.orElse(StatusMessageDto.error("Data Invoice telah ditemukan"));

	}

	@Override
	public StatusMessageDto<?> saveMulti(InvoiceDto dto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StatusMessageDto<?> getById(Integer invoiceId) {
		// TODO Auto-generated method stub
		InvoiceEntity invoiceEntity = invoiceRepository.getById(invoiceId);
		StatusMessageDto<InvoiceDto> result = new StatusMessageDto<>();
		if (invoiceEntity != null) {

			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy hh:mm");
			String invoiceDateString = invoiceEntity.getInvoiceDate().format(formatter);
			
			String treshold = invoiceEntity.getInvoiceDate().plusDays(7).format(formatter);
			
			InvoiceDto invoiceDto = new InvoiceDto();
			
			invoiceDto.setInvoiceId(invoiceEntity.getInvoiceId());
			invoiceDto.setInvoiceDate(invoiceDateString);
			invoiceDto.setGrandTotal(invoiceEntity.getGrandTotal());
			invoiceDto.setPaymentDate(null);
			invoiceDto.setNoInvoice(invoiceEntity.getNoInvoice());
			invoiceDto.setStatusInvoice(invoiceEntity.getNoInvoice());
			invoiceDto.setBorrower(invoiceEntity.getFirstName().toUpperCase() + " " + invoiceEntity.getLastName().toUpperCase());
			invoiceDto.setAddress(invoiceEntity.getAddress());
			invoiceDto.setEmail(invoiceEntity.getBorrowerEntity().getEmail());
			invoiceDto.setPhone(invoiceEntity.getPhoneNumber());
			invoiceDto.setTreshold(treshold);
			

			result.setStatus(HttpStatus.OK.value());
			result.setMessage("Data ditemukan");
			result.setData(invoiceDto);
		} else {
			result.setMessage("Data belum ada");
			result.setStatus(HttpStatus.BAD_GATEWAY.value());
			result.setData(null);
		}
		return result;
	}

//	method

	public InvoiceEntity convertToInvoiceEntity(InvoiceDto dto) {
		InvoiceEntity invoiceEntity = new InvoiceEntity();
		invoiceEntity.setNoInvoice(dto.getNoInvoice());
//		invoiceEntity.setInvoiceDate(dto.getInvoiceDate());
		invoiceEntity.setGrandTotal(dto.getGrandTotal());
		return invoiceEntity;
	}

}
