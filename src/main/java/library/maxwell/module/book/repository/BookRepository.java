package library.maxwell.module.book.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import library.maxwell.module.book.entity.BookEntity;

@Repository
public interface BookRepository extends JpaRepository<BookEntity, Integer> {
	
	List<BookEntity> findByTitle(String title);
	List<BookEntity> findByAuthor(String author);
	
	@Query(value = "SELECT * FROM book WHERE status IS true", nativeQuery = true)
	List<BookEntity> findActiveBook();
	
	@Query(value = "SELECT * FROM book WHERE status IS false", nativeQuery = true)
	List<BookEntity> findInactiveBook();
	
	@Query(value = "SELECT * FROM book WHERE status IS true ORDER BY created_at DESC LIMIT 5", nativeQuery = true)
	List<BookEntity> getRecentFive();
	
}
