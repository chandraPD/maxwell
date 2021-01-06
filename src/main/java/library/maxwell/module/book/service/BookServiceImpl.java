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
import library.maxwell.module.book.dto.BookDto;
import library.maxwell.module.book.dto.StatusMessageDto;
import library.maxwell.module.book.dto.UpdateQtyBookDto;
import library.maxwell.module.book.entity.BookEntity;
import library.maxwell.module.book.entity.CategoryEntity;
import library.maxwell.module.book.repository.BookRepository;
import library.maxwell.module.book.repository.CategoryRepository;
import library.maxwell.module.user.entity.UserEntity;
import library.maxwell.module.user.repository.UserRepository;

@Service
@Transactional
public class BookServiceImpl implements BookService {
	
	@Autowired
	BookRepository bookRepository;
	
	@Autowired
	CategoryRepository categoryRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Override
	public ResponseEntity<?> getBook() {
		// TODO Auto-generated method stub
		List<BookEntity> bookEntities = bookRepository.findAll();
		return ResponseEntity.ok(bookEntities);
	}
	
	@Override
	public ResponseEntity<?> getBookById(Integer id) {
		// TODO Auto-generated method stub
		BookEntity bookEntities = bookRepository.findById(id).get();
		return ResponseEntity.ok(bookEntities);
	}

	@Override
	public ResponseEntity<?> getActiveBook() {
		// TODO Auto-generated method stub
		List<BookEntity> bookEntities = bookRepository.findActiveBook();
		return ResponseEntity.ok(bookEntities);
	}

	@Override
	public ResponseEntity<?> getInactiveBook() {
		// TODO Auto-generated method stub
		List<BookEntity> bookEntities = bookRepository.findInactiveBook();
		return ResponseEntity.ok(bookEntities);
	}
	
	@Override
	public ResponseEntity<?> getRecentFive() {
		// TODO Auto-generated method stub
		List<BookEntity> bookEntities = bookRepository.getRecentFive();
		return ResponseEntity.ok(bookEntities);
	}
	
	@Override
	public ResponseEntity<?> getOldestFive() {
		// TODO Auto-generated method stub
		List<BookEntity> bookEntities = bookRepository.getOldestFive();
		return ResponseEntity.ok(bookEntities);
	}
	
	@Override
	public ResponseEntity<?> getRecommendedDetail(Integer categoryId, Integer bookId) {
		// TODO Auto-generated method stub
		List<BookEntity> bookEntities = bookRepository.getRecommendedDetail(categoryId, bookId);
		return ResponseEntity.ok(bookEntities);
	}
	
	@Override
	public ResponseEntity<?> findByTitle(String title) {
		// TODO Auto-generated method stub
		List<BookEntity> bookEntities = bookRepository.findByTitleLike( "%" + title + "%");
		StatusMessageDto<BookEntity> result = new StatusMessageDto<>();
		
		if(bookEntities == null) {
			result.setStatus(HttpStatus.BAD_REQUEST.value());
			result.setMessage("Book not found!");
			result.setData(null);
			return ResponseEntity.ok(result);
		} else {
			return ResponseEntity.ok(bookEntities);
		}		
	}

	@Override
	public ResponseEntity<?> findByAuthor(String author) {
		// TODO Auto-generated method stub
		List<BookEntity> bookEntities = bookRepository.findByAuthor(author);
		StatusMessageDto<BookEntity> result = new StatusMessageDto<>();
		
		if(bookEntities == null) {
			result.setStatus(HttpStatus.BAD_REQUEST.value());
			result.setMessage("Book not found!");
			result.setData(null);
			return ResponseEntity.ok(result);
		}else {
			return ResponseEntity.ok(bookEntities);
		}	
	}

