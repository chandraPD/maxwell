package library.maxwell.module.book.service;

import library.maxwell.module.book.dto.ReturnBookDto;
import org.springframework.http.ResponseEntity;

import library.maxwell.config.security.auth.UserPrincipal;
import library.maxwell.module.book.dto.BorrowBookDto;
import library.maxwell.module.book.entity.BorrowedBookEntity;
import library.maxwell.module.invoice.dto.StatusMessageDto;

import java.util.List;


public interface BorrowedBookService {

	StatusMessageDto<?> borrowBook(UserPrincipal userPrincipal,BorrowBookDto dto);
	StatusMessageDto<?> getById(Integer borrowedBookId);
	StatusMessageDto<?> getAll();
	StatusMessageDto<?> getAll(UserPrincipal userPrincipal);
	StatusMessageDto<?> getAllBorrowed(UserPrincipal userPrincipal);
	StatusMessageDto<?> getAllBorrowedByUserId(Integer userId);
	StatusMessageDto<?> accAct(UserPrincipal userPrincipal, Integer borrowedBookId);
	StatusMessageDto<?> decAct(UserPrincipal userPrincipal, Integer borrowedBookId);
	StatusMessageDto returnBook(UserPrincipal userPrincipal, List<ReturnBookDto> dtos);
	StatusMessageDto<?> getAllBorrowerBook();
}
