package library.maxwell.module.book.service;



import org.springframework.http.ResponseEntity;

import library.maxwell.config.security.auth.UserPrincipal;
import library.maxwell.module.book.dto.BookDto;
import library.maxwell.module.book.dto.UpdateQtyBookDto;

public interface BookService {
	ResponseEntity<?> getBook();
	ResponseEntity<?> getBookById(Integer id);
	ResponseEntity<?> getActiveBook();
	ResponseEntity<?> getInactiveBook();
	ResponseEntity<?> findByTitle(String title);
	ResponseEntity<?> findByAuthor(String author);
	ResponseEntity<?> addBook(UserPrincipal userPrincipal, BookDto dto);
	ResponseEntity<?> updateBook(UserPrincipal userPrincipal, Integer id, BookDto dto);
	ResponseEntity<?> deleteBook(Integer id);
	ResponseEntity<?> updateQtyBook(Integer id, UpdateQtyBookDto dto);
	ResponseEntity<?> getRecentFive();
	ResponseEntity<?> getOldestFive();
	ResponseEntity<?> getRecommendedDetail(Integer categoryId, Integer bookId);
}
