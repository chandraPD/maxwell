package library.maxwell.module.book.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
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
import library.maxwell.module.invoice.service.InvoiceDetailServiceImpl;
import library.maxwell.module.invoice.service.InvoiceService;
import library.maxwell.module.invoice.service.InvoiceServiceImpl;
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
		// TODO Auto-generated method stub
		
		Integer userId = userPrincipal.getId();
		
		List<BorrowedBookEntity> borrowedBookEntities = borrowedBookRepository.findAllByStatusIsTrueAndUserIdEntity_UserIdIs(userId);
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
		BorrowedBookEntity borrowedBookEntity = converToBorrowedBookEntity(dto);
		StatusMessageDto<BorrowedBookEntity> result = new StatusMessageDto<>();
		
		Integer idUser = userPrincipal.getId();
		
		UserEntity userEntity = userRepository.findById(idUser).get();
		borrowedBookEntity.setUserIdEntity(userEntity);
		borrowedBookRepository.save(borrowedBookEntity);
//		Add Invoice
		InvoiceEntity invoiceEntity =  invoiceService.addInvoice(userPrincipal);
//		Get ID Invoice
		Integer idInvoice = invoiceEntity.getInvoiceId();
//		Add Invoice Detail base on id_invoice and borrowed_book_id
		
		InvoiceDetailEntity invoiceDetailEntity = invoiceDetailService.addInvoiceDetail(invoiceEntity, borrowedBookEntity);
		
		if(invoiceDetailEntity == null) {
			result.setMessage("Gagal meminjam Buku, silahkan hubungi administrator");
			result.setStatus(HttpStatus.BAD_REQUEST.value());
			result.setData(null);
		}else {
			result.setMessage("Buku berhasil dipinjam");
			result.setStatus(HttpStatus.OK.value());
			result.setData(borrowedBookEntity);
		}
		return result;
	}

//	method 
	public BorrowedBookEntity converToBorrowedBookEntity (BorrowBookDto dto) {
		
		BorrowedBookEntity borrowedBookEntity = new BorrowedBookEntity();
		
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
		
		borrowedBookEntity.setDp((double) 5000);
		borrowedBookEntity.setGrandTotal((double) 5000);
//		return date pertama kali isi nya null
		borrowedBookEntity.setStatusBook("Waiting Given By Librarian");

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy hh:mm");
		LocalDateTime threshold = LocalDateTime.parse(dto.getReturnedDate());
		borrowedBookEntity.setThreshold(threshold);
		Integer idBookDetail = dto.getBookDetailId();
		BookDetailEntity bookDetailEntity = bookDetailRepository.findByBookDetailId(idBookDetail);
		borrowedBookEntity.setBookDetailEntity(bookDetailEntity);
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
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy hh:mm");
		
		dto.setBorrowedBookCode(borrowedBookEntity.getBorrowedBookCode());
		dto.setBorrower(borrower);
		dto.setTitle(bookEntity.getTitle());
		dto.setGivenBy(givenBy);
		dto.setTakenBy(takenBy);
		dto.setBorrowedDate(borrowedBookEntity.getBorrowedDate().format(formatter));
		
		if(borrowedBookEntity.getReturnedDate() == null) {
			dto.setReturnedDate("-");
		}else {
			dto.setReturnedDate(borrowedBookEntity.getReturnedDate().format(formatter).toString());
		}
		
		dto.setStatusBook(borrowedBookEntity.getStatusBook());
		dto.setThreshold(borrowedBookEntity.getThreshold().format(formatter));
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
	
}
