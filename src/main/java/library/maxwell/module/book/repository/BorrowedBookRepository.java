package library.maxwell.module.book.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import library.maxwell.module.book.entity.BorrowedBookEntity;

@Repository
public interface BorrowedBookRepository extends JpaRepository<BorrowedBookEntity, Integer> {

	List<BorrowedBookEntity> findAllByStatusIsTrueAndUserIdEntity_UserIdIs(int userId);
	
	List<BorrowedBookEntity> findAllByStatusIsTrue();
	

	@Query(value = "select borrowed_book_code from borrowed_book where YEAR(borrowed_date) = ? order by borrowed_book_id DESC limit 1  ", nativeQuery = true)
	String getLastBorrowed(Integer year);
	
	BorrowedBookEntity findByBorrowedBookId(Integer borrowedBookId);
	
	@Query(value="SELECT borrowed_book_id FROM `borrowed_book` WHERE user_id=?",nativeQuery = true)
	List<BorrowedBookEntity> findId2(Integer id);
}
