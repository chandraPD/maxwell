package library.maxwell.module.book.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cloudinary.utils.ObjectUtils;

import library.maxwell.config.CloudinaryConfig;
import library.maxwell.config.security.auth.UserPrincipal;
import library.maxwell.module.book.dto.BookDto;
import library.maxwell.module.book.dto.BookDto2;
import library.maxwell.module.book.dto.StatusMessageDto;
import library.maxwell.module.book.dto.UpdateQtyBookDto;
import library.maxwell.module.book.entity.AuthorEntity;
import library.maxwell.module.book.entity.BookEntity;
import library.maxwell.module.book.entity.CategoryEntity;
import library.maxwell.module.book.repository.AuthorRepository;
import library.maxwell.module.book.repository.BookDetailRepository;
import library.maxwell.module.book.repository.BookRepository;
import library.maxwell.module.book.repository.CategoryRepository;
import library.maxwell.module.log.entity.LogEntity;
import library.maxwell.module.log.repository.LogRepository;
import library.maxwell.module.user.entity.UserEntity;
import library.maxwell.module.user.repository.UserRepository;

@Service
@Transactional
public class BookServiceImpl implements BookService {
	
	@Autowired
	BookRepository bookRepository;
	
	@Autowired
	BookDetailRepository bookdetailRepository;
	
	@Autowired
	CategoryRepository categoryRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	AuthorRepository authorRepository;
	
	@Autowired
	LogRepository logRepository;
	
	@Autowired
	private CloudinaryConfig cloudinary;
	
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
		BookDto2 result = convertToBookDto(bookEntities);
		return ResponseEntity.ok(result);
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
	public ResponseEntity<?> getQtyBook() {
		// TODO Auto-generated method stub
		BookEntity bookEntity = bookRepository.getMaxQtyBook();
		return ResponseEntity.ok(bookEntity);
	}
	
