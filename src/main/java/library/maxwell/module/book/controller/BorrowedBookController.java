package library.maxwell.module.book.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import library.maxwell.config.security.auth.CurrentUser;
import library.maxwell.config.security.auth.UserPrincipal;
import library.maxwell.module.book.dto.BorrowBookDto;
import library.maxwell.module.book.service.BorrowedBookService;
import library.maxwell.module.book.service.BorrowedBookServiceImpl;
import library.maxwell.module.invoice.dto.StatusMessageDto;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/borrow")
public class BorrowedBookController {
	
	@Autowired
	BorrowedBookService borrowImplService;
	
	@PostMapping("/save")
	public ResponseEntity<?> save(@CurrentUser UserPrincipal userPrincipal, @RequestBody BorrowBookDto dto){
		
		StatusMessageDto<?> result = borrowImplService.borrowBook(userPrincipal, dto);
		return ResponseEntity.ok(result); 
	}
	
	@GetMapping("/get-all")
	public ResponseEntity<?> getAll(@CurrentUser UserPrincipal userPrincipal){
		StatusMessageDto<?> result = borrowImplService.getAll(userPrincipal);
		return ResponseEntity.ok(result); 
	}
	
}
