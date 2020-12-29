package library.maxwell.module.book.service;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import library.maxwell.module.book.dto.CategoryDto;
import library.maxwell.module.book.dto.StatusMessageDto;
import library.maxwell.module.book.entity.CategoryEntity;
import library.maxwell.module.book.repository.CategoryRepository;import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {
		
	@Autowired
	CategoryRepository categoryRepository;

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
	public ResponseEntity<?> addCategory(CategoryDto dto) {
		// TODO Auto-generated method stub
		CategoryEntity categoryEntity = convertToCategoryEntity(dto);
		categoryRepository.save(categoryEntity);
		return ResponseEntity.ok(categoryEntity);
	}
	
	@Override
	public ResponseEntity<?> updateCategory(Integer id, CategoryDto dto) {
		// TODO Auto-generated method stub
		CategoryEntity categoryEntity = categoryRepository.findById(id).get();
		categoryEntity.setCategory(dto.getCategory());
		categoryRepository.save(categoryEntity);
		return ResponseEntity.ok(categoryEntity);
	}

	@Override
	public ResponseEntity<?> deleteCategory(Integer id) {
		// TODO Auto-generated method stub
		CategoryEntity categoryEntity = categoryRepository.findById(id).get();
		StatusMessageDto<CategoryEntity> result = new StatusMessageDto<>();
		
		categoryEntity.setStatus(false);
		categoryRepository.save(categoryEntity);
		result.setStatus(HttpStatus.OK.value());
		result.setMessage("Data has been deleted!");
		result.setData(categoryEntity);
		return ResponseEntity.ok(result);
			
	}
	
	public CategoryEntity convertToCategoryEntity(CategoryDto dto) {
		CategoryEntity categoryEntity = new CategoryEntity();
		categoryEntity.setCategory(dto.getCategory());
		return categoryEntity;
	}

	

	


}
