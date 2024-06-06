package com.project.tracker.service;

import java.util.List;

import com.project.tracker.dto.ExpenseDTO;
import com.project.tracker.exception.ExpenseException;

public interface ExpenseService {

	public ExpenseDTO createExpense(ExpenseDTO expenseDTO) throws ExpenseException;
	public List<ExpenseDTO> createExpenses(List<ExpenseDTO> expenseDTOs) throws ExpenseException;
	public ExpenseDTO getExpenseById(Long expenseId) throws ExpenseException;
	public List<ExpenseDTO> getAllExpenses() throws ExpenseException;
	public ExpenseDTO updateExpenseById(Long expenseId, ExpenseDTO expenseDTO) throws ExpenseException;
	public void deleteExpenseById(Long expenseId) throws ExpenseException;
}
