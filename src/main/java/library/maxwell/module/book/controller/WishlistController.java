package library.maxwell.module.book.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import library.maxwell.config.security.auth.CurrentUser;
import library.maxwell.config.security.auth.UserPrincipal;
import library.maxwell.module.book.entity.WishlistEntity;
import library.maxwell.module.book.repository.WishlistRepository;
import library.maxwell.module.book.service.WishlistService;

@RestController
@RequestMapping("/wishlist")
@CrossOrigin(origins = "http://localhost:3000")
public class WishlistController {
	@Autowired
	WishlistService service;

	@Autowired
	WishlistRepository repo;

	@PostMapping("/post/{id}")
	public ResponseEntity<?> post(@CurrentUser UserPrincipal userPrincipal, @PathVariable Integer id) {
		Integer id3 = userPrincipal.getId();
		Boolean existsByAuthor = repo.existsByBookEntityBookIdAndUserEntityUserId(id, id3);
		if (existsByAuthor) {
			Boolean status = repo.findStatus(id, id3);
			if (status) {
				WishlistEntity wishlistEntity = service.update(userPrincipal, id);
				return ResponseEntity.ok(wishlistEntity);
			} else {
				WishlistEntity wishlistEntity = service.update2(userPrincipal, id);
				return ResponseEntity.ok(wishlistEntity);
			}
		} else {
			WishlistEntity wishlistEntity = service.post(userPrincipal, id);
			return ResponseEntity.ok(wishlistEntity);
		}
	}

	@GetMapping("/get/{id}")
	public ResponseEntity<?> get(@CurrentUser UserPrincipal userPrincipal, @PathVariable Integer id) {
		Boolean wishlistEntity = service.get(userPrincipal, id);
		return ResponseEntity.ok(wishlistEntity);
	}

	@GetMapping("/getAll")
	public ResponseEntity<?> get(@CurrentUser UserPrincipal userPrincipal) {
		List<WishlistEntity> wishlistEntity = service.getAll(userPrincipal);
		return ResponseEntity.ok(wishlistEntity);
	}

}
