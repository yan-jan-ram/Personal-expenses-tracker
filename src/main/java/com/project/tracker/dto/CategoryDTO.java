package com.project.tracker.dto;

import com.project.tracker.entity.CategoryEntity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Schema(
		description = "Category Data Transfer Object to transfer data"
				+ " between client and server"
		)
@Getter
@Setter
public class CategoryDTO {

	@Schema(description = "category Id")
	private Long categoryId;
	
	@Schema(description = "category name")
	@NotNull(message = "{category.name.absent}")
	@Pattern(regexp = "([A-Z][a-z]+)+([ ]+[A-Z]*[a-z]+)*", message = "{category.name.invalid}")
	private String categoryName;
	
	public static CategoryEntity prepareCategoryEntity(CategoryDTO categoryDTO) {
		CategoryEntity categoryEntity = new CategoryEntity();
		
		categoryEntity.setCategoryId(categoryDTO.getCategoryId());
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
