package library.maxwell.module.slideshow.controller;




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
import library.maxwell.module.slideshow.service.SlideShowServiceImpl;



@RestController
@RequestMapping("/slideshow")
@CrossOrigin(origins = "http://localhost:3000")
public class SlideShowController {

	@Autowired
	SlideShowServiceImpl slideShowService;
	
	//GET ALL
	@GetMapping("/get-all-slideshow")
	public ResponseEntity<?> getAllSlideShow(){
		return slideShowService.getAllSlideShow();
	}
	
	//GET BY ID
	@GetMapping("/get-slideshow-byId/{idSlideShow}")
	public ResponseEntity<?> getSlideShowById(@PathVariable Integer idSlideShow) {
		StatusMessageDto<SlideShowEntity> result = new StatusMessageDto<>();
		SlideShowEntity slideShowEntity = slideShowService.getSlideShowById(idSlideShow);
		if(slideShowEntity == null) {
			result.setStatus(HttpStatus.BAD_REQUEST.value());
			result.setMessage("Data Tidak Ditemukan!");
			result.setData(null);
		} else {
			result.setStatus(HttpStatus.OK.value());
			result.setMessage("Data Ditemukan!");
			result.setData(slideShowEntity);
		}
			return ResponseEntity.ok(result);
	}
		
	//GET BY ACTIVE STATUS
	@GetMapping("/get-all-active")
	public ResponseEntity<?> getAllActive(){
		return slideShowService.getAllActive();
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
	public ResponseEntity<?> updateSlideShow(@CurrentUser UserPrincipal userPrincipal, @PathVariable Integer idSlideShow,
			@RequestBody SlideShowDto dto){
		SlideShowEntity slideShowEntity = slideShowService.updateSlideShow(userPrincipal, idSlideShow, dto);
		StatusMessageDto<SlideShowEntity> result = new StatusMessageDto<>();
		result.setStatus(HttpStatus.OK.value());
		result.setMessage("Data berhasil diupdate!");
		result.setData(slideShowEntity);
		return ResponseEntity.ok(result);
	}
	
	//DELETE
	@PutMapping("/delete-slideshow/{idSlideShow}")
	public ResponseEntity<?> deleteSlideShow(@CurrentUser UserPrincipal userPrincipal , @PathVariable Integer idSlideShow){
		SlideShowEntity slideShowEntity = slideShowService.deleteSlideShow(userPrincipal, idSlideShow);
		StatusMessageDto<SlideShowEntity> result = new StatusMessageDto<>();
		result.setStatus(HttpStatus.OK.value());
		result.setMessage("Data berhasil dihapus!");
		result.setData(slideShowEntity);
		return ResponseEntity.ok(result);
	}
	
	//DELETE DATA SLIDESHOW YANG DIGUNAKAN
	@DeleteMapping("/delete-data-slideshow/{idSlideShow}")
	public ResponseEntity<?> deleteDataSlideShow(@CurrentUser UserPrincipal userPrincipal, @PathVariable Integer idSlideShow){
		SlideShowEntity slideShowEntity = slideShowService.deleteDataSlideShow(userPrincipal, idSlideShow);
		StatusMessageDto<SlideShowEntity> result = new StatusMessageDto<>();
		result.setStatus(HttpStatus.OK.value());
		result.setMessage("Data berhasil dihapus!");
		result.setData(slideShowEntity);
		return ResponseEntity.ok(result);
		
	}
	
	@PutMapping("update-status/{idSlideShow}/{status}")
	public ResponseEntity<?> editStatus(@CurrentUser UserPrincipal userPrincipal,
			@PathVariable Integer idSlideShow, @PathVariable Integer status) {
		Boolean statusSlideShow;
		if(status == 1) {
			statusSlideShow = true;
		} else {
			statusSlideShow = false;
		}
		SlideShowEntity slideShowEntity = slideShowService.editStatus(userPrincipal, idSlideShow, statusSlideShow);
		StatusMessageDto<SlideShowEntity> result = new StatusMessageDto<>();
		result.setStatus(HttpStatus.OK.value());
		result.setMessage("Data status berhasil diupdate!");
		result.setData(slideShowEntity);
		return ResponseEntity.ok(result);
		
	}
	
}
