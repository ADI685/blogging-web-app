package com.adi685.blogging_web_app.service.impl;

import com.adi685.blogging_web_app.DTO.CategoryDTO;
import com.adi685.blogging_web_app.entity.Category;
import com.adi685.blogging_web_app.exception.ResourceNotFoundException;
import com.adi685.blogging_web_app.repositories.CategoryRepository;
import com.adi685.blogging_web_app.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

	private final CategoryRepository categoryRepository;

	private final ModelMapper modelMapper;

	@Override
	public CategoryDTO addCategory(CategoryDTO categoryDTO) {
		Category category = dtotoCategory(categoryDTO);
		Category createdCategory = categoryRepository.save(category);

		return categoryToDTO(createdCategory);
	}

	@Override
	public CategoryDTO updateCategory(CategoryDTO categoryDTO, Integer id) {
		Category category = categoryRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "Id", id));
		category.setCategoryTitle(category.getCategoryTitle());
		category.setCategoryDescription(categoryDTO.getCategoryDescription());
		Category updatedCategory = categoryRepository.save(category);

		return categoryToDTO(updatedCategory);
	}

	@Override
	public List<CategoryDTO> getAllCategories() {
		return categoryRepository.findAll().stream().map(this::categoryToDTO).collect(Collectors.toList());
	}

	@Override
	public CategoryDTO getCategoryById(Integer id) {
		Category category = categoryRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "Id", id));

		return categoryToDTO(category);
	}

	@Override
	public void deleteCategoryById(Integer id) {
		Category category = categoryRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "Id", id));
		categoryRepository.delete(category);
	}

	private Category dtotoCategory(CategoryDTO categoryDTO) {
		return modelMapper.map(categoryDTO, Category.class);
	}

	private CategoryDTO categoryToDTO(Category category) {
		return modelMapper.map(category, CategoryDTO.class);
	}

}
