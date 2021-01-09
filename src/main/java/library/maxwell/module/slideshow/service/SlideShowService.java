package library.maxwell.module.slideshow.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import library.maxwell.config.security.auth.UserPrincipal;
import library.maxwell.module.slideshow.dto.SlideShowDto;
import library.maxwell.module.slideshow.entity.SlideShowEntity;

public interface SlideShowService {
	List<SlideShowEntity> getAllSlideShow();
	ResponseEntity<?> getAllActive();
	SlideShowEntity getSlideShowById(Integer idSlideShow);
	SlideShowEntity addSlideShow(UserPrincipal userPrincipal, SlideShowDto dto);
	SlideShowEntity updateSlideShow(UserPrincipal userPrincipal, Integer idSlideShow, SlideShowDto dto);
	SlideShowEntity deleteSlideShow(UserPrincipal userPrincipal, Integer idSlideShow);
	SlideShowEntity deleteDataSlideShow(UserPrincipal userPrincipal, Integer idSlideShow);
	SlideShowEntity editStatus(UserPrincipal userPrincipal, Integer idSlideShow, Boolean status);
	
}
