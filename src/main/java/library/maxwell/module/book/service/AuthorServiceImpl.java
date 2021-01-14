package library.maxwell.module.book.service;

import java.time.LocalDateTime;
import java.util.List;

import javax.transaction.Transactional;

import library.maxwell.module.book.dto.StatusMessageDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import library.maxwell.config.security.auth.UserPrincipal;
import library.maxwell.module.book.dto.AuthorDto;
import library.maxwell.module.book.entity.AuthorEntity;
import library.maxwell.module.book.repository.AuthorRepository;
import library.maxwell.module.log.entity.LogEntity;
import library.maxwell.module.log.repository.LogRepository;
import library.maxwell.module.user.entity.UserEntity;
import library.maxwell.module.user.repository.UserRepository;

@Service
@Transactional
public class AuthorServiceImpl implements AuthorService {
	@Autowired
	AuthorRepository repo;

	@Autowired
	LogRepository repo2;

	@Autowired
	UserRepository repo3;

	@Override
	public List<AuthorEntity> findAll() {
		List<AuthorEntity> authorEntities = repo.findAllByStatusIsTrue();
		return authorEntities;
	}

	@Override
	public AuthorEntity post(UserPrincipal userPrincipal, AuthorDto dto) {
		LocalDateTime now = LocalDateTime.now();
		AuthorEntity authorEntity = convertToAuthorEntity(dto);
		repo.save(authorEntity);
		UserEntity userEntity = repo3.findByUserId(userPrincipal.getId());
		LogEntity logEntity = new LogEntity();
		logEntity.setAction("Post");
		logEntity.setDateTime(now);
		logEntity.setStatus(true);
		logEntity.setUserEntity(userEntity);
		logEntity.setDescription("Melakukan Tambah Author");
		repo2.save(logEntity);
		return authorEntity;
	}

	public AuthorEntity convertToAuthorEntity(AuthorDto dto) {
		AuthorEntity authorEntity = new AuthorEntity();
		authorEntity.setAuthorName(dto.getAuthorName());
		return authorEntity;
	}

	@Override
	public AuthorEntity getId(Integer id) {
		AuthorEntity authorEntities = repo.findById(id).get();
		return authorEntities;
	}

	@Override
	public AuthorEntity update(UserPrincipal userPrincipal, Integer id, AuthorDto dto) {
		LocalDateTime now = LocalDateTime.now();
		AuthorEntity authorEntity = repo.findById(id).get();
		authorEntity.setAuthorName(dto.getAuthorName());
		repo.save(authorEntity);
		UserEntity userEntity = repo3.findByUserId(userPrincipal.getId());
		LogEntity logEntity = new LogEntity();
		logEntity.setAction("Update");
		logEntity.setDateTime(now);
		logEntity.setStatus(true);
		logEntity.setUserEntity(userEntity);
		logEntity.setDescription("Melakukan Update data Author");
		repo2.save(logEntity);
		return authorEntity;
	}

	@Override
	public AuthorEntity delete(UserPrincipal userPrincipal, Integer id) {
		LocalDateTime now = LocalDateTime.now();
		AuthorEntity authorEntity = repo.findById(id).get();
		authorEntity.setStatus(false);
		;
		repo.save(authorEntity);
		UserEntity userEntity = repo3.findByUserId(userPrincipal.getId());
		LogEntity logEntity = new LogEntity();
		logEntity.setAction("Delete");
		logEntity.setDateTime(now);
		logEntity.setStatus(true);
		logEntity.setUserEntity(userEntity);
		logEntity.setDescription("Melakukan Hapus data Author");
		repo2.save(logEntity);
		return authorEntity;
	}

	@Override
	public String author(String author) {
		AuthorEntity authorEntity = repo.findByStatusIsTrueAndAuthorNameIs(author);
		if(authorEntity != null){
			return authorEntity.getAuthorName();
		}else{
			return "";
		}
	}

	@Override
	public AuthorEntity post2(UserPrincipal userPrincipal, AuthorDto dto) {
		LocalDateTime now = LocalDateTime.now();
		AuthorEntity authorEntity = repo.authorEntity(dto.getAuthorName());
		authorEntity.setStatus(true);
		repo.save(authorEntity);
		UserEntity userEntity = repo3.findByUserId(userPrincipal.getId());
		LogEntity logEntity = new LogEntity();
		logEntity.setAction("Post");
		logEntity.setDateTime(now);
		logEntity.setStatus(true);
		logEntity.setUserEntity(userEntity);
		logEntity.setDescription("Melakukan Tambah Author");
		repo2.save(logEntity);
		return authorEntity;
	}

	@Override
	public AuthorEntity update2(UserPrincipal userPrincipal, AuthorDto dto, Integer id) {
		LocalDateTime now = LocalDateTime.now();
		AuthorEntity authorEntity = repo.findById(id).get();
		AuthorEntity authorEntity2 = repo.authorEntity(dto.getAuthorName());
		authorEntity.setStatus(false);
		authorEntity2.setStatus(true);
		repo.save(authorEntity);
		repo.save(authorEntity2);
		UserEntity userEntity = repo3.findByUserId(userPrincipal.getId());
		LogEntity logEntity = new LogEntity();
		logEntity.setAction("Update");
		logEntity.setDateTime(now);
		logEntity.setStatus(true);
		logEntity.setUserEntity(userEntity);
		logEntity.setDescription("Melakukan Update data Author");
		repo2.save(logEntity);
		return authorEntity;
	}

}
