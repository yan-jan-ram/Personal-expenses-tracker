package com.project.tracker.service;

import java.util.List;

import com.project.tracker.dto.CategoryDTO;
import com.project.tracker.exception.CategoryException;

public interface CategoryService {

	public CategoryDTO createCategory(CategoryDTO categoryDTO) throws CategoryException;
	public List<CategoryDTO> createCategories(List<CategoryDTO> categoryDTOs) throws CategoryException;
	public CategoryDTO getCategoryById(Long categoryId) throws CategoryException;
	public List<CategoryDTO> getAllCategories() throws CategoryException;
	public CategoryDTO updateCategoryNameById(Long categoryId, String categoryName) throws CategoryException;
	public void deleteCategoryById(Long categoryId) throws CategoryException;
}
