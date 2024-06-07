package com.project.tracker.controller;

import java.util.List;

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

import com.project.tracker.dto.ExpenseDTO;
import com.project.tracker.exception.ExpenseException;
import com.project.tracker.service.ExpenseService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/api/expenses")
@Validated
public class ExpenseController {

	private final ExpenseService expenseService;
	
	Environment environment;
	
	//http://localhost:8082/api/expenses/create
	@PostMapping(value = "/create", consumes = "application/json", produces = "application/json")
	public ResponseEntity<ExpenseDTO> createExpense(@RequestBody @Valid ExpenseDTO expenseDTO) throws ExpenseException {
		ExpenseDTO expenseDto = expenseService.createExpense(expenseDTO);
		
		return new ResponseEntity<>(expenseDto, HttpStatus.CREATED);
	}
	
	//http://localhost:8082/api/expenses/bulk
	@PostMapping(value = "/bulk")
	public ResponseEntity<List<ExpenseDTO>> createExpenses(@RequestBody @Valid List<ExpenseDTO> expenseDTOs) throws ExpenseException {
		List<ExpenseDTO> expenseDtos = expenseService.createExpenses(expenseDTOs);
		
		return new ResponseEntity<>(expenseDtos, HttpStatus.CREATED);
	}
	
	//http://localhost:8082/api/expenses/get/
	@GetMapping(value = "/get/{id}")
	public ResponseEntity<ExpenseDTO> getExpenseById(@PathVariable("id") Long expenseId) throws ExpenseException {
		ExpenseDTO expenseDto = expenseService.getExpenseById(expenseId);
		
		return new ResponseEntity<>(expenseDto, HttpStatus.OK);
	}
	
	//http://localhost:8082/api/expenses/getAll
	@GetMapping(value = "/getAll")
	public ResponseEntity<List<ExpenseDTO>> getAllExpenses() throws ExpenseException {
		List<ExpenseDTO> expenseDTOs = expenseService.getAllExpenses();
		
		return new ResponseEntity<>(expenseDTOs, HttpStatus.OK);
	}
	
	//http://localhost:8082/api/expenses/update/
	@PutMapping(value = "/update/{id}")
	public ResponseEntity<ExpenseDTO> updateExpenseById(@PathVariable("id") Long expenseId, @RequestBody @Valid ExpenseDTO expenseDTO) throws ExpenseException {
		ExpenseDTO expenseDto = expenseService.updateExpenseById(expenseId, expenseDTO);
		
		return new ResponseEntity<>(expenseDto, HttpStatus.OK);
	}
	
	//http://localhost:8082/api/expenses/delete/
	@DeleteMapping(value = "/delete/{id}")
	public ResponseEntity<String> deleteExpenseById(@PathVariable("id") Long expenseId) throws ExpenseException {
		expenseService.deleteExpenseById(expenseId);
		String message = environment.getProperty("API.EXPENSE_DELETE_SUCCESS");
		
		return new ResponseEntity<>(message, HttpStatus.OK);
	}
}
