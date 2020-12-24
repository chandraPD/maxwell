package library.maxwell.module.slideshow.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import library.maxwell.module.slideshow.dto.SlideShowDto;
import library.maxwell.module.slideshow.entity.SlideShowEntity;
import library.maxwell.module.slideshow.repository.SlideShowRepository;
import library.maxwell.module.user.entity.UserEntity;

@RestController
@RequestMapping("/slideshow")
public class SlideShowController {
	@Autowired
	private SlideShowRepository slideShowRepository;
	//@Autowired
	//private UserRepository userRepository;
	
	//GET ALL
	@GetMapping("/get-all-slideshow")
	public List<SlideShowEntity> getAllSlideShow(){
		List<SlideShowEntity> slideShowEntities = slideShowRepository.findAll();
		return slideShowEntities;
	}
	
	//GET BY ID
	@GetMapping("/get-slideshow-byId/{idSlideShow}")
	public ResponseEntity<?> getSlideShowById(@PathVariable Integer idSlideShow) {
		SlideShowEntity slideShowEntity = slideShowRepository.findById(idSlideShow).get();
		return ResponseEntity.ok(slideShowEntity);
	}
	
	//POST
	@PostMapping("/add-slideshow")
	public ResponseEntity<?> addSlideShow(@RequestBody SlideShowDto dto) {
		SlideShowEntity slideShowEntity = convertToSlideShowEntity(dto);
		slideShowRepository.save(slideShowEntity);
		return ResponseEntity.ok(slideShowEntity);
	}
	
	//UPDATE
	@PutMapping("/update-slideshow/{idSlideShow}")
	public ResponseEntity<?> updateSlideShow(@PathVariable Integer idSlideShow,
			@RequestBody SlideShowDto dto){
		//UserEntity userEntity = userRepository.findByUserId(dto.userId);
		SlideShowEntity slideShowEntity = slideShowRepository.findById(idSlideShow).get();
		SlideShowEntity updateSlideShow = convertToSlideShowEntity(dto);
//		SlideShowEntity.setUserId(userEntity);
		slideShowRepository.save(updateSlideShow);
		return ResponseEntity.ok(slideShowEntity);
	}
	
	//DELETE
	@PutMapping("/delete-slideshow/{idSlideShow}")
	public ResponseEntity<?> deleteSlideShow(@PathVariable Integer idSlideShow){
		SlideShowEntity slideShowEntity = slideShowRepository.findById(idSlideShow).get();
		slideShowEntity.setStatus(false);
		slideShowRepository.save(slideShowEntity);
		return ResponseEntity.ok(slideShowEntity);
	}
	
	public SlideShowEntity convertToSlideShowEntity(SlideShowDto dto) {
		SlideShowEntity slideShowEntity = new SlideShowEntity();
		slideShowEntity.setTitle(dto.getTitle());
		slideShowEntity.setSubTitle(dto.getSubTitle());
		slideShowEntity.setImg(dto.getImg());
		return slideShowEntity;
		
	}
}
