package library.maxwell.module.book.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import library.maxwell.module.book.dto.CategoryDto;
import library.maxwell.module.book.service.CategoryService;

@RestController
@RequestMapping("/category")
@CrossOrigin(origins = "http://localhost:3000")
public class CategoryManagementController {
		
	@Autowired
	CategoryService categoryService;
	
	@GetMapping("/get-all")
	public ResponseEntity<?> getCategory() {
		return categoryService.getCategory();
	}
	
	@GetMapping("/get-by-category/{category}")
	public ResponseEntity<?> getByCategory(@PathVariable String category) {
		return categoryService.getByCategory(category);
	}
	
	@GetMapping("/get-by-id/{id}")
	public ResponseEntity<?> getCategoryById(@PathVariable Integer id) {
		return categoryService.getCategoryById(id);
	}
	
	@GetMapping("/get-all-active")
	public ResponseEntity<?> getActiveCategory(){
		return categoryService.getActiveCategory();
	}
	
	@GetMapping("/get-all-inactive")
	public ResponseEntity<?> getInactiveCategory() {
		return categoryService.getInactiveCategory();
	}
	
	@PostMapping("/add-category")
	public ResponseEntity<?> addCategory(@RequestBody CategoryDto dto) {
		return categoryService.addCategory(dto);
	}
	
	@PutMapping("/update-category/{id}")
	public ResponseEntity<?> updateCategory(@PathVariable Integer id, @RequestBody CategoryDto dto) {
		return categoryService.updateCategory(id, dto);
	}
	
	@PutMapping("/delete-category/{id}")
	public ResponseEntity<?> deleteCategory(@PathVariable Integer id) {
		return categoryService.deleteCategory(id);
	}
	
}
