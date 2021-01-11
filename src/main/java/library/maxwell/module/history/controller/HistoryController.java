package library.maxwell.module.history.controller;


import library.maxwell.config.security.auth.CurrentUser;
import library.maxwell.config.security.auth.UserPrincipal;
import library.maxwell.module.book.entity.BorrowedBookEntity;
import library.maxwell.module.donate.dto.StatusMessageDto;
import library.maxwell.module.history.service.HistoryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class HistoryController {
    @Autowired
    private HistoryServiceImpl historyService;

    private StatusMessageDto statusMessageDto = new StatusMessageDto();

    @GetMapping("/history/borrowed")
    public ResponseEntity<?> getAllHistory(@CurrentUser UserPrincipal userPrincipal) {
        List<BorrowedBookEntity> borrowedBookEntities = historyService.getAllBorrowedBook(userPrincipal);
        statusMessageDto.setStatus(200);
        statusMessageDto.setMessages("Data have succesfully loaded");
        statusMessageDto.setData(borrowedBookEntities);
        return ResponseEntity.ok(statusMessageDto);

    }

    @GetMapping("/history/currentread")
    public ResponseEntity<?> getAllHistorycurrent(@CurrentUser UserPrincipal userPrincipal) {
        List<BorrowedBookEntity> borrowedBookEntities = historyService.getAllCurrentRead(userPrincipal);
        statusMessageDto.setStatus(200);
        statusMessageDto.setMessages("Data have succesfully loaded");
        statusMessageDto.setData(borrowedBookEntities);
        return ResponseEntity.ok(statusMessageDto);

    }
}