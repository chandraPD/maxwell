package library.maxwell.module.book.service;

import org.springframework.http.ResponseEntity;

import library.maxwell.module.book.dto.BookDto;

public interface BookService {
	ResponseEntity<?> getBook();
	ResponseEntity<?> getBookById(Integer id);
	ResponseEntity<?> getActiveBook();
	ResponseEntity<?> getInactiveBook();
	ResponseEntity<?> findByTitle(String title);
	ResponseEntity<?> findByAuthor(String author);
	ResponseEntity<?> addBook(BookDto dto);
	ResponseEntity<?> updateBook(Integer id, BookDto dto);
	ResponseEntity<?> deleteBook(Integer id);
}
