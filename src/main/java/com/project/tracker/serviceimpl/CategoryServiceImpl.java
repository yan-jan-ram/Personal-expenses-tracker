package com.project.tracker.serviceimpl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.project.tracker.dto.CategoryDTO;
import com.project.tracker.entity.CategoryEntity;
import com.project.tracker.exception.CategoryException;
import com.project.tracker.repository.CategoryRepository;
import com.project.tracker.service.CategoryService;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service(value = "categoryService")
@Transactional
public class CategoryServiceImpl implements CategoryService {

	private final CategoryRepository categoryRepository;

	@Override
	public CategoryDTO createCategory(CategoryDTO categoryDTO) throws CategoryException {
		// TODO Auto-generated method stub
//		if (categoryRepository
//				.findById(categoryDTO.getCategoryId())
//				.isPresent()) {
//			throw new CategoryException("service.CATEGORY_ALREADY_EXISTS");
//		}
		
		CategoryEntity categoryEntity = CategoryDTO.prepareCategoryEntity(categoryDTO);
		CategoryEntity savedEntity = categoryRepository.save(categoryEntity);
		
		return CategoryDTO.prepareCategoryDTO(savedEntity);
	}
	
	@Override
	public List<CategoryDTO> createCategories(List<CategoryDTO> categoryDTOs) throws CategoryException {
		// TODO Auto-generated method stub
		List<CategoryEntity> categories = categoryDTOs
				.stream()
				.map((category) -> CategoryDTO.prepareCategoryEntity(category))
				.collect(Collectors.toList());
		List<CategoryEntity> savedEntities = categoryRepository.saveAll(categories);
		
		return savedEntities
				.stream()
				.map(CategoryDTO::prepareCategoryDTO)
				.collect(Collectors.toList());
	}

	@Override
	public CategoryDTO getCategoryById(Long categoryId) throws CategoryException {
		// TODO Auto-generated method stub
		CategoryEntity category = categoryRepository
				.findById(categoryId)
				.orElseThrow(() -> new CategoryException("service.CATEGORY_NOT_FOUND"));
		
		return CategoryDTO.prepareCategoryDTO(category);
	}

	@Override
	public List<CategoryDTO> getAllCategories() throws CategoryException {
		// TODO Auto-generated method stub
		List<CategoryEntity> categories = categoryRepository.findAll();
		if (categories.isEmpty()) {
			throw new CategoryException("service.NO_CATEGORIES_FOUND");
		}
		
		return categories
				.stream()
				.map((category) -> CategoryDTO.prepareCategoryDTO(category))
				.collect(Collectors.toList());
	}

	@Override
	public CategoryDTO updateCategoryNameById(Long categoryId, String categoryName) throws CategoryException {
		// TODO Auto-generated method stub
		CategoryEntity categoryEntity = categoryRepository
				.findById(categoryId)
				.orElseThrow(() -> new CategoryException("service.CATEGORY_NOT_FOUND"));
		categoryEntity.setCategoryName(categoryName);
		CategoryEntity savedEntity = categoryRepository.save(categoryEntity);
		
		return CategoryDTO.prepareCategoryDTO(savedEntity);
	}

	@Override
	public void deleteCategoryById(Long categoryId) throws CategoryException {
		// TODO Auto-generated method stub
		categoryRepository
				.findById(categoryId)
				.orElseThrow(() -> new CategoryException("service.CATEGORY_NOT_FOUND"));
		categoryRepository.deleteById(categoryId);
	}

}
