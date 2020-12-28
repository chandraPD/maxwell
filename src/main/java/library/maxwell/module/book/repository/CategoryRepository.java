package library.maxwell.module.book.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import library.maxwell.module.book.entity.CategoryEntity;


@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Integer> {
	
	CategoryEntity findByCategory(String category);
	
	@Query(value = "SELECT * FROM category WHERE status IS true", nativeQuery = true) 
	List<CategoryEntity> findActiveCategory();
	
	@Query(value = "SELECT * FROM category WHERE status IS false", nativeQuery = true)
	List<CategoryEntity> findInactiveCategory();
	
}
