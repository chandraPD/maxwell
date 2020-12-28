package library.maxwell.module.slideshow.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import library.maxwell.module.slideshow.entity.SlideShowEntity;

@Repository
public interface SlideShowRepository extends JpaRepository<SlideShowEntity, Integer>{

}