	@Override
	public ResponseEntity<?> addBook(UserPrincipal userPrincipal, BookDto dto) {
		// TODO Auto-generated method stub
		Integer userId = userPrincipal.getId();
		BookEntity bookEntity = convertToBookEntity(dto);
		
		Boolean existsByTitle = bookRepository.existsByTitle(dto.getTitle());
		
		if (existsByTitle) {
			StatusMessageDto<CategoryEntity> result = new StatusMessageDto<>();
			result.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
			result.setMessage("Book already exist!");
			result.setData(null);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
		} else {
			UserEntity createdByEntity = userRepository.findById(userId).get();
			UserEntity updatedByEntity = userRepository.findById(userId).get();
			CategoryEntity categoryEntity = categoryRepository.findById(dto.getCategoryId()).get();
			bookEntity.setQty(0);
			bookEntity.setCategoryEntity(categoryEntity);
			bookEntity.setCreatedByEntity(createdByEntity);
			bookEntity.setUpdatedByEntity(updatedByEntity);
			bookRepository.save(bookEntity);
			
			return ResponseEntity.ok(bookEntity);
		}
		
	}

	@Override
	public ResponseEntity<?> updateBook(UserPrincipal userPrincipal, Integer id, BookDto dto) {
		// TODO Auto-generated method stub
		Integer userId = userPrincipal.getId();
		BookEntity bookEntity = bookRepository.findById(id).get();
		CategoryEntity categoryEntity = categoryRepository.findById(dto.getCategoryId()).get();
		UserEntity updatedByEntity = userRepository.findById(userId).get();
		LocalDateTime dateTime = LocalDateTime.now();
		
		bookEntity.setTitle(dto.getTitle());
		bookEntity.setDescription(dto.getDescription());
		bookEntity.setImgBanner(dto.getImgBanner());
		bookEntity.setImgDetail(dto.getImgDetail());
		bookEntity.setUpdatedAt(dateTime);
		bookEntity.setStatusBook(dto.getStatusBook());
		bookEntity.setPublishDate(dto.getPublishDate());
		bookEntity.setAuthor(dto.getAuthor());
		
		bookEntity.setCategoryEntity(categoryEntity);
		bookEntity.setUpdatedByEntity(updatedByEntity);
		bookRepository.save(bookEntity);
		
		return ResponseEntity.ok(bookEntity);
	}
	
	@Override
	public ResponseEntity<?> updateQtyBook(Integer id, UpdateQtyBookDto dto) {
		// TODO Auto-generated method stub
		BookEntity bookEntity = bookRepository.findById(id).get();
		bookEntity.setQty(dto.getQty());
		
		bookRepository.save(bookEntity);
		
		return ResponseEntity.ok(bookEntity);
	}

	@Override
	public ResponseEntity<?> deleteBook(Integer id) {
		// TODO Auto-generated method stub
		BookEntity bookEntity = bookRepository.findById(id).get();
		bookEntity.setStatus(false);
		bookRepository.save(bookEntity);
		return ResponseEntity.ok(bookEntity);
	}

	public BookEntity convertToBookEntity(BookDto dto) {
		BookEntity bookEntity = new BookEntity();

		DateTimeFormatter getYearFull = DateTimeFormatter.ofPattern("yyyy");
		DateTimeFormatter getYear = DateTimeFormatter.ofPattern("yy");
		String year = LocalDate.now().format(getYear);
		Integer yearFull = Integer.parseInt(LocalDate.now().format(getYearFull));
		
		// mencari nomor terakhir dari book code
		String lastBookCode = bookRepository.getLastBookCode(yearFull);
		String seq;
		
		if(lastBookCode == null) {
			seq = String.format("%04d", 1);
		} else {
			Integer number = Integer.parseInt(lastBookCode.substring(4, 7)) + 1;
			seq = String.format("%04d", number);
		}
		
		String bookCode = "B" + year + seq;
		bookEntity.setBookCode(bookCode);
		
		
		bookEntity.setTitle(dto.getTitle());
		bookEntity.setDescription(dto.getDescription());
		bookEntity.setImgBanner(dto.getImgBanner());
		bookEntity.setImgDetail(dto.getImgDetail());
		bookEntity.setQty(dto.getQty());
		bookEntity.setStatusBook(dto.getStatusBook());
		bookEntity.setPublishDate(dto.getPublishDate());
		bookEntity.setAuthor(dto.getAuthor());
		return bookEntity;
	}

	

	
}
