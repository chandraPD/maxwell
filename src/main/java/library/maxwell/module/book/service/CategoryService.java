package library.maxwell.module.book.service;

import org.springframework.http.ResponseEntity;

import library.maxwell.module.book.dto.CategoryDto;


public interface CategoryService {
	ResponseEntity<?> getCategory();
	ResponseEntity<?> getByCategory(String category);
	ResponseEntity<?> getActiveCategory();
	ResponseEntity<?> getInactiveCategory();
	ResponseEntity<?> addCategory(CategoryDto dto);
	ResponseEntity<?> updateCategory(Integer id, CategoryDto dto);
	ResponseEntity<?> deleteCategory(Integer id);
}
