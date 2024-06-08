package com.project.tracker.controller;

import java.util.List;
import java.util.Map;

import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.tracker.dto.CategoryDTO;
import com.project.tracker.exception.CategoryException;
import com.project.tracker.service.CategoryService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;

@Tag(
		name = "CRUD REST APIs for Category resource",
		description = "Contains methods to create category, create multiple categories, "
				+ "get category, get all categories, update and delete category by Id"
)
@AllArgsConstructor
@RestController
@RequestMapping(value = "/api/categories")
@Validated
public class CategoryController {

	private final CategoryService categoryService;
	
	Environment environment;
	
	@Operation(
			summary = "Create Category REST API", 
			description = "to create and save a category")
	@ApiResponse(
			responseCode = "201",
			description = "HTTP STATUS 201 CREATED"
			)
	//http://localhost:8082/api/categories/create
	@PostMapping(value = "/create", consumes = "application/json", produces = "application/json")
	public ResponseEntity<CategoryDTO> createCategory(@RequestBody @Valid CategoryDTO categoryDTO) throws CategoryException {
		CategoryDTO categoryDto = categoryService.createCategory(categoryDTO);
		
		return new ResponseEntity<>(categoryDto, HttpStatus.CREATED);
	}
	
	@Operation(
			summary = "Create Categories REST API", 
			description = "to create and save multiple categories at once")
	@ApiResponse(
			responseCode = "201",
			description = "HTTP STATUS 201 CREATED"
			)
	//http://localhost:8082/api/categories/bulk
	@PostMapping(value = "/bulk")
	public ResponseEntity<List<CategoryDTO>> createCategories(@RequestBody @Valid List<CategoryDTO> categoryDTOs) throws CategoryException {
		List<CategoryDTO> categoryDtos = categoryService.createCategories(categoryDTOs);
		
		return new ResponseEntity<>(categoryDtos, HttpStatus.CREATED);
	}
	
	@Operation(
			summary = "Get Category REST API", 
			description = "to read a category by Id")
	@ApiResponse(
			responseCode = "200",
			description = "HTTP STATUS 200 OK"
			)
	//http://localhost:8082/api/categories/get/
	@GetMapping(value = "/get/{id}")
	public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable("id") 
	@Min(value = 1, message = "{category.id.invalid}") Long categoryId) throws CategoryException {
		CategoryDTO categoryDTO = categoryService.getCategoryById(categoryId);
		
		return new ResponseEntity<>(categoryDTO, HttpStatus.OK);
	}
	
	@Operation(
			summary = "Get All Categories REST API", 
			description = "to read all categories")
	@ApiResponse(
			responseCode = "200",
			description = "HTTP STATUS 200 OK"
			)
	//http://localhost:8082/api/categories/getAll
	@GetMapping(value = "/getAll")
	public ResponseEntity<List<CategoryDTO>> getAllCategories() throws CategoryException {
		List<CategoryDTO> categoryDTOs = categoryService.getAllCategories();
		
		return new ResponseEntity<>(categoryDTOs, HttpStatus.OK);
	}
	
	@Operation(
			summary = "Put Category REST API", 
			description = "to update a category by Id")
	@ApiResponse(
			responseCode = "200",
			description = "HTTP STATUS 200 OK"
			)
	//http://localhost:8082/api/categories/update/
	@PutMapping(value = "/update/{id}")
	public ResponseEntity<CategoryDTO> updateCategoryNameById(@PathVariable("id")
	@Min(value = 1, message = "{category.id.invalid}") Long categoryId, @RequestBody @Valid Map<String, String> updateRequest) throws CategoryException {
		String categoryName = updateRequest.get("categoryName");
		CategoryDTO categoryDTO = categoryService.updateCategoryNameById(categoryId, categoryName);
		
		return new ResponseEntity<>(categoryDTO, HttpStatus.OK);
	}
	
	@Operation(
			summary = "Delete Category REST API", 
			description = "to delete a category by Id")
	@ApiResponse(
			responseCode = "200",
			description = "HTTP STATUS 200 OK"
			)
	//http://localhost:8082/api/categories/delete/
	@DeleteMapping(value = "/delete/{id}")
	public ResponseEntity<String> deleteCategoryById(@PathVariable("id")
	@Min(value = 1, message = "{category.id.invalid}") Long categoryId) throws CategoryException {
		categoryService.deleteCategoryById(categoryId);
		String message = environment.getProperty("API.CATEGORY_DELETE_SUCCESS");
		
		return new ResponseEntity<>(message, HttpStatus.OK);
	}
	
}
