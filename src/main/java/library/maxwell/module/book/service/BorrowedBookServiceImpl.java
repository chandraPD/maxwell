package library.maxwell.module.book.service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import library.maxwell.module.book.controller.BorrowedBookController;
import library.maxwell.module.book.dto.ReturnBookDto;
import library.maxwell.module.invoice.dto.InvoiceDetailDto;
import library.maxwell.module.invoice.repository.InvoiceDetailRepository;
import library.maxwell.module.invoice.repository.InvoiceRepository;
import library.maxwell.module.topup.entity.HistoryBalanceEntity;
import library.maxwell.module.topup.entity.UserBalanceEntity;
import library.maxwell.module.topup.repository.HistoryBalanceRepository;
import library.maxwell.module.topup.repository.UserBalanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import library.maxwell.config.security.auth.UserPrincipal;
import library.maxwell.module.book.dto.BorrowBookDto;
import library.maxwell.module.book.entity.BookDetailEntity;
import library.maxwell.module.book.entity.BookEntity;
import library.maxwell.module.book.entity.BorrowedBookEntity;
import library.maxwell.module.book.repository.BookDetailRepository;
import library.maxwell.module.book.repository.BorrowedBookRepository;
import library.maxwell.module.invoice.dto.StatusMessageDto;
import library.maxwell.module.invoice.entity.InvoiceDetailEntity;
import library.maxwell.module.invoice.entity.InvoiceEntity;
import library.maxwell.module.invoice.service.InvoiceDetailService;
import library.maxwell.module.invoice.service.InvoiceService;
import library.maxwell.module.user.entity.UserDetailEntity;
import library.maxwell.module.user.entity.UserEntity;
import library.maxwell.module.user.repository.UserDetailRepository;
import library.maxwell.module.user.repository.UserRepository;

@Service
@Transactional
public class BorrowedBookServiceImpl implements BorrowedBookService {

	@Autowired
	private BookDetailRepository bookDetailRepository;

	@Autowired
	private UserDetailRepository userDetailRepository;

	@Autowired
	private BorrowedBookRepository borrowedBookRepository;

	@Autowired
	private InvoiceService invoiceService;

	@Autowired
	private InvoiceDetailService invoiceDetailService;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private InvoiceRepository invoiceRepository;

	@Autowired
	private InvoiceDetailRepository invoiceDetailRepository;

	@Autowired
	UserBalanceRepository userBalanceRepository;

	@Autowired
	HistoryBalanceRepository historyBalanceRepository;

