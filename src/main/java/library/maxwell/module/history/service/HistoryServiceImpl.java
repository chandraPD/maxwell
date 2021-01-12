package library.maxwell.module.history.service;

import library.maxwell.config.security.auth.UserPrincipal;
import library.maxwell.module.book.entity.BorrowedBookEntity;
import library.maxwell.module.book.repository.BorrowedBookRepository;
import library.maxwell.module.history.dto.HistoryDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HistoryServiceImpl implements  HistoryService{

    @Autowired
    private BorrowedBookRepository borrowedBookRepository;

    @Override
    public List<BorrowedBookEntity> getAllBorrowedBook(UserPrincipal userPrincipal) {
    Integer idUser = userPrincipal.getId();
    List<BorrowedBookEntity> borrowedBookEntities = borrowedBookRepository.findByStatusBookAndUserIdEntityUserId("Returned", idUser);
        return borrowedBookEntities;
    }

    @Override
    public List<BorrowedBookEntity> getAllCurrentRead(UserPrincipal userPrincipal) {
        Integer idUser = userPrincipal.getId();
        List<BorrowedBookEntity> borrowedBookEntities = borrowedBookRepository.findByStatusBookNotAndStatusBookNotAndUserIdEntityUserId("Returned", "Canceled", idUser);

        return borrowedBookEntities;
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
