package com.adi685.blogging_web_app.contoller;

import com.adi685.blogging_web_app.DTO.CategoryDTO;
import com.adi685.blogging_web_app.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.adi685.blogging_web_app.enums.Constants.DELETED_SUCCESFULLY_STRING;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;

	@PostMapping("/")
	private ResponseEntity<CategoryDTO> addCategory(@Valid @RequestBody CategoryDTO categoryDTO) {
		CategoryDTO savedCategoryDTO = categoryService.addCategory(categoryDTO);

		return new ResponseEntity<>(savedCategoryDTO, HttpStatus.CREATED);
	}

	@PutMapping("/{id}")
	private ResponseEntity<CategoryDTO> updateCategory(@PathVariable Integer id,
			@Valid @RequestBody CategoryDTO categoryDTO) {
		CategoryDTO updatedCategoryDTO = categoryService.updateCategory(categoryDTO, id);

		return new ResponseEntity<>(updatedCategoryDTO, HttpStatus.OK);
	}

	@GetMapping("/")
	private ResponseEntity<List<CategoryDTO>> getAllCategories() {
		List<CategoryDTO> categoryDTOList = categoryService.getAllCategories();

		return new ResponseEntity<>(categoryDTOList, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	private ResponseEntity<CategoryDTO> getCategoryById(@PathVariable Integer id) {
		CategoryDTO categoryDTO = categoryService.getCategoryById(id);
		return new ResponseEntity<>(categoryDTO, HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	private ResponseEntity<String> deleteCategoryById(@PathVariable Integer id) {
		categoryService.deleteCategoryById(id);
		return new ResponseEntity<>(DELETED_SUCCESFULLY_STRING, HttpStatus.OK);
	}

}
