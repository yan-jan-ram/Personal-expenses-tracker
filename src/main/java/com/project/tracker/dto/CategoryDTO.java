package com.project.tracker.dto;

import com.project.tracker.entity.CategoryEntity;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryDTO {

	private Long categoryId;
	
	@NotNull(message = "{category.name.absent}")
	@Pattern(regexp = "([A-Z][a-z]+)+([ ]+[A-Z]*[a-z]+)*", message = "{category.name.invalid}")
	private String categoryName;
	
	public static CategoryEntity prepareCategoryEntity(CategoryDTO categoryDTO) {
		CategoryEntity categoryEntity = new CategoryEntity();
		
		categoryEntity.setCategoryName(categoryDTO.getCategoryName());
		
		return categoryEntity;
	}
	
	public static CategoryDTO prepareCategoryDTO(CategoryEntity categoryEntity) {
		CategoryDTO categoryDTO = new CategoryDTO();
		
		categoryDTO.setCategoryId(categoryEntity.getCategoryId());
		categoryDTO.setCategoryName(categoryEntity.getCategoryName());
		
		return categoryDTO;
	}
}
