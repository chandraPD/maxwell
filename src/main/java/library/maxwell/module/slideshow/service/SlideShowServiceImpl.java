package library.maxwell.module.slideshow.service;

import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cloudinary.utils.ObjectUtils;

import library.maxwell.config.CloudinaryConfig;
import library.maxwell.config.security.auth.UserPrincipal;
import library.maxwell.module.log.entity.LogEntity;
import library.maxwell.module.log.repository.LogRepository;
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
	
	@Autowired
	private LogRepository logRepository;
	
	@Autowired
	private CloudinaryConfig cloudinary;
	
	@Override
	public ResponseEntity<?> getAllSlideShow() {
		// TODO Auto-generated method stub
		List<SlideShowEntity> slideShowEntities = slideShowRepository.findAll();
		return ResponseEntity.ok(slideShowEntities);
	}
	@Override
	public SlideShowEntity getSlideShowById(Integer idSlideShow) {
		// TODO Auto-generated method stub
		Boolean findSlideShowEntity = slideShowRepository.existsBySlideShowId(idSlideShow);
		
		if(findSlideShowEntity) {
			SlideShowEntity slideShowEntity = slideShowRepository.findById(idSlideShow).get();
			System.out.println(slideShowEntity);
			
			return slideShowEntity;
		}
		
		return null;
		
	}
	
	@Override
	public ResponseEntity<?> getAllActive() {
		// TODO Auto-generated method stub
		List<SlideShowEntity> slideShowEntities = slideShowRepository.findAllActive();
		return ResponseEntity.ok(slideShowEntities);
	}
	
	@Override
	public SlideShowEntity addSlideShow(UserPrincipal userPrincipal, SlideShowDto dto) {
		// TODO Auto-generated method stub
		Integer userId = userPrincipal.getId();
		UserEntity userEntity = userRepository.findById(userId).get();
		SlideShowEntity slideShowEntity = convertToSlideShowEntity(dto);
		
		
		//Upload image
        try {
            //Covert Base64 to bytes
            byte[] slideShowImage = Base64.getMimeDecoder().decode(dto.getImg());

            //UNTUK MAPPING FILE UPLOAD 
            Map uploadResult = cloudinary.upload(slideShowImage,
                    ObjectUtils.asMap("resourcetype", "auto"));            
            
            slideShowEntity.setUserEntity(userEntity);
            slideShowEntity.setImg(uploadResult.get("url").toString());
    		slideShowRepository.save(slideShowEntity);

        } catch (Exception e) {
            e.getMessage();
        }
        
        //MENGIRIM AKTIVITAS KE LOG
		LogEntity logEntity = new LogEntity();
		logEntity.setAction("Post");
		logEntity.setDateTime(LocalDateTime.now());
		logEntity.setStatus(true);
		logEntity.setUserEntity(userEntity);
		logEntity.setDescription("Melakukan Penambahan Data Pada Slide Show dengan judul : " + dto.getTitle());
		logRepository.save(logEntity);
		
		return slideShowEntity;
	}
	
	@Override
	public SlideShowEntity updateSlideShow(UserPrincipal userPrincipal, Integer idSlideShow, SlideShowDto dto) {
		// TODO Auto-generated method stub
		SlideShowEntity slideShowEntity = slideShowRepository.findById(idSlideShow).get();
		Integer userId = userPrincipal.getId();
		UserEntity userEntity = userRepository.findById(userId).get();
		
		//Upload image
        try {
            //Covert Base64 to bytes
            byte[] slideShowImage = Base64.getMimeDecoder().decode(dto.getImg());

            Map uploadResult = cloudinary.upload(slideShowImage,
                    ObjectUtils.asMap("resourcetype", "auto"));

    		slideShowEntity.setImg(uploadResult.get("url").toString());
        } catch (Exception e) {
            e.getMessage();
        }
		
      //MENGIRIM AKTIVITAS KE LOG
  		LogEntity logEntity = new LogEntity();
  		logEntity.setAction("Update");
  		logEntity.setDateTime(LocalDateTime.now());
  		logEntity.setStatus(true);
  		logEntity.setUserEntity(userEntity);
  		logEntity.setDescription("Melakukan Update Data Pada Slide Show");
  		logRepository.save(logEntity);
      		
        slideShowEntity.setCreatedAt(LocalDateTime.now());
		slideShowEntity.setTitle(dto.getTitle());
		slideShowEntity.setSubTitle(dto.getSubTitle());
		slideShowEntity.setStatusShow(dto.getStatusShow());
		slideShowEntity.setUserEntity(userEntity);
		slideShowRepository.save(slideShowEntity);

		return slideShowEntity;
	}
	
	public SlideShowEntity convertToSlideShowEntity(SlideShowDto dto) {
		SlideShowEntity slideShowEntity = new SlideShowEntity();
		slideShowEntity.setSlideShowId(dto.getSlideShowId());
		slideShowEntity.setCreatedAt(LocalDateTime.now());
		slideShowEntity.setTitle(dto.getTitle());
		slideShowEntity.setSubTitle(dto.getSubTitle());
		return slideShowEntity;
	}
	
	//DELETE YANG DIGUNAKAN DI FRONT-END
	@Override
	public SlideShowEntity deleteDataSlideShow(UserPrincipal userPrincipal, Integer idSlideShow) {
		// TODO Auto-generated method stub
		Integer userId = userPrincipal.getId();
		UserEntity userEntity = userRepository.findById(userId).get();
		SlideShowEntity slideShowEntity = slideShowRepository.findById(idSlideShow).get();
		 
		//MENGIRIM AKTIVITAS KE LOG
  		LogEntity logEntity = new LogEntity();
  		logEntity.setAction("Delete");
  		logEntity.setDateTime(LocalDateTime.now());
  		logEntity.setStatus(true);
  		logEntity.setUserEntity(userEntity);
  		logEntity.setDescription("Menghapus Data Slide Show");
  		logRepository.save(logEntity);
		
		slideShowEntity.setUserEntity(userEntity);
		slideShowRepository.delete(slideShowEntity);
		return slideShowEntity;
	}
	
	@Override
	public SlideShowEntity editStatus(UserPrincipal userPrincipal, Integer idSlideShow, Boolean status) {
		// TODO Auto-generated method stub
		Integer userId = userPrincipal.getId();
		SlideShowEntity slideShowEntity = slideShowRepository.findById(idSlideShow).get();
		UserEntity userEntity = userRepository.findById(userId).get();
		
		//MENGIRIM AKTIVITAS KE LOG
  		LogEntity logEntity = new LogEntity();
  		logEntity.setAction("Update Status");
  		logEntity.setDateTime(LocalDateTime.now());
  		logEntity.setStatus(true);
  		logEntity.setUserEntity(userEntity);
  		logEntity.setDescription("Melakukan Update Status Pada Slide Show");
  		logRepository.save(logEntity);
		
		slideShowEntity.setUserEntity(userEntity);
		slideShowEntity.setStatusShow(status);
		slideShowRepository.save(slideShowEntity);
		return slideShowEntity;
		
	}
	
	
	
}
