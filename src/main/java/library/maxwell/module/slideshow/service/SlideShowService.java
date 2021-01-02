package library.maxwell.module.slideshow.service;

import java.util.List;

import library.maxwell.config.security.auth.UserPrincipal;
import library.maxwell.module.slideshow.dto.SlideShowDto;
import library.maxwell.module.slideshow.entity.SlideShowEntity;

public interface SlideShowService {
	List<SlideShowEntity> getAllSlideShow();
	SlideShowEntity getSlideShowById(Integer idSlideShow);
	SlideShowEntity addSlideShow(UserPrincipal userPrincipal, SlideShowDto dto);
	SlideShowEntity updateSlideShow(Integer idSlideShow, SlideShowDto dto);
	SlideShowEntity deleteSlideShow(Integer idSlideShow);
	SlideShowEntity deleteDataSlideShow(Integer idSlideShow);
	
}
