package library.maxwell.module.book.repository;

import java.util.List;

import library.maxwell.module.book.entity.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import library.maxwell.module.book.entity.BorrowedBookEntity;

@Repository
public interface BorrowedBookRepository extends JpaRepository<BorrowedBookEntity, Integer> {

	List<BorrowedBookEntity> findAllByStatusIsTrueAndUserIdEntity_UserIdIs(int userId);
	
	List<BorrowedBookEntity> findAllByStatusIsTrue();

	@Query(value ="SELECT DISTINCT (user_id) from borrowed_book where status_book = ?", nativeQuery = true)
	List<BorrowedBookEntity> getDistinct(String statusBook);

	List<BorrowedBookEntity> findDistinctByStatusBook(String statusBook);

	@Query(value = "select borrowed_book_code from borrowed_book where YEAR(borrowed_date) = ? order by borrowed_book_id DESC limit 1  ", nativeQuery = true)
	String getLastBorrowed(Integer year);
	
	BorrowedBookEntity findByBorrowedBookId(Integer borrowedBookId);

	List<BorrowedBookEntity> findByStatusBookAndUserIdEntityUserId(String statusBook, Integer idUser);

	List<BorrowedBookEntity> findByStatusBookNotAndStatusBookNotAndUserIdEntityUserId(String status1, String status2, Integer idUser);

	List<BorrowedBookEntity> findAllByStatusIsTrueAndUserIdEntity_UserIdAndStatusBookIs(Integer userId, String StatusBook);

	@Query(value="SELECT borrowed_book_id FROM `borrowed_book` WHERE user_id=?",nativeQuery = true)
	List<BorrowedBookEntity> findId2(Integer id);

}
