package library.maxwell.module.slideshow.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import library.maxwell.config.security.auth.CurrentUser;
import library.maxwell.config.security.auth.UserPrincipal;
import library.maxwell.module.log.dto.StatusMessageDto;
import library.maxwell.module.slideshow.dto.SlideShowDto;
import library.maxwell.module.slideshow.entity.SlideShowEntity;
import library.maxwell.module.slideshow.repository.SlideShowRepository;
import library.maxwell.module.slideshow.service.SlideShowService;
import library.maxwell.module.slideshow.service.SlideShowServiceImpl;
import library.maxwell.module.user.entity.UserEntity;
import library.maxwell.module.user.service.UserService;
import library.maxwell.module.user.service.UserServiceImpl;


@RestController
@RequestMapping("/slideshow")
@CrossOrigin(origins = "http://localhost:3000")
public class SlideShowController {

	@Autowired
	UserServiceImpl userService;
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
		StatusMessageDto<SlideShowEntity> result = new StatusMessageDto<>();
		SlideShowEntity slideShowEntity = slideShowService.getSlideShowById(idSlideShow);
		if(slideShowEntity == null) {
			result.setStatus(HttpStatus.BAD_REQUEST.value());
			result.setMessage("Data Tidak Ditemukan!");
			result.setData(slideShowEntity);
			return ResponseEntity.badRequest().body(result);
		} 
			result.setStatus(HttpStatus.OK.value());
			result.setMessage("Data Ditemukan!");
			result.setData(slideShowEntity);
			return ResponseEntity.ok(result);
	}
		
	
	//POST
	@PostMapping("/add-slideshow")
	public ResponseEntity<?> addSlideShow(@CurrentUser UserPrincipal userPrincipal , @RequestBody SlideShowDto dto) {
		SlideShowEntity slideShowEntity = slideShowService.addSlideShow(userPrincipal, dto);
		StatusMessageDto<SlideShowEntity> result = new StatusMessageDto<>();
		if(slideShowEntity == null) {
			result.setStatus(HttpStatus.BAD_REQUEST.value());
			result.setMessage("Data sudah diinputkan!");
			result.setData(slideShowEntity);
			return ResponseEntity.badRequest().body(result);
		} else {
			result.setStatus(HttpStatus.OK.value());
			result.setMessage("Data berhasil diinputkan!");
			result.setData(slideShowEntity);
			return ResponseEntity.ok(result);
		}
		
	}
	
	//UPDATE
	@PutMapping("/update-slideshow/{idSlideShow}")
	public ResponseEntity<?> updateSlideShow(@PathVariable Integer idSlideShow,
			@RequestBody SlideShowDto dto){
		SlideShowEntity slideShowEntity = slideShowService.updateSlideShow(idSlideShow, dto);
		StatusMessageDto<SlideShowEntity> result = new StatusMessageDto<>();
		result.setStatus(HttpStatus.OK.value());
		result.setMessage("Data berhasil diupdate!");
		result.setData(slideShowEntity);
		return ResponseEntity.ok(result);
	}
	
	//DELETE
	@PutMapping("/delete-slideshow/{idSlideShow}")
	public ResponseEntity<?> deleteSlideShow(@PathVariable Integer idSlideShow){
		SlideShowEntity slideShowEntity = slideShowService.deleteSlideShow(idSlideShow);
		StatusMessageDto<SlideShowEntity> result = new StatusMessageDto<>();
		result.setStatus(HttpStatus.OK.value());
		result.setMessage("Data berhasil dihapus!");
		result.setData(slideShowEntity);
		return ResponseEntity.ok(result);
	}
	
	//DELETE DATA SLIDESHOW
	@DeleteMapping("/delete-data-slideshow/{idSlideShow}")
	public ResponseEntity<?> deleteDataSlideShow(@PathVariable Integer idSlideShow){
		SlideShowEntity slideShowEntity = slideShowService.deleteDataSlideShow(idSlideShow);
		StatusMessageDto<SlideShowEntity> result = new StatusMessageDto<>();
		result.setStatus(HttpStatus.OK.value());
		result.setMessage("Data berhasil dihapus!");
		result.setData(slideShowEntity);
		return ResponseEntity.ok(result);
		
	}
	
}
