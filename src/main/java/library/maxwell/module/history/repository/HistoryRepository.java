package library.maxwell.module.history.repository;

import library.maxwell.module.book.entity.BorrowedBookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoryRepository extends JpaRepository<BorrowedBookEntity, Integer> {
}
