package library.maxwell.module.book.service;



import java.io.Console;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import library.maxwell.config.security.auth.UserPrincipal;
import library.maxwell.module.book.dto.CategoryDto;
import library.maxwell.module.book.dto.StatusMessageDto;
import library.maxwell.module.book.entity.CategoryEntity;
import library.maxwell.module.book.repository.CategoryRepository;
import library.maxwell.module.log.entity.LogEntity;
import library.maxwell.module.log.repository.LogRepository;
import library.maxwell.module.user.entity.UserEntity;
import library.maxwell.module.user.repository.UserRepository;

import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {
		
	@Autowired
	CategoryRepository categoryRepository;
	
	@Autowired
	LogRepository logRepository;
	
	@Autowired
	UserRepository userRepository;

	@Override
	public ResponseEntity<?> getCategory() {
		// TODO Auto-generated method stub
		List<CategoryEntity> categoryEntities = categoryRepository.findAll();
		return ResponseEntity.ok(categoryEntities);
	}

	@Override
	public ResponseEntity<?> getByCategory(String category) {
		// TODO Auto-generated method stub
		CategoryEntity categoryEntity = categoryRepository.findByCategory(category);
			return ResponseEntity.ok(categoryEntity);
		
	}
	
	@Override
	public ResponseEntity<?> getCategoryById(Integer id) {
		// TODO Auto-generated method stub
		CategoryEntity categoryEntity = categoryRepository.findById(id).get();
		if(categoryEntity == null) {
			StatusMessageDto<CategoryEntity> result = new StatusMessageDto<>();
			result.setStatus(HttpStatus.BAD_REQUEST.value());
			result.setMessage("Data Not Found!");
			result.setData(categoryEntity);
			return ResponseEntity.badRequest().body(result);
		} else {
			return ResponseEntity.ok(categoryEntity);
		}
	}
	
	@Override
	public ResponseEntity<?> getActiveCategory() {
		// TODO Auto-generated method stub
		List<CategoryEntity> categoryEntities = categoryRepository.findActiveCategory();
		return ResponseEntity.ok(categoryEntities);
	}
	
	@Override
	public ResponseEntity<?> getInactiveCategory() {
		// TODO Auto-generated method stub
		List<CategoryEntity> categoryEntities = categoryRepository.findInactiveCategory();
		return ResponseEntity.ok(categoryEntities);
	}

	@Override
	public ResponseEntity<?> addCategory(UserPrincipal userPrincipal, CategoryDto dto) {
		// TODO Auto-generated method stub
		CategoryEntity categoryEntity = convertToCategoryEntity(dto);
		CategoryEntity categoryExist = categoryRepository.findByCategory(dto.getCategory());
		LogEntity logEntity = new LogEntity();
		UserEntity userEntity = userRepository.findByUserId(userPrincipal.getId());
		LocalDateTime now = LocalDateTime.now();
		
		Boolean existsByCategory = categoryRepository.existsByCategory(dto.getCategory());
		Boolean existsByStatus = categoryRepository.existsByCategoryAndStatusTrue(dto.getCategory());
		System.out.println(existsByCategory);
		if(existsByCategory == true && existsByStatus == true) {
			StatusMessageDto<CategoryEntity> result = new StatusMessageDto<>();
			result.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
			result.setMessage("Category already exist!");
			result.setData(categoryEntity);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
		} else if(existsByCategory == true && existsByStatus == false) {
			categoryExist.setStatus(true);
			categoryRepository.save(categoryExist);
			logEntity.setAction("Post");
			logEntity.setDateTime(now);
			logEntity.setDescription("Menambahkan Category: " + dto.getCategory());
			logEntity.setStatus(true);
			logEntity.setUserEntity(userEntity);
			logRepository.save(logEntity);
			return ResponseEntity.ok(categoryExist);
		}
		else {
			categoryRepository.save(categoryEntity);
			logEntity.setAction("Post");
			logEntity.setDateTime(now);
			logEntity.setDescription("Menambahkan Category: " + dto.getCategory());
			logEntity.setStatus(true);
			logEntity.setUserEntity(userEntity);
			logRepository.save(logEntity);
			return ResponseEntity.ok(categoryEntity);
		}
		
	}
	
	@Override
	public ResponseEntity<?> updateCategory(UserPrincipal userPrincipal, Integer id, CategoryDto dto) {
		// TODO Auto-generated method stub
		CategoryEntity categoryEntity = categoryRepository.findById(id).get();
		LogEntity logEntity = new LogEntity();
		UserEntity userEntity = userRepository.findByUserId(userPrincipal.getId());
		LocalDateTime now = LocalDateTime.now();
		categoryEntity.setCategory(dto.getCategory());
		categoryRepository.save(categoryEntity);
		logEntity.setAction("Update");
		logEntity.setDateTime(now);
		logEntity.setDescription("Mengupdate Category: " + dto.getCategory());
		logEntity.setStatus(true);
		logEntity.setUserEntity(userEntity);
		logRepository.save(logEntity);
		return ResponseEntity.ok(categoryEntity);
	}

	@Override
	public ResponseEntity<?> deleteCategory(UserPrincipal userPrincipal, Integer id) {
		// TODO Auto-generated method stub
		CategoryEntity categoryEntity = categoryRepository.findById(id).get();
		StatusMessageDto<CategoryEntity> result = new StatusMessageDto<>();
		LogEntity logEntity = new LogEntity();
		UserEntity userEntity = userRepository.findByUserId(userPrincipal.getId());
		LocalDateTime now = LocalDateTime.now();
		
		categoryEntity.setStatus(false);
		categoryRepository.save(categoryEntity);
		result.setStatus(HttpStatus.OK.value());
		result.setMessage("Data has been deleted!");
		result.setData(categoryEntity);
		logEntity.setAction("Delete");
		logEntity.setDateTime(now);
		logEntity.setDescription("Menghapus Category: " + categoryEntity.getCategory());
		logEntity.setStatus(true);
		logEntity.setUserEntity(userEntity);
		logRepository.save(logEntity);
		return ResponseEntity.ok(result);
			
	}
	
	public CategoryEntity convertToCategoryEntity(CategoryDto dto) {
		CategoryEntity categoryEntity = new CategoryEntity();
		categoryEntity.setCategory(dto.getCategory());
		return categoryEntity;
	}




	

	


}
