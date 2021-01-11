package library.maxwell.module.invoice.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import library.maxwell.config.security.auth.UserPrincipal;
import library.maxwell.module.topup.entity.HistoryBalanceEntity;
import library.maxwell.module.topup.repository.HistoryBalanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import library.maxwell.module.invoice.dto.InvoiceDto;
import library.maxwell.module.invoice.dto.StatusMessageDto;
import library.maxwell.module.invoice.entity.InvoiceEntity;
import library.maxwell.module.invoice.repository.InvoiceRepository;
import library.maxwell.module.topup.entity.UserBalanceEntity;
import library.maxwell.module.topup.repository.UserBalanceRepository;
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
	
	@Autowired
	private UserBalanceRepository userBalanceRepository;

	@Autowired
	private HistoryBalanceRepository historyBalanceRepository;

	@Override
	public StatusMessageDto<?> getAll(UserPrincipal userPrincipal, String statusInvoice) {

		StatusMessageDto<List<?>> result = new StatusMessageDto<>();
		Integer id = userPrincipal.getId();
		List<InvoiceEntity> invoiceEntity;
		if (statusInvoice.equals("")) {
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

		LocalDateTime threshold = invoiceEntity.getInvoiceDate().plusDays(7);
		System.out.println(threshold);
		UserDetailEntity borrower = userDetailRepository.findByUserEntityUserId(idBorrower);
		invoiceDto.setInvoiceId(invoiceEntity.getInvoiceId());
		invoiceDto.setNoInvoice(invoiceEntity.getNoInvoice());
		invoiceDto.setGrandTotal(invoiceEntity.getGrandTotal());

		if (invoiceEntity.getPaymentDate() == null) {
			invoiceDto.setPaymentDate(null);
		} else {
			LocalDateTime paymentDate = invoiceEntity.getPaymentDate();
			invoiceDto.setPaymentDate(paymentDate);
		}
		invoiceDto.setInvoiceDate(invoiceEntity.getInvoiceDate());
		invoiceDto.setThreshold(threshold);
		invoiceDto.setBorrower(borrower.getFirstName() + " " + borrower.getLastName());
		invoiceDto.setAddress(borrower.getAddress());
		invoiceDto.setPhone(borrower.getPhoneNumber());
		invoiceDto.setEmail(invoiceEntity.getBorrowerEntity().getEmail());
		invoiceDto.setStatusInvoice(invoiceEntity.getStatusInvoice());

		return invoiceDto;
	}

	@Override
	public InvoiceEntity addInvoice(Double grandTotal, String typeInvoice, String statusInvoice, UserPrincipal userPrincipal) {
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
		invoiceEntity.setTypeInvoice(typeInvoice);
		invoiceEntity.setGrandTotal(grandTotal);
//		payment date pertama kali null, akan berubah pada saat user membayar invoice
		invoiceEntity.setStatusInvoice(statusInvoice);
		invoiceRepository.save(invoiceEntity);
		return invoiceEntity;
	}

	@Override
	public StatusMessageDto<?> pay(UserPrincipal userPrincipal, Integer invoiceId) {
		StatusMessageDto<InvoiceEntity> result = new StatusMessageDto<>();

		LocalDateTime now = LocalDateTime.now();
		Integer userid = userPrincipal.getId();
		UserBalanceEntity userBalanceEntity = userBalanceRepository.findByUserEntity_UserIdIs(userid);
		
		InvoiceEntity invoiceEntity = invoiceRepository.findById(invoiceId).get();
		
		if(invoiceEntity.getGrandTotal() >= userBalanceEntity.getNominal()) {
			result.setMessage("Sorry, Your Current Balance is Insufficient.");
			result.setStatus(HttpStatus.BAD_GATEWAY.value());
			result.setData(null);
			return result;
		}
		
		
		if(invoiceEntity.getStatusInvoice().equalsIgnoreCase("Paid")) {
			result.setMessage("Your invoice is already paid.");
			result.setStatus(HttpStatus.BAD_GATEWAY.value());
			result.setData(null);
			return result;
		}
		
//		mengurangi balance user
		userBalanceEntity.setNominal(userBalanceEntity.getNominal() - invoiceEntity.getGrandTotal());
		userBalanceRepository.save(userBalanceEntity);

//		insert history top up

		UserEntity admin = userRepository.findByUserId(1);

		HistoryBalanceEntity historyBalanceEntity = new HistoryBalanceEntity();
		historyBalanceEntity.setUserBalanceEntity(userBalanceEntity);
		historyBalanceEntity.setUserEntity(admin); //check by admin
		historyBalanceEntity.setStatusPayment("Success");
		historyBalanceEntity.setNominal(invoiceEntity.getGrandTotal());
		historyBalanceEntity.setDateAcc(now);
		historyBalanceEntity.setStatus(true);
		historyBalanceEntity.setPaymentMethod("Debit");
		historyBalanceRepository.save(historyBalanceEntity);

//		update status invoice menjadi Paid
		invoiceEntity.setStatusInvoice("Paid");
		invoiceEntity.setPaymentDate(now);
		invoiceRepository.save(invoiceEntity);
		
		result.setStatus(HttpStatus.OK.value());
		result.setMessage(invoiceEntity.getStatusInvoice());
		result.setData(invoiceEntity);
		return result;
	}

}
