package library.maxwell.module.history.controller;

import library.maxwell.module.book.entity.BorrowedBookEntity;
import library.maxwell.module.history.dto.HistoryDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/history")
public class HistoryController {
    @Autowired
    private HistoryServiceImpl historyService;

    @GetMapping
    public List<BorrowedBookEntity> getAllHistory(){
        List<BorrowedBookEntity> borrowedBookEntities = historyService.getAllHistory();
        return borrowedBookEntities;
    }

    @PostMapping
    public ResponseEntity<?> insertHistory(@RequestBody HistoryDto dto){
        BorrowedBookEntity borrowedBookEntity = historyService.insertHistory(dto);
        return ResponseEntity.ok(BorrowedBookEntity);
    }

    @PutMapping("/historyId")
    public ResponseEntity<?> updateHistory(@PathVariable Integer historyId, @RequestBody HistoryDto dto){
        BorrowedBookEntity borrowedBookEntity = historyService.updateHistory(dto, historyId);
        return ResponseEntity.ok(BorrowedBookEntity);
    }

    @DeleteMapping("/historyId")
    public ResponseEntity<?> deleteHistory(@PathVariable Integer historyId){
        BorrowedBookEntity borrowedBookEntity = historyService.deleteHistory(historyId);
        return ResponseEntity.ok(BorrowedBookEntity);
    }
}
