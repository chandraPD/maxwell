package library.maxwell.module.book.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import library.maxwell.module.book.entity.BookEntity;

@Repository
public interface BookRepository extends JpaRepository<BookEntity, Integer> {
	
	List<BookEntity> findByTitleLike(String title);	
	
	BookEntity findByTitle(String title);
	
	@Query(value = "SELECT * FROM book WHERE status IS true", nativeQuery = true)
	List<BookEntity> findActiveBook();
	
	@Query(value = "SELECT * FROM book WHERE status IS false", nativeQuery = true)
	List<BookEntity> findInactiveBook();
	
	@Query(value = "SELECT * FROM book WHERE status IS true ORDER BY created_at DESC LIMIT 5", nativeQuery = true)
	List<BookEntity> getRecentFive();
	
	@Query(value = "SELECT * FROM book WHERE status IS true ORDER BY created_at ASC LIMIT 5", nativeQuery = true)
	List<BookEntity> getOldestFive();
	
	@Query(value = "SELECT * FROM book WHERE category_id = ?1 AND book_id != ?2 LIMIT 3", nativeQuery = true)
	List<BookEntity> getRecommendedDetail(Integer categoryId, Integer bookId);
	
	@Query(value = "SELECT book_code FROM book WHERE EXTRACT(YEAR FROM created_at)  = ? ORDER BY book_code DESC LIMIT 1", nativeQuery = true)
	String getLastBookCode(Integer year);
	
	@Query(value = "SELECT * FROM book WHERE qty = (SELECT MAX(qty) FROM book) AND STATUS is true LIMIT 1", nativeQuery = true)
	BookEntity getMaxQtyBook();
	
	@Query(value = "SELECT * FROM book WHERE category_id = ? AND status IS true", nativeQuery = true)
	List<BookEntity> getBookByCategory(Integer categoryId);
	
	@Query(value = "SELECT * FROM book WHERE category_id = ?1 AND YEAR(publish_date) = ?2 AND status IS true", nativeQuery = true)
	List<BookEntity> getBookByCategoryAndYear(Integer categoryId, String yearBook);
	
	@Query(value = "SELECT * FROM book WHERE YEAR(publish_date) = ? AND status IS true", nativeQuery = true)
	List<BookEntity> getBookByYear(String yearBook);
	
	@Query(value = "SELECT DISTINCT YEAR(publish_date) as year FROM book ORDER BY publish_date DESC", nativeQuery = true)
	List<String> getYear();
	
	Boolean existsByTitle(String title);
	
	@Query(value="SELECT COUNT(author_id) FROM `book` WHERE author_id=?",nativeQuery = true)
	Integer count(Integer id);

	Integer countBookEntityByAuthorEntityAuthorId(Integer id);
	
	@Query(value="SELECT COUNT(category_id) FROM book WHERE category_id = ?", nativeQuery = true)
	Integer countCategory(Integer categoryId);
	
	@Query(value="SELECT status FROM book WHERE title = ?", nativeQuery = true)
	Boolean existsByStatusTitle(String title);
}
