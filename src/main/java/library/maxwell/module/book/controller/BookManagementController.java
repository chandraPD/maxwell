package library.maxwell.module.book.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import library.maxwell.config.security.auth.CurrentUser;
import library.maxwell.config.security.auth.UserPrincipal;
import library.maxwell.module.book.dto.BookDto;
import library.maxwell.module.book.service.BookService;

@RestController
@RequestMapping("/book")
@CrossOrigin(origins = "http://localhost:3000")
public class BookManagementController {
	
	@Autowired
	BookService bookService;
	
	@GetMapping("/get-all")
	public ResponseEntity<?> getBook() {
		return bookService.getBook();
	}
	
	@GetMapping("/get-all-active")
	public ResponseEntity<?> getActiveBook() {
		return bookService.getActiveBook();
	}
	
	@GetMapping("/get-all-inactive")
	public ResponseEntity<?> getInactiveBook() {
		return bookService.getInactiveBook();
	}
	
	@GetMapping("/get-by-id/{id}")
	public ResponseEntity<?> getById(@PathVariable Integer id) {
		return bookService.getBookById(id);
	}
	
	@GetMapping("/get-by-title/{title}")
	public ResponseEntity<?> getByTitle(@PathVariable String title) {
		return bookService.findByTitle(title);
	}
	
	@GetMapping("/get-by-author/{author}")
	public ResponseEntity<?> getByAuthor(@PathVariable String author) {
		return bookService.findByAuthor(author);
	}
	
	@PostMapping("/add-book")
	public ResponseEntity<?> addBook(@CurrentUser UserPrincipal userPrincipal,@RequestBody BookDto dto) {
		return bookService.addBook(userPrincipal, dto);
	}
	
	@PutMapping("/update-book/{id}")
	public ResponseEntity<?> updateBook(@CurrentUser UserPrincipal userPrincipal, @PathVariable Integer id, @RequestBody BookDto dto) {
		return bookService.updateBook(userPrincipal, id, dto);
	}
	
	@PutMapping("/delete-book/{id}")
	public ResponseEntity<?> deleteBook(@PathVariable Integer id) {
		return bookService.deleteBook(id);
	}
	
}
