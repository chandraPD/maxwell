package library.maxwell.module.slideshow.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import library.maxwell.module.slideshow.dto.SlideShowDto;
import library.maxwell.module.slideshow.entity.SlideShowEntity;
import library.maxwell.module.slideshow.repository.SlideShowRepository;
import library.maxwell.module.user.entity.UserEntity;
import library.maxwell.module.user.repository.UserRepository;

@Service
@Transactional
public class SlideShowServiceImpl implements SlideShowService{
	@Autowired
	private SlideShowRepository slideShowRepository;
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public List<SlideShowEntity> getAllSlideShow() {
		// TODO Auto-generated method stub
		List<SlideShowEntity> slideShowEntities = slideShowRepository.findAll();
		return slideShowEntities;
	}
	@Override
	public SlideShowEntity getSlideShowById(Integer idSlideShow) {
		// TODO Auto-generated method stub
		SlideShowEntity slideShowEntity = slideShowRepository.findById(idSlideShow).get();
		return slideShowEntity;
	}
	@Override
	public SlideShowEntity addSlideShow(SlideShowDto dto) {
		// TODO Auto-generated method stub
		UserEntity userEntity = userRepository.findById(dto.getUserId()).get();
		SlideShowEntity slideShowEntity = convertToSlideShowEntity(dto);
		slideShowEntity.setUserEntity(userEntity);
		slideShowRepository.save(slideShowEntity);
		return slideShowEntity;
	}
	@Override
	public SlideShowEntity updateSlideShow(Integer idSlideShow, SlideShowDto dto) {
		// TODO Auto-generated method stub
		SlideShowEntity slideShowEntity = slideShowRepository.findById(idSlideShow).get();
		slideShowEntity.setCreatedAt(dto.getCreatedAt());
		slideShowEntity.setTitle(dto.getTitle());
		slideShowEntity.setSubTitle(dto.getSubTitle());
		slideShowEntity.setImg(dto.getImg());
		slideShowRepository.save(slideShowEntity);
		return slideShowEntity;
	}
	@Override
	public SlideShowEntity deleteSlideShow(Integer idSlideShow) {
		// TODO Auto-generated method stub
		SlideShowEntity slideShowEntity = slideShowRepository.findById(idSlideShow).get();
		slideShowEntity.setStatus(false);
		slideShowRepository.save(slideShowEntity);
		return slideShowEntity;
	}
	
	public SlideShowEntity convertToSlideShowEntity(SlideShowDto dto) {
		SlideShowEntity slideShowEntity = new SlideShowEntity();
		slideShowEntity.setCreatedAt(LocalDateTime.now());
		slideShowEntity.setTitle(dto.getTitle());
		slideShowEntity.setSubTitle(dto.getSubTitle());
		slideShowEntity.setImg(dto.getImg());
		return slideShowEntity;
	}
}
