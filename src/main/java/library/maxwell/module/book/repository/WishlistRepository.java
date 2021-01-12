package library.maxwell.module.book.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import library.maxwell.module.book.entity.WishlistEntity;

@Repository
public interface WishlistRepository extends JpaRepository<WishlistEntity, Integer>{
	Boolean existsByBookEntityBookId(Integer id);
	
	@Query(value="Select status from wishlist where book_id=? AND user_id=?",nativeQuery = true)
	Boolean findStatus(Integer id,Integer id2);
	
	@Query(value="Select * from wishlist where book_id=? And user_id=?",nativeQuery=true)
	WishlistEntity getEntities(Integer Id,Integer id2);
	
	List<WishlistEntity> findAllByStatusIsTrueAndUserEntityUserId(Integer id);
}
