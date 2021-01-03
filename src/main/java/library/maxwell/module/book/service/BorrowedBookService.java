package library.maxwell.module.book.service;

import library.maxwell.config.security.auth.UserPrincipal;
import library.maxwell.module.book.dto.BorrowBookDto;
import library.maxwell.module.invoice.dto.StatusMessageDto;


public interface BorrowedBookService {
	
	StatusMessageDto<?> borrowBook(UserPrincipal userPrincipal,BorrowBookDto dto);
	StatusMessageDto<?> getById(Integer borrowedBookId);
	StatusMessageDto<?> getAll();
	StatusMessageDto<?> getAll(UserPrincipal userPrincipal);
	
}
