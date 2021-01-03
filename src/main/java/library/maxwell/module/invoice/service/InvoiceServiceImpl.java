package library.maxwell.module.invoice.service;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import library.maxwell.config.security.auth.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import library.maxwell.module.invoice.dto.InvoiceDto;
import library.maxwell.module.invoice.dto.StatusMessageDto;
import library.maxwell.module.invoice.entity.InvoiceEntity;
import library.maxwell.module.invoice.repository.InvoiceRepository;
import library.maxwell.module.user.entity.UserDetailEntity;
import library.maxwell.module.user.repository.UserDetailRepository;

@Service
@Transactional
public class InvoiceServiceImpl implements InvoiceService {

	@Autowired
	private InvoiceRepository invoiceRepository;

	@Autowired
	private UserDetailRepository userDetailRepository;

	@Override
	public StatusMessageDto<?> getAll() {

//		List<InvoiceEntity> invoiceEntity2 = invoiceRepository.findAllByBorrowerEntity_UserId(id);
//		return Optional.ofNullable(invoiceEntity)
//				.map(invoiceEntities -> StatusMessageDto.success("Data Invoice telah ditemukan", invoiceEntities))
//				.orElse(StatusMessageDto.error("Data Invoice Belum ditemukan"));
		StatusMessageDto<List<?>> result = new StatusMessageDto<>();
		List<InvoiceEntity> invoiceEntity = invoiceRepository.findAllByStatusIsTrue();
		List<InvoiceDto> invoiceDtos = new ArrayList<>();

		if (invoiceEntity.isEmpty()) {
			result.setStatus(HttpStatus.BAD_REQUEST.value());
			result.setMessage("Data belum ada");
			result.setData(null);
		} else {
			for (InvoiceEntity row : invoiceEntity) {
				invoiceDtos.add(convertToInvoiceDto(row));
			}
			result.setStatus(HttpStatus.OK.value());
			result.setMessage("Data Invoice telah ditemukan1");
			result.setData(invoiceDtos);
		}
		return result;
	}

	@Override
	public StatusMessageDto<?> getById(Integer invoiceId) {
		// TODO Auto-generated method stub
		InvoiceEntity invoiceEntity = invoiceRepository.getById(invoiceId);
		StatusMessageDto<InvoiceDto> result = new StatusMessageDto<>();
		if (invoiceEntity != null) {

			InvoiceDto invoiceDto = convertToInvoiceDto(invoiceEntity);

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

	public InvoiceDto convertToInvoiceDto(InvoiceEntity invoiceEntity) {
		Integer idBorrower = invoiceEntity.getBorrowerEntity().getUserId();

		InvoiceDto invoiceDto = new InvoiceDto();

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy hh:mm");
		String invoiceDateString = invoiceEntity.getInvoiceDate().format(formatter);

		String treshold = invoiceEntity.getInvoiceDate().plusDays(7).format(formatter);

		UserDetailEntity borrower = userDetailRepository.findByUserEntityUserId(idBorrower);
		invoiceDto.setInvoiceId(invoiceEntity.getInvoiceId());
		invoiceDto.setNoInvoice(invoiceEntity.getNoInvoice());
		invoiceDto.setGrandTotal(invoiceEntity.getGrandTotal());
		if (invoiceEntity.getCheckedByEntity() == null) {
			invoiceDto.setCheckedBy(null);
		} else {
			Integer idChecked = invoiceEntity.getCheckedByEntity().getUserId();
			UserDetailEntity checked = userDetailRepository.findByUserEntityUserId(idChecked);
			invoiceDto.setCheckedBy(checked.getFirstName() + " " + checked.getLastName());
		}
		if (invoiceEntity.getPaymentDate() == null) {
			invoiceDto.setPaymentDate(null);
		} else {
			String paymentDate = invoiceEntity.getPaymentDate().format(formatter);
			invoiceDto.setPaymentDate(paymentDate);
		}
		invoiceDto.setInvoiceDate(invoiceDateString);
		invoiceDto.setTreshold(treshold);
		invoiceDto.setBorrower(borrower.getFirstName() + " " + borrower.getLastName());
		invoiceDto.setAddress(borrower.getAddress());
		invoiceDto.setPhone(borrower.getPhoneNumber());
		invoiceDto.setEmail(invoiceEntity.getBorrowerEntity().getEmail());
		invoiceDto.setStatusInvoice(invoiceEntity.getStatusInvoice());

		return invoiceDto;
	}

	@Override
	public StatusMessageDto<?> getAll(UserPrincipal userPrincipal) {

//		List<InvoiceEntity> invoiceEntity2 = invoiceRepository.findAllByBorrowerEntity_UserId(id);
//		return Optional.ofNullable(invoiceEntity)
//				.map(invoiceEntities -> StatusMessageDto.success("Data Invoice telah ditemukan", invoiceEntities))
//				.orElse(StatusMessageDto.error("Data Invoice Belum ditemukan"));
		StatusMessageDto<List<?>> result = new StatusMessageDto<>();
		Integer id = userPrincipal.getId();
		List<InvoiceEntity> invoiceEntity = invoiceRepository.findAllByBorrowerEntity_UserId(id);
		List<InvoiceDto> invoiceDtos = new ArrayList<>();

		if (invoiceEntity != null) {
			for (InvoiceEntity row : invoiceEntity) {
				invoiceDtos.add(convertToInvoiceDto(row));
			}
			result.setStatus(HttpStatus.OK.value());
			result.setMessage("Data Invoice telah ditemukan1");
			result.setData(invoiceDtos);
		} else {
			result.setMessage("Data belum ada");
			result.setStatus(HttpStatus.BAD_GATEWAY.value());
			result.setData(null);
		}
		return result;
	}

}
