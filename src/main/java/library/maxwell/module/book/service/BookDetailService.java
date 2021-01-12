package library.maxwell.module.book.service;

import org.springframework.http.ResponseEntity;

import library.maxwell.config.security.auth.UserPrincipal;
import library.maxwell.module.book.dto.BookDetailDto;

public interface BookDetailService {
	public ResponseEntity<?> getAllDetail();
	public ResponseEntity<?> getAllActive();
	public ResponseEntity<?> getAllInactive();
	public ResponseEntity<?> getDetailBook(Integer id);
	public ResponseEntity<?> getByBookId(Integer bookId);
	public ResponseEntity<?> getByTypeofDamage(String typeOfDamage);
	public ResponseEntity<?> addDetailBook(UserPrincipal userPrincipal, BookDetailDto dto);
	public ResponseEntity<?> updateDetailBook(UserPrincipal userPrincipal, Integer id, BookDetailDto dto);
	public ResponseEntity<?> deleteDetailBook(UserPrincipal userPrincipal, Integer id);
	
}