	@Override
	public StatusMessageDto<?> getById(Integer borrowedBookId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StatusMessageDto<?> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StatusMessageDto<?> getAll(UserPrincipal userPrincipal) {
		Integer userId = userPrincipal.getId();
		UserEntity userEntity = userRepository.findByUserId(userId);

		List<BorrowedBookEntity> borrowedBookEntities;
		if(userEntity.getActiveRole().equalsIgnoreCase("1")) {
			borrowedBookEntities = borrowedBookRepository.findAllByStatusIsTrueAndUserIdEntity_UserIdIs(userId);
		} else {
			borrowedBookEntities = borrowedBookRepository.findAllByStatusIsTrue();
		}

		StatusMessageDto<List<BorrowBookDto>> result = new StatusMessageDto<>();

		List<BorrowBookDto> borrowBookDtos = new ArrayList<>();

		if(borrowedBookEntities == null) {
			result.setMessage("Data belum ada");
			result.setStatus(HttpStatus.BAD_REQUEST.value());
			result.setData(null);
		}else {
			for (BorrowedBookEntity d : borrowedBookEntities) {
				borrowBookDtos.add(convertToBorrowBookDto(d));
			}
			result.setMessage("Data berhasil diambil");
			result.setStatus(HttpStatus.OK.value());
			result.setData(borrowBookDtos);
		}
		return result;
	}

	@Override
	public StatusMessageDto<?> getAllBorrowed(UserPrincipal userPrincipal) {
		Integer userId = userPrincipal.getId();
		UserEntity userEntity = userRepository.findByUserId(userId);

		List<BorrowedBookEntity> borrowedBookEntities;
		borrowedBookEntities = borrowedBookRepository.findAllByStatusIsTrueAndUserIdEntity_UserIdAndStatusBookIs(userId,"Waiting For Return");

		StatusMessageDto<List<BorrowBookDto>> result = new StatusMessageDto<>();

		List<BorrowBookDto> borrowBookDtos = new ArrayList<>();
		
		if(borrowedBookEntities == null) {
			result.setMessage("Data belum ada");
			result.setStatus(HttpStatus.BAD_REQUEST.value());
			result.setData(null);
		}else {
			for (BorrowedBookEntity d : borrowedBookEntities) {
				borrowBookDtos.add(convertToBorrowBookDto(d));
			}
			result.setMessage("Data berhasil diambil");
			result.setStatus(HttpStatus.OK.value());
			result.setData(borrowBookDtos);
		}
		return result;
	}


	@Override
	public StatusMessageDto<?> borrowBook(UserPrincipal userPrincipal, BorrowBookDto dto) {
		// TODO Auto-generated method stub

		StatusMessageDto<BorrowedBookEntity> result = new StatusMessageDto<>();

//		check user yang akan meminjam, data nya sudah lengkap atau belum
		Integer idUser = userPrincipal.getId();
		UserEntity userEntity = userRepository.findById(idUser).get();

		UserDetailEntity userDetailEntity = userDetailRepository.findByUserEntityUserId(idUser);
		if(userDetailEntity.getAddress() == null) {
			result.setMessage("Gagal meminjam Buku, Alamat anda belum ada, harap melengkapi Profile anda");
			result.setStatus(HttpStatus.BAD_REQUEST.value());
			result.setData(null);
			return result;
		}else if (userDetailEntity.getPhoneNumber() == null) {
			result.setMessage("Gagal meminjam Buku, No HP anda belum ada, harap melengkapi Profile anda");
			result.setStatus(HttpStatus.BAD_REQUEST.value());
			result.setData(null);
			return result;
		}else if (userDetailEntity.getDateOfBirth() == null) {
			result.setMessage("Gagal meminjam Buku, Tanggal Lahir anda belum ada, harap melengkapi Profile anda");
			result.setStatus(HttpStatus.BAD_REQUEST.value());
			result.setData(null);
		}


//		check tanggal peminjaman buku tidak boleh kurang dari tanggal hari ini
		LocalDateTime firstDate = dto.getReturnedDate();
		LocalDateTime secondDate = dto.getBorrowedDate();
		if(firstDate.isBefore(secondDate)) {
			result.setMessage("Gagal meminjam Buku, Tanggal yang diinputkan lebih kecil dari tanggal sekarang");
			result.setStatus(HttpStatus.BAD_REQUEST.value());
			result.setData(null);
			return result;
		}


		Long count = bookDetailRepository.countByStatusIsTrueAndStatusBookDetailIsAndBookEntity_BookIdIs("Available", dto.getBookId());
		if(count == 0 ) {
			result.setMessage("Gagal meminjam Buku, Buku yang akan dipinjam sudah tidak tersedia");
			result.setStatus(HttpStatus.BAD_REQUEST.value());
			result.setData(null);
			return result;
		}

		BorrowedBookEntity borrowedBookEntity = converToBorrowedBookEntity(dto);

		borrowedBookEntity.setUserIdEntity(userEntity);
		borrowedBookRepository.save(borrowedBookEntity);
//		Add Invoice
		InvoiceEntity invoiceEntity =  invoiceService.addInvoice((double)5000,"DP","Waiting For Payment",userPrincipal);
//		Get ID Invoice
		Integer idInvoice = invoiceEntity.getInvoiceId();
//		Add Invoice Detail base on id_invoice and borrowed_book_id

		InvoiceDetailEntity invoiceDetailEntity = invoiceDetailService.addInvoiceDetail(invoiceEntity, borrowedBookEntity);

		if(invoiceDetailEntity == null) {
			result.setMessage("Gagal meminjam Buku, silahkan hubungi administrator");
			result.setStatus(HttpStatus.BAD_REQUEST.value());
			result.setData(null);
		}else {
			result.setMessage("Berhsil, harap melakukan proses pembayaran");
			result.setStatus(HttpStatus.OK.value());
			result.setData(borrowedBookEntity);
		}
		return result;
	}

//	method 
	public BorrowedBookEntity converToBorrowedBookEntity (BorrowBookDto dto) {

		BorrowedBookEntity borrowedBookEntity = new BorrowedBookEntity();

//		Limit 1
		Pageable pageable  = PageRequest.of(0, 1, Sort.by(Sort.Direction.DESC, "bookDetailId"));
		Page<BookDetailEntity> pageBookDetail = bookDetailRepository.findByStatusIsTrueAndStatusBookDetailIsAndBookEntity_BookIdIs("Available",dto.getBookId(), pageable);

//		Store pageBookDetail ke dalam bookDetailEntity dan ambil 1 data
		BookDetailEntity bookDetailEntity = pageBookDetail.getContent().get(0);

//		update status book detail menjadi unavailable
		bookDetailEntity.setStatusBookDetail("Unavailable");
		bookDetailRepository.save(bookDetailEntity);

		borrowedBookEntity.setBookDetailEntity(bookDetailEntity);
		DateTimeFormatter getYearFull = DateTimeFormatter.ofPattern("yyyy");
		DateTimeFormatter getYear = DateTimeFormatter.ofPattern("yy");
		String year = LocalDate.now().format(getYear);
		Integer yearFull = Integer.parseInt(LocalDate.now().format(getYearFull));
//		find last no invoice
		String lastBorrowedCode = borrowedBookRepository.getLastBorrowed(yearFull);
		String seq;
		if (lastBorrowedCode == null) {
			seq = String.format("%04d", 1);
		} else {
			Integer number = Integer.parseInt(lastBorrowedCode.substring(4, 7)) + 1;
			seq = String.format("%04d", number);
		}
		String borrowedBookCode = "R" + year + seq;
		borrowedBookEntity.setBorrowedBookCode(borrowedBookCode);

		borrowedBookEntity.setGrandTotal((double) 5000);
//		return date pertama kali isi nya null
		borrowedBookEntity.setStatusBook("Waiting Given By Librarian");

		borrowedBookEntity.setThreshold(dto.getReturnedDate().plusHours(7));
		return borrowedBookEntity;
	}

	public BorrowBookDto convertToBorrowBookDto (BorrowedBookEntity borrowedBookEntity) {
		BookEntity bookEntity = borrowedBookEntity.getBookDetailEntity().getBookEntity();
		String borrower,givenBy,takenBy;
		if(borrowedBookEntity.getUserIdEntity() == null) {
			borrower = "-";
		}else {
			borrower = getNamaUserById(borrowedBookEntity.getUserIdEntity().getUserId());
		}

		if(borrowedBookEntity.getGivenByEntity() == null) {
			givenBy = "-";
		}else {
			givenBy = getNamaUserById(borrowedBookEntity.getGivenByEntity().getUserId());
		}

		if(borrowedBookEntity.getTakenByEntity() == null) {
			takenBy = "-";
		}else {
			takenBy = getNamaUserById(borrowedBookEntity.getTakenByEntity().getUserId());
		}

		BorrowBookDto dto = new BorrowBookDto();
		dto.setBorrowedBookId(borrowedBookEntity.getBorrowedBookId());
		dto.setBorrowedBookCode(borrowedBookEntity.getBorrowedBookCode());
		dto.setBorrower(borrower);
		dto.setBookDetailCode(borrowedBookEntity.getBookDetailEntity().getBookDetailCode());
		dto.setTitle(bookEntity.getTitle());
		dto.setGivenBy(givenBy);
		dto.setTakenBy(takenBy);
		dto.setBorrowedDate(borrowedBookEntity.getBorrowedDate());
		dto.setReturnedDate(borrowedBookEntity.getReturnedDate());

		if(borrowedBookEntity.getReturnedDate() == null) {
			dto.setReturnedDate(null);
		}else {
			dto.setReturnedDate(borrowedBookEntity.getReturnedDate());
		}

		dto.setStatusBook(borrowedBookEntity.getStatusBook());
		dto.setThreshold(borrowedBookEntity.getThreshold());
		dto.setGrandTotal(borrowedBookEntity.getGrandTotal());

		return dto;
	}

	public String getNamaUserById(Integer userId) {

		UserDetailEntity userDetailEntity = userDetailRepository.findByUserEntityUserId(userId);
		String fullName;
		if(userDetailEntity != null) {
			fullName = userDetailEntity.getFirstName() + " " + userDetailEntity.getLastName();
		}else {
			fullName = "-";
		}

		return fullName;
	}

	@Override
	public StatusMessageDto<?> accAct(UserPrincipal userPrincipal, Integer borrowedBookId) {
		StatusMessageDto result = new StatusMessageDto();
		Integer test = userPrincipal.getId();
		BorrowedBookEntity borrowedBookEntity = borrowedBookRepository.findByBorrowedBookId(borrowedBookId);

//		check invoice sudah dibayar atau belum untuk borrowed_book_id ini
		InvoiceDetailEntity lastData = invoiceDetailRepository.findTopByBorrowedBookEntity_BorrowedBookId(borrowedBookId);
		if(lastData.getInvoiceEntity().getStatusInvoice().equalsIgnoreCase("Waiting For Payment")){
			result.setStatus(HttpStatus.BAD_REQUEST.value());
			result.setMessage("This Invoice has not been paid");
			result.setData(null);
			return result;
		}

		if(borrowedBookEntity.getStatusBook().equalsIgnoreCase("Waiting Given By Librarian")) {
			borrowedBookEntity.setStatusBook("Waiting For Return");
			borrowedBookEntity.setGivenByEntity(userRepository.findByUserId(userPrincipal.getId()));
		}else if (borrowedBookEntity.getStatusBook().equalsIgnoreCase("Waiting Taken By Librarian")) {
//			borrowedBookEntity.setStatusBook("Waiting for Payment of Fines");
//			Check jika ada yang harus dikenakan denda
		}else {
			System.out.println("gagal");
		}
		borrowedBookRepository.save(borrowedBookEntity);
		result.setStatus(HttpStatus.OK.value());
		result.setMessage("Rent has been Accepted!");
		result.setData(borrowedBookEntity);
        return result;

	}

	@Override
	public StatusMessageDto<?> decAct(UserPrincipal userPrincipal, Integer borrowedBookId) {
		StatusMessageDto result = new StatusMessageDto();

		LocalDateTime now = LocalDateTime.now();
		BorrowedBookEntity borrowedBookEntity = borrowedBookRepository.findByBorrowedBookId(borrowedBookId);


//		check invoice sudah dibayar atau belum untuk borrowed_book_id ini
		InvoiceDetailEntity lastData = invoiceDetailRepository.findTopByBorrowedBookEntity_BorrowedBookId(borrowedBookId);


		if(lastData.getInvoiceEntity().getStatusInvoice().equalsIgnoreCase("Paid")){
//			Jika sudah dibayar lakukan input saldo ke user yang melakukan pembayaran
			Integer userId = lastData.getInvoiceEntity().getBorrowerEntity().getUserId();

			UserBalanceEntity userBalanceEntity = userBalanceRepository.findByUserEntity_UserIdIs(userId);
			userBalanceEntity.setNominal(userBalanceEntity.getNominal()+lastData.getTotal());
			userBalanceRepository.save(userBalanceEntity);

			BookDetailEntity bookDetailEntity = lastData.getBorrowedBookEntity().getBookDetailEntity();
			bookDetailEntity.setStatusBookDetail("Available");
			bookDetailRepository.save(bookDetailEntity);

			borrowedBookEntity.setStatusBook("Canceled");
			borrowedBookRepository.save(borrowedBookEntity);

			UserEntity admin = userRepository.findByUserId(1);
			HistoryBalanceEntity historyBalanceEntity = new HistoryBalanceEntity();
			historyBalanceEntity.setUserBalanceEntity(userBalanceEntity);
			historyBalanceEntity.setUserEntity(admin); //check by admin
			historyBalanceEntity.setStatusPayment("Success");
			historyBalanceEntity.setNominal(lastData.getTotal());
			historyBalanceEntity.setDateAcc(now);
			historyBalanceEntity.setStatus(true);
			historyBalanceEntity.setPaymentMethod("Cancel Invoice");
			historyBalanceRepository.save(historyBalanceEntity);

			InvoiceEntity invoiceEntity = lastData.getInvoiceEntity();
			invoiceEntity.setStatusInvoice("Canceled");
			invoiceRepository.save(invoiceEntity);

			result.setStatus(HttpStatus.OK.value());
			result.setMessage("Rent has been Canceled, and balance has been add to user");
			result.setData(null);
            return result;
		}

		if(borrowedBookEntity.getStatusBook().equalsIgnoreCase("Waiting Given By Librarian")) {
			borrowedBookEntity.setStatusBook("Canceled");
			borrowedBookRepository.save(borrowedBookEntity);
		}

		InvoiceEntity invoiceEntity = lastData.getInvoiceEntity();
		invoiceEntity.setStatusInvoice("Canceled");
		invoiceRepository.save(invoiceEntity);

		result.setStatus(HttpStatus.OK.value());
		result.setMessage("Rent has been Canceled!");
		result.setData(borrowedBookEntity);
        return result;
	}

	@Override
	public StatusMessageDto returnBook(UserPrincipal userPrincipal, List<ReturnBookDto> dtos) {
		StatusMessageDto result = new StatusMessageDto();
		List<InvoiceDetailDto> late = new ArrayList<>();
		Integer countOnTime = 0;
		Double grandTotal = 0.0;
		LocalDateTime now = LocalDateTime.now();
		for (ReturnBookDto e: dtos) {
			BorrowedBookEntity borrowedBookEntity = borrowedBookRepository.findByBorrowedBookId(e.getBorrowBookId());
			if(e.getBorrowBookId() == null){
				continue;
			}

			LocalDateTime threshold = borrowedBookEntity.getThreshold();
			Duration duration = Duration.between(threshold, now);
			Long diffDays = duration.toDays();
//			Terlambat
			if(diffDays > 0){
				InvoiceDetailDto dto = new InvoiceDetailDto();
				dto.setLate(diffDays);
				dto.setType("Denda Keterlambatan sebanayak "+diffDays + " Hari");
				double total = diffDays * 2000;
				grandTotal = grandTotal+total;
				dto.setGrandTotal(total);
				dto.setBorrowedBookEntity(borrowedBookEntity);
				late.add(dto);
			}else{
				countOnTime++;
			}
			borrowedBookEntity.setStatusBook("Waiting Taken By Librarian");
			borrowedBookRepository.save(borrowedBookEntity);
		}

		if(!late.isEmpty()){
//			add Invoice
			InvoiceEntity invoiceEntity = invoiceService.addInvoice(grandTotal,"Denda","Waiting Check By Librarian", userPrincipal);
			InvoiceDetailEntity invoiceDetailEntity = invoiceDetailService.addInvoiceDetails(invoiceEntity, late);
		}else if ( countOnTime >= 1){
//			do nothing
		}else{
			result.setStatus(HttpStatus.BAD_REQUEST.value());
			result.setMessage("Belum ada data yang dipilih");
			result.setData(null);
			return result;
		}

		result.setStatus(HttpStatus.OK.value());
		result.setMessage("Berhasil, harap menunggu Librarian");
		result.setData(null);

		return result;
	}

}