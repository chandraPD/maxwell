package library.maxwell.module.history.service;

import library.maxwell.module.book.entity.BorrowedBookEntity;
import library.maxwell.module.history.dto.HistoryDto;

import java.util.List;

public interface HistoryService {
    List<BorrowedBookEntity> getAllHistory();
    BorrowedBookEntity insertHistory(HistoryDto dto);
    BorrowedBookEntity updateHistory(HistoryDto dto, Integer UserId);
    BorrowedBookEntity deleteHistory(Integer UserId);
}
