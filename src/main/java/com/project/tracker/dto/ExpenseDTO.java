package com.project.tracker.dto;

import java.time.LocalDate;

import com.project.tracker.entity.ExpenseEntity;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExpenseDTO {

	private Long expenseId;
	
	@NotNull(message = "{expense.amount.absent}")
	private Double amount;
	
	@NotNull(message = "{expense.date.absent}")
	@PastOrPresent(message = "{expense.date.invalid}")
	private LocalDate expenseDate;

	private CategoryDTO category;
	
	public static ExpenseEntity prepareExpenseEntity(ExpenseDTO expenseDTO) {
		ExpenseEntity expenseEntity = new ExpenseEntity();
		
		expenseEntity.setExpenseId(expenseDTO.getExpenseId());
		expenseEntity.setAmount(expenseDTO.getAmount());
		expenseEntity.setExpenseDate(expenseDTO.getExpenseDate());
		expenseEntity.setCategory(CategoryDTO.prepareCategoryEntity(expenseDTO.getCategory()));
		
		return expenseEntity;
	}
	
	public static ExpenseDTO prepareExpenseDTO(ExpenseEntity expenseEntity) {
		ExpenseDTO expenseDTO = new ExpenseDTO();
		
		expenseDTO.setExpenseId(expenseEntity.getExpenseId());
		expenseDTO.setAmount(expenseEntity.getAmount());
		expenseDTO.setExpenseDate(expenseEntity.getExpenseDate());
		expenseDTO.setCategory(CategoryDTO.prepareCategoryDTO(expenseEntity.getCategory()));
		
		return expenseDTO;
	}
}
