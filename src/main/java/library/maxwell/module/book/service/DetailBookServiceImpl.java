package library.maxwell.module.book.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import library.maxwell.config.security.auth.UserPrincipal;
import library.maxwell.module.book.dto.BookDetailDto;
import library.maxwell.module.book.dto.StatusMessageDto;
import library.maxwell.module.book.entity.BookDetailEntity;
import library.maxwell.module.book.entity.BookEntity;
import library.maxwell.module.book.entity.CategoryEntity;
import library.maxwell.module.book.repository.BookDetailRepository;
import library.maxwell.module.book.repository.BookRepository;
import library.maxwell.module.log.entity.LogEntity;
import library.maxwell.module.log.repository.LogRepository;
import library.maxwell.module.user.entity.UserEntity;
import library.maxwell.module.user.repository.UserRepository;

@Service
@Transactional
public class DetailBookServiceImpl implements BookDetailService{
	
	@Autowired
	BookDetailRepository bookDetailRepository;
	
	@Autowired
	BookRepository bookRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	LogRepository logRepository;
	
	@Override
	public ResponseEntity<?> getAllDetail() {
		// TODO Auto-generated method stub
		List<BookDetailEntity> bookDetailEntities = bookDetailRepository.findAll();
		return ResponseEntity.ok(bookDetailEntities);
	}

	@Override
	public ResponseEntity<?> getAllActive() {
		// TODO Auto-generated method stub
		List<BookDetailEntity> bookDetailEntities = bookDetailRepository.findAllActive();
		return ResponseEntity.ok(bookDetailEntities);
	}

	@Override
	public ResponseEntity<?> getAllInactive() {
		// TODO Auto-generated method stub
		List<BookDetailEntity> bookDetailEntities = bookDetailRepository.findAllInactive();
		return ResponseEntity.ok(bookDetailEntities);
	}
	
	@Override
	public ResponseEntity<?> getByBookId(Integer bookId) {
		// TODO Auto-generated method stub
		List<BookDetailEntity> bookDetailEntities = bookDetailRepository.findByBookEntityBookId(bookId);
		if(bookDetailEntities == null) {
			StatusMessageDto<CategoryEntity> result = new StatusMessageDto<>();
			result.setStatus(HttpStatus.BAD_REQUEST.value());
			result.setMessage("Data Not Found!");
			result.setData(null);
			return ResponseEntity.ok(result);
		} else {
			return ResponseEntity.ok(bookDetailEntities);
		}
	}

	@Override
	public ResponseEntity<?> getDetailBook(Integer id) {
		// TODO Auto-generated method stub
		BookDetailEntity bookDetailEntity = bookDetailRepository.findById(id).get();
		
		if(bookDetailEntity == null) {
			StatusMessageDto<CategoryEntity> result = new StatusMessageDto<>();
			result.setStatus(HttpStatus.BAD_REQUEST.value());
			result.setMessage("Data Not Found!");
			result.setData(null);
			return ResponseEntity.ok(result);
		} else {
			return ResponseEntity.ok(bookDetailEntity);
		}
	}

	@Override
	public ResponseEntity<?> getByTypeofDamage(String typeOfDamage) {
		// TODO Auto-generated method stub
		List<BookDetailEntity> bookDetailEntities = bookDetailRepository.findByTypeOfDamage(typeOfDamage);
		
		if(bookDetailEntities == null) {
			StatusMessageDto<CategoryEntity> result = new StatusMessageDto<>();
			result.setStatus(HttpStatus.BAD_REQUEST.value());
			result.setMessage("Data Not Found!");
			result.setData(null);
			return ResponseEntity.ok(result);
		} else {
			return ResponseEntity.ok(bookDetailEntities);
		}
	}

