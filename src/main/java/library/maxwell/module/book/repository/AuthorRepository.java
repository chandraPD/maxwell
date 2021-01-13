package library.maxwell.module.book.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import library.maxwell.module.book.entity.AuthorEntity;

@Repository
public interface AuthorRepository extends JpaRepository<AuthorEntity, Integer> {

	Boolean existsByAuthorName(String author);

	List<AuthorEntity> findAllByStatusIsTrue();

	@Query(value = "Select author_name from author where author_name=? AND status IS TRUE", nativeQuery = true)
	String Author(String author);

	@Query(value = "Select status from author where author_name=?", nativeQuery = true)
	Boolean findStatus(String name);

	@Query(value = "select * from author where author_name=?", nativeQuery = true)
	AuthorEntity authorEntity(String name);

}
