package com.project.tracker.validator;

import java.time.LocalDate;

import com.project.tracker.dto.ExpenseDTO;
import com.project.tracker.exception.InvalidDateException;

public class ExpenseValidator {

	public static void validate(ExpenseDTO expenseDTO) throws InvalidDateException {
		if (!isValid(expenseDTO.getExpenseDate())) {
			throw new InvalidDateException("validator.INVALID_DATE");
		}
	}
	
	public static Boolean isValid(LocalDate date) {
		
		if (date.isBefore(LocalDate.of(1990, 01, 01))) {
			return false;
		}
		return true;
	}
}
