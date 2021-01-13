package library.maxwell.module.book.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import library.maxwell.config.security.auth.CurrentUser;
import library.maxwell.config.security.auth.UserPrincipal;
import library.maxwell.module.book.dto.AuthorDto;
import library.maxwell.module.book.dto.ReviewDto;
import library.maxwell.module.book.entity.AuthorEntity;
import library.maxwell.module.book.entity.BookEntity;
import library.maxwell.module.book.entity.ReviewEntity;
import library.maxwell.module.book.entity.WishlistEntity;
import library.maxwell.module.book.repository.BookRepository;
import library.maxwell.module.book.repository.ReviewRepository;
import library.maxwell.module.log.dto.LogDto;
import library.maxwell.module.log.entity.LogEntity;
import library.maxwell.module.log.repository.LogRepository;
import library.maxwell.module.user.entity.UserDetailEntity;
import library.maxwell.module.user.entity.UserEntity;
import library.maxwell.module.user.repository.UserDetailRepository;
import library.maxwell.module.user.repository.UserRepository;

@Service
@Transactional
public class ReviewServiceImpl implements ReviewService {
	@Autowired
	ReviewRepository repo;
	
	@Autowired
	BookRepository repo2;
	
	@Autowired
	UserRepository repo3;
	
	@Autowired
	LogRepository repo4;
	
	@Autowired
	UserDetailRepository repo5;
	
	public ReviewEntity convertToReviewEntity(ReviewDto Dto) {
		ReviewEntity reviewEntity=new ReviewEntity();
		reviewEntity.setRate(Dto.getRate());
		reviewEntity.setComment(Dto.getComment());
		return reviewEntity;
	}

	@Override
	public List<ReviewEntity> getAll(Integer Id) {				
		List<ReviewEntity> entities=repo.findAll1(Id);		
		return entities;
	}

	@Override
	public ReviewEntity post(UserPrincipal userPrincipal, Integer id,ReviewDto Dto) {
		LocalDateTime now = LocalDateTime.now();
		Integer id2=userPrincipal.getId();
		UserEntity userEntity=repo3.findByUserId(id2);
		BookEntity bookEntity=repo2.findById(id).get();
		ReviewEntity entity=convertToReviewEntity(Dto);
		entity.setStatus(true);
		entity.setBookEntity(bookEntity);
		entity.setUserEntity(userEntity);
		repo.save(entity);		
		LogEntity logEntity=new LogEntity();
		logEntity.setAction("Post");
		logEntity.setDateTime(now);
		logEntity.setStatus(true);
		logEntity.setUserEntity(userEntity);
		logEntity.setDescription("Melakukan Tambah Review");
		repo4.save(logEntity);
		return entity;
	}

	@Override
	public ReviewEntity update(UserPrincipal userPrincipal, Integer id,ReviewDto Dto) {		
		LocalDateTime now = LocalDateTime.now();
		Integer id2=userPrincipal.getId();
		UserEntity userEntity=repo3.findByUserId(id2);
		BookEntity bookEntity=repo2.findById(id).get();
		ReviewEntity entity=repo.getEntities(id, id2);
		Boolean status= repo.findStatus(id,id2);
		System.out.println(status);
		if(status) {
			entity.setStatus(true);
			entity.setBookEntity(bookEntity);
			entity.setUserEntity(userEntity);
			entity.setComment(Dto.getComment());
			entity.setRate(Dto.getRate());
			repo.save(entity);
			LogEntity logEntity=new LogEntity();
			logEntity.setAction("Update");
			logEntity.setDateTime(now);
			logEntity.setStatus(true);
			logEntity.setUserEntity(userEntity);
			logEntity.setDescription("Melakukan Update Review");
			repo4.save(logEntity);
			return entity;
		} else {
			entity.setStatus(true);
			entity.setBookEntity(bookEntity);
			entity.setUserEntity(userEntity);
			entity.setComment(Dto.getComment());
			entity.setRate(Dto.getRate());
			repo.save(entity);
			LogEntity logEntity=new LogEntity();
			logEntity.setAction("Post");
			logEntity.setDateTime(now);
			logEntity.setStatus(true);
			logEntity.setUserEntity(userEntity);
			logEntity.setDescription("Melakukan Tambah Review");
			repo4.save(logEntity);
			return entity;
		}			
		
	}

	@Override
	public ReviewEntity delete(UserPrincipal userPrincipal, Integer id) {
		LocalDateTime now = LocalDateTime.now();
		Integer id2=userPrincipal.getId();
		UserEntity userEntity=repo3.findByUserId(id2);
		BookEntity bookEntity=repo2.findById(id).get();
		ReviewEntity entity=repo.getEntities(id, id2);
		entity.setStatus(false);
		entity.setBookEntity(bookEntity);
		entity.setUserEntity(userEntity);
		repo.save(entity);
		LogEntity logEntity=new LogEntity();
		logEntity.setAction("Delete");
		logEntity.setDateTime(now);
		logEntity.setStatus(true);
		logEntity.setUserEntity(userEntity);
		logEntity.setDescription("Melakukan Delete Review");
		repo4.save(logEntity);
		return entity;
	}

	@Override
	public Boolean get(UserPrincipal userPrincipal, Integer id) {
		Integer id2=userPrincipal.getId();
		Boolean wishlistEntity=repo.findStatus(id, id2);
		return wishlistEntity;
	}

	@Override
	public Double findRate(Integer id) {
		Double rate=repo.findRate(id);
		return rate;
	}

}
