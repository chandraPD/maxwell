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
import library.maxwell.module.book.dto.AuthorDto;
import library.maxwell.module.book.dto.StatusMessageDto;
import library.maxwell.module.book.entity.AuthorEntity;
import library.maxwell.module.book.repository.AuthorRepository;
import library.maxwell.module.book.service.AuthorService;
import library.maxwell.module.book.service.BookService;

@RestController
@RequestMapping("/author")
@CrossOrigin
public class AuthorController {
	@Autowired
	AuthorService authorService;

	@Autowired
	AuthorRepository repo;

	@Autowired
	BookService service;

	@GetMapping("/getAll")
	public ResponseEntity<?> getAll() {
		List<AuthorEntity> authorEntities = authorService.findAll();
		return ResponseEntity.ok(authorEntities);
	}

	@PostMapping("/post")
	public ResponseEntity<?> post(@CurrentUser UserPrincipal user, @RequestBody AuthorDto dto) {
		Boolean existsByAuthor = repo.existsByAuthorName(dto.getAuthorName());
		if (existsByAuthor) {
			Boolean status = repo.findStatus(dto.getAuthorName());
			if (!status) {
				AuthorEntity authorEntity = authorService.post2(user, dto);
				return ResponseEntity.ok(authorEntity);
			} else {
				StatusMessageDto<AuthorEntity> result = new StatusMessageDto<>();
				result.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
				result.setMessage("Category already exist!");
				result.setData(null);
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
			}
		} else {
			AuthorEntity authorEntity = authorService.post(user, dto);
			return ResponseEntity.ok(authorEntity);
		}
	}

	@GetMapping("/getid/{id}")
	public ResponseEntity<?> getId(@PathVariable Integer id) {
		AuthorEntity authorEntities = authorService.getId(id);
		return ResponseEntity.ok(authorEntities);
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<?> update(@CurrentUser UserPrincipal user, @RequestBody AuthorDto dto,
			@PathVariable Integer id) {
		Boolean existsByAuthor = repo.existsByAuthorName(dto.getAuthorName());
		if (existsByAuthor) {
			Boolean status = repo.findStatus(dto.getAuthorName());
			if (!status) {
				AuthorEntity authorEntity = authorService.update2(user, dto, id);
				return ResponseEntity.ok(authorEntity);
			} else {
				StatusMessageDto<AuthorEntity> result = new StatusMessageDto<>();
				result.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
				result.setMessage("Category already exist!");
				result.setData(null);
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
			}
		} else {
			AuthorEntity authorEntity = authorService.update(user, id, dto);
			return ResponseEntity.ok(authorEntity);
		}
	}

	@PutMapping("/delete/{id}")
	public ResponseEntity<?> delete(@CurrentUser UserPrincipal user, @PathVariable Integer id) {
		AuthorEntity authorEntity = authorService.delete(user, id);
		return ResponseEntity.ok(authorEntity);
	}

	@GetMapping("/getAuthor/{author}")
	public ResponseEntity<?> author(@PathVariable String author) {

		StatusMessageDto author2 = authorService.author(author);
		return ResponseEntity.ok(author2);
	}

	@GetMapping("/getCount/{id}")
	public ResponseEntity<?> author(@PathVariable Integer id) {
		StatusMessageDto author2 = service.getCount(id);
		return ResponseEntity.ok(author2);
	}

}
