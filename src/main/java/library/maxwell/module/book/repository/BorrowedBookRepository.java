package library.maxwell.module.book.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import library.maxwell.module.book.entity.BorrowedBookEntity;

@Repository
public interface BorrowedBookRepository extends JpaRepository<BorrowedBookEntity, Integer> {

	
	
}
