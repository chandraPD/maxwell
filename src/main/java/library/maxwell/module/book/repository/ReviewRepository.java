package library.maxwell.module.book.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import library.maxwell.module.book.entity.ReviewEntity;

@Repository
public interface ReviewRepository extends JpaRepository<ReviewEntity, Integer> {
	Boolean existsByBookEntityBookIdAndUserEntityUserId(Integer id, Integer id2);

	@Query(value = "select * from review where book_id=? AND Status is True", nativeQuery = true)
	List<ReviewEntity> findAll1(Integer id);

	@Query(value = "Select status from review where book_id=? AND user_id=?", nativeQuery = true)
	Boolean findStatus(Integer id, Integer id2);

	@Query(value = "SELECT AVG(rate) FROM `review` WHERE book_id=? AND Status is True", nativeQuery = true)
	Double findRate(Integer id);

	@Query(value = "Select * from review where book_id=? And user_id=?", nativeQuery = true)
	ReviewEntity getEntities(Integer Id, Integer id2);
}
