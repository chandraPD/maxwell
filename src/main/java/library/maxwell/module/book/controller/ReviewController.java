package library.maxwell.module.book.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import library.maxwell.config.security.auth.CurrentUser;
import library.maxwell.config.security.auth.UserPrincipal;
import library.maxwell.module.book.dto.ReviewDto;
import library.maxwell.module.book.dto.StatusMessageDto;
import library.maxwell.module.book.entity.AuthorEntity;
import library.maxwell.module.book.entity.ReviewEntity;

import library.maxwell.module.book.repository.ReviewRepository;

import library.maxwell.module.book.service.ReviewService;

@RestController
@RequestMapping("/review")
@CrossOrigin
public class ReviewController {
	@Autowired
	ReviewService service;

	@Autowired
	ReviewRepository repo;

	@PostMapping("/post/{id}")
	public ResponseEntity<?> post(@CurrentUser UserPrincipal userPrincipal, @PathVariable Integer id,
			@RequestBody ReviewDto Dto) {
		Integer id3 = userPrincipal.getId();
		Boolean existsByAuthor = repo.existsByBookEntityBookIdAndUserEntityUserId(id, id3);
		if (existsByAuthor) {
			Boolean status = repo.findStatus(id, id3);
			if (status) {
				ReviewEntity reviewEntity = service.update(userPrincipal, id, Dto);
				return ResponseEntity.ok(reviewEntity);
			} else {
				ReviewEntity reviewEntity = service.update(userPrincipal, id, Dto);
				return ResponseEntity.ok(reviewEntity);
			}
		} else {
			ReviewEntity reviewEntity = service.post(userPrincipal, id, Dto);
			return ResponseEntity.ok(reviewEntity);
		}
	}

	@PutMapping("/delete/{id}")
	public ResponseEntity<?> delete(@CurrentUser UserPrincipal userPrincipal, @PathVariable Integer id,
			@RequestBody ReviewDto Dto) {
		Integer id3 = userPrincipal.getId();
		Boolean existsByAuthor = repo.existsByBookEntityBookIdAndUserEntityUserId(id, id3);
		if (existsByAuthor) {
			Boolean status = repo.findStatus(id, id3);
			if (status) {
				ReviewEntity reviewEntity = service.delete(userPrincipal, id);
				return ResponseEntity.ok(reviewEntity);
			}
		}
		StatusMessageDto<AuthorEntity> result = new StatusMessageDto<>();
		result.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
		result.setMessage("Author already exist!");
		result.setData(null);
		return ResponseEntity.ok(result);
	}

	@GetMapping("/get/{id}")
	public ResponseEntity<?> get(@CurrentUser UserPrincipal userPrincipal, @PathVariable Integer id) {
		Boolean wishlistEntity = service.get(userPrincipal, id);
		return ResponseEntity.ok(wishlistEntity);
	}

	@GetMapping("/rate/{id}")
	public ResponseEntity<?> rate(@PathVariable Integer id) {
		Double wishlistEntity = service.findRate(id);
		return ResponseEntity.ok(wishlistEntity);
	}

	@GetMapping("/getAll/{id}")
	public ResponseEntity<?> get(@PathVariable Integer id) {
		List<ReviewEntity> wishlistEntity = service.getAll(id);
		return ResponseEntity.ok(wishlistEntity);
	}

}
