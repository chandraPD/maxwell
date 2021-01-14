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
	ResponseEntity<?> addBook(UserPrincipal userPrincipal, BookDto dto);
	ResponseEntity<?> updateBook(UserPrincipal userPrincipal, Integer id, BookDto dto);
	ResponseEntity<?> deleteBook(UserPrincipal userPrincipal, Integer id);
	ResponseEntity<?> updateQtyBook(Integer id, UpdateQtyBookDto dto);
	ResponseEntity<?> getRecentFive();
	ResponseEntity<?> getOldestFive();
	ResponseEntity<?> getRecommendedDetail(Integer categoryId, Integer bookId);
	ResponseEntity<?> getQtyBook();
	ResponseEntity<?> getBookByCategoryId(Integer categoryId);
	ResponseEntity<?> getBookByCategoryAndYear(Integer categoryId, String yearBook);
	ResponseEntity<?> getBookByYear(String yearBook);
	ResponseEntity<?> getYear();
	Integer getCount(Integer id);
	Integer getCountCategory(Integer categoryId);
	ResponseEntity<?> getTitle(String title);
}
