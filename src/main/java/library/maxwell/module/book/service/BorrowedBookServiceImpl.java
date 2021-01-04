package library.maxwell.module.book.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import library.maxwell.config.security.auth.UserPrincipal;
import library.maxwell.module.book.dto.BorrowBookDto;
import library.maxwell.module.book.entity.BookDetailEntity;
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
		return null;
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
		
		borrowedBookEntity.setDp((double) 5000);
		borrowedBookEntity.setGrandTotal((double) 5000);
//		return date pertama kali isi nya null
		borrowedBookEntity.setStatusBook("Waiting Given By Librarian");
		borrowedBookEntity.setThreshold(dto.getReturnedDate());
		Integer idBookDetail = dto.getBookDetailId();
		BookDetailEntity bookDetailEntity = bookDetailRepository.findByBookDetailId(idBookDetail);
		borrowedBookEntity.setBookDetailEntity(bookDetailEntity);
		return borrowedBookEntity;
	}

	
}