	@Override
	public ResponseEntity<?> findByTitle(String title) {
		// TODO Auto-generated method stub
		List<BookEntity> bookEntities = bookRepository.findByTitleLike( "%" + title + "%");
		return ResponseEntity.ok(bookEntities);			
	}
	
	
	@Override
	public ResponseEntity<?> getBookByCategoryId(Integer categoryId) {
		// TODO Auto-generated method stub
		List<BookEntity> bookEntities = bookRepository.getBookByCategory(categoryId);
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
	public ResponseEntity<?> getBookByCategoryAndYear(Integer categoryId, String yearBook) {
		// TODO Auto-generated method stub
		List<BookEntity> bookEntities = bookRepository.getBookByCategoryAndYear(categoryId, yearBook);
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
	public ResponseEntity<?> getBookByYear(String yearBook) {
		// TODO Auto-generated method stub
		List<BookEntity> bookEntities = bookRepository.getBookByYear(yearBook);
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
		BookEntity bookExists = bookRepository.findByTitle(dto.getTitle());
		LogEntity logEntity = new LogEntity();
		LocalDateTime now = LocalDateTime.now();
		UserEntity createdByEntity = userRepository.findById(userId).get();
		UserEntity updatedByEntity = userRepository.findById(userId).get();
		CategoryEntity categoryEntity = categoryRepository.findById(dto.getCategoryId()).get();
		AuthorEntity authorEntity = authorRepository.findById(dto.getAuthorId()).get();
		
		Boolean existsByTitle = bookRepository.existsByTitle(dto.getTitle());
		Boolean existsByStatus = bookRepository.existsByStatusTitle(dto.getTitle());
		
		if (existsByTitle == true && existsByStatus && true) {
			StatusMessageDto<CategoryEntity> result = new StatusMessageDto<>();
			result.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
			result.setMessage("Book already exist!");
			result.setData(null);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
		} else if(existsByTitle == true && existsByStatus == false) { 
			bookExists.setStatus(true);
			bookExists.setAuthorEntity(authorEntity);
			bookExists.setCategoryEntity(categoryEntity);
			bookExists.setDescription(dto.getDescription());
			
			byte[] imgDetail = Base64.getMimeDecoder().decode(dto.getImgDetail());
			byte[] imgBanner = Base64.getMimeDecoder().decode(dto.getImgBanner());
			
			Map uploadResultDetail = cloudinary.upload(imgDetail, ObjectUtils.asMap("resourcetype", "auto"));
			bookExists.setImgDetail(uploadResultDetail.get("url").toString());
			
			Map uploadResultBanner = cloudinary.upload(imgBanner, ObjectUtils.asMap("resourcetype", "auto"));
			bookExists.setImgBanner(uploadResultBanner.get("url").toString());
			
			bookExists.setPublishDate(dto.getPublishDate());
			
			bookRepository.save(bookExists);
			logEntity.setAction("Post");
			logEntity.setDateTime(now);
			logEntity.setDescription("Menambahkan Buku: " + dto.getTitle());
			logEntity.setStatus(true);
			logEntity.setUserEntity(createdByEntity);
			logRepository.save(logEntity);
			
			return ResponseEntity.ok(bookEntity);
		}
		else {
			
			bookEntity.setAuthorEntity(authorEntity);
			bookEntity.setQty(0);
			bookEntity.setCategoryEntity(categoryEntity);
			bookEntity.setCreatedByEntity(createdByEntity);
			bookEntity.setUpdatedByEntity(updatedByEntity);
			bookRepository.save(bookEntity);
			
			logEntity.setAction("Post");
			logEntity.setDateTime(now);
			logEntity.setDescription("Menambahkan Buku: " + dto.getTitle());
			logEntity.setStatus(true);
			logEntity.setUserEntity(createdByEntity);
			logRepository.save(logEntity);
			
			return ResponseEntity.ok(bookEntity);
		}
		
	}

	@Override
	public ResponseEntity<?> updateBook(UserPrincipal userPrincipal, Integer id, BookDto dto) {
		// TODO Auto-generated method stub
		Integer userId = userPrincipal.getId();
		BookEntity bookEntity = bookRepository.findById(id).get();
		CategoryEntity categoryEntity = categoryRepository.findById(dto.getCategoryId()).get();
		AuthorEntity authorEntity = authorRepository.findById(dto.getAuthorId()).get();
		UserEntity updatedByEntity = userRepository.findById(userId).get();
		LocalDateTime dateTime = LocalDateTime.now();
		LogEntity logEntity = new LogEntity();
		
		bookEntity.setTitle(dto.getTitle());
		bookEntity.setDescription(dto.getDescription());
		
		System.out.println("Book Banner: " + bookEntity.getImgBanner());
		System.out.println("DTO Banner: "+ dto.getImgBanner());
		
		System.out.println("Book Detail: " + bookEntity.getImgDetail());
		System.out.println("DTO Detail: "+ dto.getImgDetail());
		
		if(bookEntity.getImgBanner().equals(dto.getImgBanner()) && bookEntity.getImgDetail().equals(dto.getImgDetail())) {
			System.out.println(bookEntity.getImgBanner().equals(dto.getImgBanner()) && bookEntity.getImgDetail().equals(dto.getImgDetail()));
			bookEntity.setImgBanner(dto.getImgBanner());
			bookEntity.setImgDetail(dto.getImgDetail());
		} else if(!bookEntity.getImgBanner().equals(dto.getImgBanner()) && bookEntity.getImgDetail().equals(dto.getImgDetail())) {
			byte[] imgBanner = Base64.getMimeDecoder().decode(dto.getImgBanner());
			Map uploadResultBanner = cloudinary.upload(imgBanner, ObjectUtils.asMap("resourcetype", "auto"));
			bookEntity.setImgBanner(uploadResultBanner.get("url").toString());
			
			bookEntity.setImgDetail(dto.getImgDetail());
		} else if(bookEntity.getImgBanner().equals(dto.getImgBanner()) && !bookEntity.getImgDetail().equals(dto.getImgDetail())) {
			bookEntity.setImgBanner(dto.getImgBanner());
			
			byte[] imgDetail = Base64.getMimeDecoder().decode(dto.getImgDetail());
			Map uploadResultDetail = cloudinary.upload(imgDetail, ObjectUtils.asMap("resourcetype", "auto"));
			bookEntity.setImgDetail(uploadResultDetail.get("url").toString());			
		} else {
			byte[] imgDetail = Base64.getMimeDecoder().decode(dto.getImgDetail());
			byte[] imgBanner = Base64.getMimeDecoder().decode(dto.getImgBanner());
			
			Map uploadResultDetail = cloudinary.upload(imgDetail, ObjectUtils.asMap("resourcetype", "auto"));
			bookEntity.setImgDetail(uploadResultDetail.get("url").toString());
			
			Map uploadResultBanner = cloudinary.upload(imgBanner, ObjectUtils.asMap("resourcetype", "auto"));
			bookEntity.setImgBanner(uploadResultBanner.get("url").toString());
		}
		

		
		bookEntity.setUpdatedAt(dateTime);
		bookEntity.setPublishDate(dto.getPublishDate());
		bookEntity.setAuthorEntity(authorEntity);
		
		bookEntity.setCategoryEntity(categoryEntity);
		bookEntity.setUpdatedByEntity(updatedByEntity);
		bookRepository.save(bookEntity);
		
		logEntity.setAction("Update");
		logEntity.setDateTime(dateTime);
		logEntity.setDescription("Mengupdate Buku: " + dto.getTitle());
		logEntity.setStatus(true);
		logEntity.setUserEntity(updatedByEntity);
		logRepository.save(logEntity);
		
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
	public ResponseEntity<?> deleteBook(UserPrincipal userPrincipal, Integer id) {
		// TODO Auto-generated method stub
		BookEntity bookEntity = bookRepository.findById(id).get();
		UserEntity userEntity = userRepository.findById(userPrincipal.getId()).get();
		LocalDateTime dateTime = LocalDateTime.now();
		LogEntity logEntity = new LogEntity();
		bookEntity.setStatus(false);
		bookRepository.save(bookEntity);
		logEntity.setAction("Delete");
		logEntity.setDateTime(dateTime);
		logEntity.setDescription("Menghapus Buku: " + bookEntity.getTitle());
		logEntity.setStatus(true);
		logEntity.setUserEntity(userEntity);
		logRepository.save(logEntity);
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
		
		byte[] imgDetail = Base64.getMimeDecoder().decode(dto.getImgDetail());
		byte[] imgBanner = Base64.getMimeDecoder().decode(dto.getImgBanner());
		
		Map uploadResultDetail = cloudinary.upload(imgDetail, ObjectUtils.asMap("resourcetype", "auto"));
		bookEntity.setImgDetail(uploadResultDetail.get("url").toString());
		
		Map uploadResultBanner = cloudinary.upload(imgBanner, ObjectUtils.asMap("resourcetype", "auto"));
		bookEntity.setImgBanner(uploadResultBanner.get("url").toString());
		
		bookEntity.setQty(dto.getQty());
		bookEntity.setPublishDate(dto.getPublishDate());
//		bookEntity.setAuthor(dto.getAuthor());
		return bookEntity;
	}
	
	public BookDto2 convertToBookDto(BookEntity data) {
		BookDto2 result = new BookDto2();
		
		result.setBookId(data.getBookId());
		result.setBookCode(data.getBookCode());
		result.setCategoryEntity(data.getCategoryEntity());
		result.setTitle(data.getTitle());
		result.setDescription(data.getDescription());
		result.setImgBanner(data.getImgBanner());
		result.setImgDetail(data.getImgDetail());
		result.setAuthorEntity(data.getAuthorEntity());
		result.setQty(data.getQty());
		result.setPublishDate(data.getPublishDate().toString());
		
		Long count = bookdetailRepository.countByStatusIsTrueAndStatusBookDetailIsAndBookEntity_BookIdIs("Available",data.getBookId());
		if(count == 0) {
			result.setStatusBook("Unavailable");
		} else {
			result.setStatusBook("Available");
		}
		return result;
	}

	@Override
	public ResponseEntity<?> getYear() {
		// TODO Auto-generated method stub
		List<String> yearList = bookRepository.getYear();
		return ResponseEntity.ok(yearList);
	}

	@Override
	public Integer getCount(Integer id) {
		Integer count=bookRepository.countBookEntityByAuthorEntityAuthorId(id);
		return count;
	}

	@Override
	public Integer getCountCategory(Integer categoryId) {
		// TODO Auto-generated method stub
		Integer countCategory = bookRepository.countCategory(categoryId);
		return countCategory;
	}

	
}