	@Override
	public ResponseEntity<?> addDetailBook(UserPrincipal userPrincipal, BookDetailDto dto) {
		// TODO Auto-generated method stub
		BookDetailEntity bookDetailEntity = convertToBookDetailEntity(dto);
		BookEntity bookEntity = bookRepository.findById(dto.getBookId()).get();
		LogEntity logEntity = new LogEntity();
		UserEntity userEntity = userRepository.findByUserId(userPrincipal.getId());
		LocalDateTime now = LocalDateTime.now();
		bookDetailEntity.setBookEntity(bookEntity);
		bookDetailRepository.save(bookDetailEntity);
		logEntity.setAction("Post");
		logEntity.setDateTime(now);
		logEntity.setDescription("Menambahkan Detail Buku: " + dto.getBookId());
		logEntity.setStatus(true);
		logEntity.setUserEntity(userEntity);
		logRepository.save(logEntity);
		return ResponseEntity.ok(bookDetailEntity);
	}

	@Override
	public ResponseEntity<?> updateDetailBook(UserPrincipal userPrincipal, Integer id, BookDetailDto dto) {
		// TODO Auto-generated method stub
		BookDetailEntity bookDetailEntity = bookDetailRepository.findById(id).get();
		BookEntity bookEntity = bookRepository.findById(dto.getBookId()).get();
		LogEntity logEntity = new LogEntity();
		UserEntity userEntity = userRepository.findByUserId(userPrincipal.getId());
		LocalDateTime now = LocalDateTime.now();
		bookDetailEntity.setBookEntity(bookEntity);
		bookDetailEntity.setTypeOfDamage(dto.getTypeOfDamage());
		bookDetailEntity.setDescOfDamage(dto.getDescOfDamage());
		bookDetailEntity.setStatusBookDetail(dto.getStatusBookDetail());
		
		bookDetailRepository.save(bookDetailEntity);
		
		logEntity.setAction("Update");
		logEntity.setDateTime(now);
		logEntity.setDescription("Mengupdate Detail Buku: " + dto.getBookId());
		logEntity.setStatus(true);
		logEntity.setUserEntity(userEntity);
		logRepository.save(logEntity);
		return ResponseEntity.ok(bookDetailEntity);
	}

	@Override
	public ResponseEntity<?> deleteDetailBook(UserPrincipal userPrincipal, Integer id) {
		// TODO Auto-generated method stub
		BookDetailEntity bookDetailEntity = bookDetailRepository.findById(id).get();
		LogEntity logEntity = new LogEntity();
		UserEntity userEntity = userRepository.findByUserId(userPrincipal.getId());
		LocalDateTime now = LocalDateTime.now();
		bookDetailEntity.setStatus(false);
		bookDetailRepository.save(bookDetailEntity);
		
		logEntity.setAction("Update");
		logEntity.setDateTime(now);
		logEntity.setDescription("Mengupdate Detail Buku: " + bookDetailEntity.getBookDetailId());
		logEntity.setStatus(true);
		logEntity.setUserEntity(userEntity);
		logRepository.save(logEntity);
		
		return ResponseEntity.ok(bookDetailEntity);
	}
	
	public BookDetailEntity convertToBookDetailEntity(BookDetailDto dto) {
		BookDetailEntity bookDetailEntity = new BookDetailEntity();
		
		DateTimeFormatter getYear = DateTimeFormatter.ofPattern("yy");
		String year = LocalDate.now().format(getYear);
		
		// mencari nomor terakhir dari book detail code
		String lastBookDetailCode = bookDetailRepository.getLastBookDetailCode();
		String seq;
		
		if(lastBookDetailCode == null) {
			seq = String.format("%04d", 1);
		} else {
			Integer number = Integer.parseInt(lastBookDetailCode.substring(5, 8)) + 1;
			seq = String.format("%04d", number);
		}
		
		String bookDetailCode = "BD" + year + seq;
		bookDetailEntity.setBookDetailCode(bookDetailCode);
		bookDetailEntity.setStatusBookDetail(dto.getStatusBookDetail());
		bookDetailEntity.setTypeOfDamage(dto.getTypeOfDamage());
		bookDetailEntity.setDescOfDamage(dto.getDescOfDamage());
		return bookDetailEntity;
	}

	

}
