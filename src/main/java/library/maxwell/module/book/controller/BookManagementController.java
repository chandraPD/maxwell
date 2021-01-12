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
import library.maxwell.module.book.dto.UpdateQtyBookDto;
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
	
	@GetMapping("/get-by-category/{categoryId}")
	public ResponseEntity<?> getByCategory(@PathVariable Integer categoryId) {
		return bookService.getBookByCategoryId(categoryId);
	}
	
	@GetMapping("/get-by-category-year/{categoryId}/{yearBook}")
	public ResponseEntity<?> getByCategoryAndYear(@PathVariable Integer categoryId, @PathVariable String yearBook) {
		return bookService.getBookByCategoryAndYear(categoryId, yearBook);
	}
	
	@GetMapping("/get-by-year/{yearBook}")
	public ResponseEntity<?> getByYear(@PathVariable String yearBook) {
		return bookService.getBookByYear(yearBook);
	}
	
	@GetMapping("/get-recent-five")
	public ResponseEntity<?> getRecentFive() {
		return bookService.getRecentFive();
	}
	
	@GetMapping("/get-oldest-five")
	public ResponseEntity<?> getOldestFive() {
		return bookService.getOldestFive();
	}
	
	@GetMapping("/get-max-qty")
	public ResponseEntity<?> getQtyBook() {
		return bookService.getQtyBook();
	}
	
	@GetMapping("/get-year")
	public ResponseEntity<?> getYear() {
		return bookService.getYear();
	}
	
	
	@GetMapping("/get-rec-detail/{categoryId}/{bookId}")
	public ResponseEntity<?> getRecommendedDetail(@PathVariable Integer categoryId, @PathVariable Integer bookId) {
		return bookService.getRecommendedDetail(categoryId, bookId);
	}
	
//	@GetMapping("/get-by-author/{author}")
//	public ResponseEntity<?> getByAuthor(@PathVariable String author) {
//		return bookService.findByAuthor(author);
//	}
	
	@PostMapping("/add-book")
	public ResponseEntity<?> addBook(@CurrentUser UserPrincipal userPrincipal,@RequestBody BookDto dto) {
		return bookService.addBook(userPrincipal, dto);
	}
	
	@PutMapping("/update-book/{id}")
	public ResponseEntity<?> updateBook(@CurrentUser UserPrincipal userPrincipal, @PathVariable Integer id, @RequestBody BookDto dto) {
		return bookService.updateBook(userPrincipal, id, dto);
	}
	
	@PutMapping("/update-qty-book/{id}")
	public ResponseEntity<?> UpdateQtyBook(@PathVariable Integer id, @RequestBody UpdateQtyBookDto dto) {
		return bookService.updateQtyBook(id, dto);
	}
	
	@PutMapping("/delete-book/{id}")
	public ResponseEntity<?> deleteBook(@CurrentUser UserPrincipal userPrincipal, @PathVariable Integer id) {
		return bookService.deleteBook(userPrincipal, id);
	}
	
}
