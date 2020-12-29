package library.maxwell.module.book.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import library.maxwell.module.book.dto.BookDetailDto;
import library.maxwell.module.book.dto.StatusMessageDto;
import library.maxwell.module.book.entity.BookDetailEntity;
import library.maxwell.module.book.entity.BookEntity;
import library.maxwell.module.book.entity.CategoryEntity;
import library.maxwell.module.book.repository.BookDetailRepository;
import library.maxwell.module.book.repository.BookRepository;

@Service
@Transactional
public class DetailBookServiceImpl implements BookDetailService{
	
	@Autowired
	BookDetailRepository bookDetailRepository;
	
	@Autowired
	BookRepository bookRepository;
	
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
	public ResponseEntity<?> addDetailBook(BookDetailDto dto) {
		// TODO Auto-generated method stub
		BookDetailEntity bookDetailEntity = convertToBookDetailEntity(dto);
		BookEntity bookEntity = bookRepository.findById(dto.getBookId()).get();
		bookDetailEntity.setBookEntity(bookEntity);
		bookDetailRepository.save(bookDetailEntity);
		return ResponseEntity.ok(bookDetailEntity);
	}

	@Override
	public ResponseEntity<?> updateDetailBook(Integer id, BookDetailDto dto) {
		// TODO Auto-generated method stub
		BookDetailEntity bookDetailEntity = bookDetailRepository.findById(id).get();
		BookEntity bookEntity = bookRepository.findById(dto.getBookId()).get();
		bookDetailEntity.setBookEntity(bookEntity);
		bookDetailEntity.setTypeOfDamage(dto.getTypeOfDamage());
		bookDetailEntity.setDescOfDamage(dto.getDescOfDamage());
		
		bookDetailRepository.save(bookDetailEntity);
		return ResponseEntity.ok(bookDetailEntity);
	}

	@Override
	public ResponseEntity<?> deleteDetailBook(Integer id) {
		// TODO Auto-generated method stub
		BookDetailEntity bookDetailEntity = bookDetailRepository.findById(id).get();
		bookDetailEntity.setStatus(false);
		bookDetailRepository.save(bookDetailEntity);
		
		return ResponseEntity.ok(bookDetailEntity);
	}
	
	public BookDetailEntity convertToBookDetailEntity(BookDetailDto dto) {
		BookDetailEntity bookDetailEntity = new BookDetailEntity();
		bookDetailEntity.setTypeOfDamage(dto.getTypeOfDamage());
		bookDetailEntity.setDescOfDamage(dto.getDescOfDamage());
		return bookDetailEntity;
	}

}
