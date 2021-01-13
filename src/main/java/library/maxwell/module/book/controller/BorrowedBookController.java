package library.maxwell.module.book.controller;

import java.util.List;

import library.maxwell.module.book.dto.ReturnBookDto;
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
import library.maxwell.module.book.dto.BorrowBookDto;
import library.maxwell.module.book.service.BorrowedBookService;
import library.maxwell.module.invoice.dto.StatusMessageDto;
import library.maxwell.module.user.service.UserService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/borrow")
public class BorrowedBookController {

	@Autowired
	BorrowedBookService borrowImplService;
	
	@Autowired
	UserService userService;

	@PostMapping("/save")
	public ResponseEntity<?> save(@CurrentUser UserPrincipal userPrincipal, @RequestBody BorrowBookDto dto) {

		StatusMessageDto<?> result = borrowImplService.borrowBook(userPrincipal, dto);
		return ResponseEntity.ok(result);
	}

	@GetMapping("/get-all")
	public ResponseEntity<?> getAll(@CurrentUser UserPrincipal userPrincipal) {
		StatusMessageDto<?> result = borrowImplService.getAll(userPrincipal);
		return ResponseEntity.ok(result);
	}

	@GetMapping("/get-all-borrowed")
	public ResponseEntity<?> getAllBorrowed(@CurrentUser UserPrincipal userPrincipal){
		StatusMessageDto<?> result = borrowImplService.getAllBorrowed(userPrincipal);
		return ResponseEntity.ok(result);
	}

	@GetMapping("/get-all-borrowed-by-userid/{userId}")
	public ResponseEntity<?> getAllBorrowedByUserId(@PathVariable Integer userId){
		StatusMessageDto<?> result = borrowImplService.getAllBorrowedByUserId(userId);
		return ResponseEntity.ok(result);
	}


	@PutMapping("/acc-act/{idBorrowedBook}")
	public ResponseEntity<?> accAct(@CurrentUser UserPrincipal userPrincipal, @PathVariable Integer idBorrowedBook) {
		return ResponseEntity.ok(borrowImplService.accAct(userPrincipal, idBorrowedBook));
	}

	@PutMapping("/dec-act/{idBorrowedBook}")
	public ResponseEntity<?> decAct(@CurrentUser UserPrincipal userPrincipal, @PathVariable Integer idBorrowedBook) {
		return ResponseEntity.ok(borrowImplService.decAct(userPrincipal, idBorrowedBook));
	}

	@PostMapping("/return")
	public ResponseEntity<?> returnBook(@CurrentUser UserPrincipal userPrincipal, @RequestBody List<ReturnBookDto> dtos ){
		return ResponseEntity.ok(borrowImplService.returnBook(userPrincipal, dtos));
	}

	@GetMapping("/get-all-borrower-book")
	public ResponseEntity<?> getAllBorrowerBook(){
		StatusMessageDto<?> result = borrowImplService.getAllBorrowerBook();
		return ResponseEntity.ok(result);
	}
}
