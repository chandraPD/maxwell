package library.maxwell.module.book.service;

import org.springframework.http.ResponseEntity;

import library.maxwell.config.security.auth.UserPrincipal;
import library.maxwell.module.book.dto.CategoryDto;


public interface CategoryService {
	ResponseEntity<?> getCategory();
	ResponseEntity<?> getByCategory(String category);
	ResponseEntity<?> getCategoryById(Integer id);
	ResponseEntity<?> getActiveCategory();
	ResponseEntity<?> getInactiveCategory();
	ResponseEntity<?> addCategory(UserPrincipal userPrincipal, CategoryDto dto);
	ResponseEntity<?> updateCategory(UserPrincipal userPrincipal, Integer id, CategoryDto dto);
	ResponseEntity<?> deleteCategory(UserPrincipal userPrincipal, Integer id);
}
