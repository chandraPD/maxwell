package library.maxwell.module.slideshow.repository;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import library.maxwell.module.slideshow.entity.SlideShowEntity;

@Repository
public interface SlideShowRepository extends JpaRepository<SlideShowEntity, Integer>{
	Boolean existsBySlideShowId(Integer id);
	
	@Query(value = "SELECT * FROM slideshow WHERE status_show IS true ", nativeQuery = true)
	List<SlideShowEntity> findAllActive();
}
