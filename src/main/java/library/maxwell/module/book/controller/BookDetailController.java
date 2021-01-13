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
import library.maxwell.module.book.dto.BookDetailDto;
import library.maxwell.module.book.service.BookDetailService;

@RestController
@RequestMapping("/book-detail")
@CrossOrigin
public class BookDetailController {
	
	@Autowired
	BookDetailService bookDetailService;
	
	@GetMapping("/get-all")
	public ResponseEntity<?> getAllDetail(){
		return bookDetailService.getAllDetail();
	}
	
	@GetMapping("/get-all-active")
	public ResponseEntity<?> getAllActive() {
		return bookDetailService.getAllActive();
	}
	
	@GetMapping("/get-all-inactive")
	public ResponseEntity<?> getAllInactive() {
		return bookDetailService.getAllInactive();
	}
	
	@GetMapping("/get-by-book-id/{id}")
	public ResponseEntity<?> getByBookId(@PathVariable Integer id) {
		return bookDetailService.getByBookId(id);
	}
	
	@GetMapping("/get-detail-book/{id}")
	public ResponseEntity<?> getDetailBook(@PathVariable Integer id) {
		return bookDetailService.getDetailBook(id);
	}
	
	@GetMapping("/get-by-type/{typeOfDamage}")
	public ResponseEntity<?> getByTypeOfDamage(@PathVariable String typeOfDamage) {
		return bookDetailService.getByTypeofDamage(typeOfDamage);
	}
	
	@GetMapping("/get-book-detail-count/{statusBookDetail}/{bookId}")
	public ResponseEntity<?> getBookDetailCount(@PathVariable String statusBookDetail, @PathVariable Integer bookId){
		return bookDetailService.getBookActiveCount(statusBookDetail, bookId);
	}
	
	@PostMapping("/add-detail")
	public ResponseEntity<?> addDetailBook(@CurrentUser UserPrincipal userPrincipal, @RequestBody BookDetailDto dto) {
		return bookDetailService.addDetailBook(userPrincipal, dto);
	}
	
	@PutMapping("/update-detail/{id}")
	public ResponseEntity<?> updateDetailBook(@CurrentUser UserPrincipal userPrincipal, @PathVariable Integer id, @RequestBody BookDetailDto dto) {
		return bookDetailService.updateDetailBook(userPrincipal, id, dto);
	}
	
	@PutMapping("/delete-detail/{id}")
	public ResponseEntity<?> deleteDetailBook(@CurrentUser UserPrincipal userPrincipal, @PathVariable Integer id) {
		return bookDetailService.deleteDetailBook(userPrincipal, id);
	}
 	
}
