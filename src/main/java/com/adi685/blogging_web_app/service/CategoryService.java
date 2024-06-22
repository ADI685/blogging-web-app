package com.adi685.blogging_web_app.service;

import com.adi685.blogging_web_app.DTO.CategoryDTO;

import java.util.List;

public interface CategoryService {

	CategoryDTO addCategory(CategoryDTO categoryDTO);

	CategoryDTO updateCategory(CategoryDTO categoryDTO, Integer id);

	List<CategoryDTO> getAllCategories();

	CategoryDTO getCategoryById(Integer id);

	void deleteCategoryById(Integer id);

}
