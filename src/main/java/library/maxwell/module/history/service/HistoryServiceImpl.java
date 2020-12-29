package library.maxwell.module.history.service;

import library.maxwell.module.book.entity.BorrowedBookEntity;
import library.maxwell.module.history.dto.HistoryDto;
import library.maxwell.module.history.repository.HistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HistoryServiceImpl implements  HistoryService{

    @Autowired
    private HistoryRepository historyRepository;

    @Override
    public List<BorrowedBookEntity> getAllHistory() {

        return null;
    }

    @Override
    public BorrowedBookEntity insertHistory(HistoryDto dto) {
        return null;
    }

    @Override
    public BorrowedBookEntity updateHistory(HistoryDto dto, Integer UserId) {
        return null;
    }

    @Override
    public BorrowedBookEntity deleteHistory(Integer UserId) {
        return null;
    }




}
