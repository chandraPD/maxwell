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
import library.maxwell.module.slideshow.service.SlideShowServiceImpl;


@RestController
@RequestMapping("/slideshow")
public class SlideShowController {

	@Autowired
	SlideShowServiceImpl slideShowService;
	
	//GET ALL
	@GetMapping("/get-all-slideshow")
	public ResponseEntity<?> getAllSlideShow(){
		List<SlideShowEntity> slideShowEntities = slideShowService.getAllSlideShow();
		return ResponseEntity.ok(slideShowEntities);
	}
	
	//GET BY ID
	@GetMapping("/get-slideshow-byId/{idSlideShow}")
	public ResponseEntity<?> getSlideShowById(@PathVariable Integer idSlideShow) {
		SlideShowEntity slideShowEntity = slideShowService.getSlideShowById(idSlideShow);
		return ResponseEntity.ok(slideShowEntity);
	}
	
	//POST
	@PostMapping("/add-slideshow")
	public ResponseEntity<?> addSlideShow(@RequestBody SlideShowDto dto) {
		SlideShowEntity slideShowEntity = slideShowService.addSlideShow(dto);
		return ResponseEntity.ok(slideShowEntity);
	}
	
	//UPDATE
	@PutMapping("/update-slideshow/{idSlideShow}")
	public ResponseEntity<?> updateSlideShow(@PathVariable Integer idSlideShow,
			@RequestBody SlideShowDto dto){
		SlideShowEntity slideShowEntity = slideShowService.updateSlideShow(idSlideShow, dto);
		return ResponseEntity.ok(slideShowEntity);
	}
	
	//DELETE
	@PutMapping("/delete-slideshow/{idSlideShow}")
	public ResponseEntity<?> deleteSlideShow(@PathVariable Integer idSlideShow){
		SlideShowEntity slideShowEntity = slideShowService.deleteSlideShow(idSlideShow);
		return ResponseEntity.ok(slideShowEntity);
	}
	
}
