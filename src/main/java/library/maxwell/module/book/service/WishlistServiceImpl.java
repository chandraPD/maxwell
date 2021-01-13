package library.maxwell.module.book.service;

import java.time.LocalDateTime;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import library.maxwell.config.security.auth.UserPrincipal;
import library.maxwell.module.book.entity.BookEntity;
import library.maxwell.module.book.entity.WishlistEntity;
import library.maxwell.module.book.repository.BookRepository;
import library.maxwell.module.book.repository.WishlistRepository;
import library.maxwell.module.log.entity.LogEntity;
import library.maxwell.module.log.repository.LogRepository;
import library.maxwell.module.user.entity.UserEntity;
import library.maxwell.module.user.repository.UserRepository;

@Service
@Transactional
public class WishlistServiceImpl implements WishlistService {
	@Autowired
	WishlistRepository repo;

	@Autowired
	BookRepository repo2;

	@Autowired
	UserRepository repo3;

	@Autowired
	LogRepository repo4;

	@Override
	public WishlistEntity post(UserPrincipal userPrincipal, Integer id) {
		LocalDateTime now = LocalDateTime.now();
		Integer id2 = userPrincipal.getId();
		UserEntity userEntity = repo3.findByUserId(id2);
		BookEntity bookEntity = repo2.findById(id).get();
		WishlistEntity entity = new WishlistEntity();
		entity.setStatus(true);
		entity.setBookEntity(bookEntity);
		entity.setUserEntity(userEntity);
		repo.save(entity);
		LogEntity logEntity = new LogEntity();
		logEntity.setAction("Post");
		logEntity.setDateTime(now);
		logEntity.setStatus(true);
		logEntity.setUserEntity(userEntity);
		logEntity.setDescription("Melakukan Tambah Wishlist");
		repo4.save(logEntity);
		return entity;
	}

	@Override
	public WishlistEntity update(UserPrincipal userPrincipal, Integer id) {
		LocalDateTime now = LocalDateTime.now();
		Integer id2 = userPrincipal.getId();
		UserEntity userEntity = repo3.findByUserId(id2);
		BookEntity bookEntity = repo2.findById(id).get();
		WishlistEntity entity = repo.getEntities(id, id2);
		entity.setStatus(false);
		entity.setBookEntity(bookEntity);
		entity.setUserEntity(userEntity);
		repo.save(entity);
		LogEntity logEntity = new LogEntity();
		logEntity.setAction("Update");
		logEntity.setDateTime(now);
		logEntity.setStatus(true);
		logEntity.setUserEntity(userEntity);
		logEntity.setDescription("Melakukan Cancel Wishlist");
		repo4.save(logEntity);
		return entity;
	}

	@Override
	public WishlistEntity update2(UserPrincipal userPrincipal, Integer id) {
		LocalDateTime now = LocalDateTime.now();
		Integer id2 = userPrincipal.getId();
		UserEntity userEntity = repo3.findByUserId(id2);
		BookEntity bookEntity = repo2.findById(id).get();
		WishlistEntity entity = repo.getEntities(id, id2);
		entity.setStatus(true);
		entity.setBookEntity(bookEntity);
		entity.setUserEntity(userEntity);
		repo.save(entity);
		LogEntity logEntity = new LogEntity();
		logEntity.setAction("Post");
		logEntity.setDateTime(now);
		logEntity.setStatus(true);
		logEntity.setUserEntity(userEntity);
		logEntity.setDescription("Melakukan Tambah Wishlist");
		repo4.save(logEntity);
		return entity;
	}

	@Override
	public Boolean get(UserPrincipal userPrincipal, Integer id) {
		Integer id2 = userPrincipal.getId();
		Boolean wishlistEntity = repo.findStatus(id, id2);
		return wishlistEntity;
	}

	@Override
	public List<WishlistEntity> getAll(UserPrincipal userPrincipal) {
		Integer id = userPrincipal.getId();
		List<WishlistEntity> entities = repo.findAllByStatusIsTrueAndUserEntityUserId(id);
		return entities;
	}

}
