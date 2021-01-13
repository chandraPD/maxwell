package library.maxwell.module.book.service;

import java.util.List;

import library.maxwell.config.security.auth.UserPrincipal;
import library.maxwell.module.book.dto.AuthorDto;
import library.maxwell.module.book.dto.StatusMessageDto;
import library.maxwell.module.book.entity.AuthorEntity;

public interface AuthorService {
	List<AuthorEntity> findAll();

	AuthorEntity post(UserPrincipal userPrincipal, AuthorDto dto);

	AuthorEntity getId(Integer id);

	AuthorEntity update(UserPrincipal userPrincipal, Integer id, AuthorDto dto);

	AuthorEntity delete(UserPrincipal userPrincipal, Integer id);

	StatusMessageDto author(String author);

	AuthorEntity post2(UserPrincipal userPrincipal, AuthorDto dto);

	AuthorEntity update2(UserPrincipal userPrincipal, AuthorDto dto, Integer id);
}
