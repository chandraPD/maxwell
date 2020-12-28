package library.maxwell.module.book.service;

import org.springframework.http.ResponseEntity;

import library.maxwell.module.book.dto.BookDetailDto;

public interface BookDetailService {
	public ResponseEntity<?> getAllDetail();
	public ResponseEntity<?> getAllActive();
	public ResponseEntity<?> getAllInactive();
	public ResponseEntity<?> getDetailBook(Integer id);
	public ResponseEntity<?> getByTypeofDamage(String typeOfDamage);
	public ResponseEntity<?> addDetailBook(BookDetailDto dto);
	public ResponseEntity<?> updateDetailBook(Integer id, BookDetailDto dto);
	public ResponseEntity<?> deleteDetailBook(Integer id);
	
}
