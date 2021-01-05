package library.maxwell.module.invoice.service;

import java.time.LocalDate;
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
import library.maxwell.module.user.entity.UserEntity;
import library.maxwell.module.user.repository.UserDetailRepository;
import library.maxwell.module.user.repository.UserRepository;

@Service
@Transactional
public class InvoiceServiceImpl implements InvoiceService {

	@Autowired
	private InvoiceRepository invoiceRepository;

	@Autowired
	private UserDetailRepository userDetailRepository;

	@Autowired
	private UserRepository userRepository;

	@Override
	public StatusMessageDto<?> getAll(UserPrincipal userPrincipal, String statusInvoice) {

		StatusMessageDto<List<?>> result = new StatusMessageDto<>();
		Integer id = userPrincipal.getId();
		List<InvoiceEntity> invoiceEntity = new ArrayList<>();
		if (statusInvoice == "") {
//			Get All Invoice
			invoiceEntity = invoiceRepository.findAllByStatusIsTrueAndBorrowerEntity_UserIdIs(id);
		} else {
//			Get All Invoice is status Invoice = Waiting for Payment
			invoiceEntity = invoiceRepository.findAllByStatusIsTrueAndBorrowerEntity_UserIdIsAndStatusInvoiceIs(id,statusInvoice);
		}
		List<InvoiceDto> invoiceDtos = new ArrayList<>();

		if (invoiceEntity != null) {
			for (InvoiceEntity row : invoiceEntity) {
				invoiceDtos.add(convertToInvoiceDto(row));
			}
			result.setStatus(HttpStatus.OK.value());
			result.setMessage("Data Invoice telah ditemukan");
			result.setData(invoiceDtos);
		} else {
			result.setMessage("Data belum ada");
			result.setStatus(HttpStatus.BAD_GATEWAY.value());
			result.setData(null);
		}
		return result;
	}

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
	public InvoiceEntity addInvoice(UserPrincipal userPrincipal) {
		// TODO Auto-generated method stub
		InvoiceEntity invoiceEntity = new InvoiceEntity();
		Integer idBorrower = userPrincipal.getId();
		UserEntity borrower = userRepository.findById(idBorrower).get();
		invoiceEntity.setBorrowerEntity(borrower);

		DateTimeFormatter getYearFull = DateTimeFormatter.ofPattern("yyyy");
		DateTimeFormatter getYear = DateTimeFormatter.ofPattern("yy");
		String year = LocalDate.now().format(getYear);
		Integer yearFull = Integer.parseInt(LocalDate.now().format(getYearFull));
//		find last no invoice
		String lastNoInvoice = invoiceRepository.getLastInvoice(yearFull);

		String seq;
		if (lastNoInvoice == null) {
			seq = String.format("%04d", 1);
		} else {
			Integer number = Integer.parseInt(lastNoInvoice.substring(6, 9)) + 1;
			seq = String.format("%04d", number);
		}
		String noInvoice = "INV" + year + seq;
		invoiceEntity.setNoInvoice(noInvoice);
		invoiceEntity.setGrandTotal((double) 5000);
//		payment date pertama kali null, akan berubah pada saat user membayar invoice
		invoiceEntity.setStatusInvoice("Waiting For Payment");
		invoiceRepository.save(invoiceEntity);
		return invoiceEntity;
	}

}
