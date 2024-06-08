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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;

@Tag(
		name = "CRUD REST APIs for Expense resource",
		description = "Contains methods to create expense, create multiple expenses, "
				+ "get expense, get all expenses, update and delete expense by Id"
)
@AllArgsConstructor
@RestController
@RequestMapping(value = "/api/expenses")
@Validated
public class ExpenseController {

	private final ExpenseService expenseService;
	
	Environment environment;
	
	@Operation(
			summary = "Create Expense REST API", 
			description = "to create and save an expense")
	@ApiResponse(
			responseCode = "201",
			description = "HTTP STATUS 201 CREATED"
			)
	//http://localhost:8082/api/expenses/create
	@PostMapping(value = "/create", consumes = "application/json", produces = "application/json")
	public ResponseEntity<ExpenseDTO> createExpense(@RequestBody @Valid ExpenseDTO expenseDTO) throws ExpenseException {
		ExpenseDTO expenseDto = expenseService.createExpense(expenseDTO);
		
		return new ResponseEntity<>(expenseDto, HttpStatus.CREATED);
	}
	
	@Operation(
			summary = "Create Expenses REST API", 
			description = "to create and save multiple expenses at once")
	@ApiResponse(
			responseCode = "201",
			description = "HTTP STATUS 201 CREATED"
			)
	//http://localhost:8082/api/expenses/bulk
	@PostMapping(value = "/bulk")
	public ResponseEntity<List<ExpenseDTO>> createExpenses(@RequestBody @Valid List<ExpenseDTO> expenseDTOs) throws ExpenseException {
		List<ExpenseDTO> expenseDtos = expenseService.createExpenses(expenseDTOs);
		
		return new ResponseEntity<>(expenseDtos, HttpStatus.CREATED);
	}
	
	@Operation(
			summary = "Get Expense REST API", 
			description = "to read an expense by Id")
	@ApiResponse(
			responseCode = "200",
			description = "HTTP STATUS 200 OK"
			)
	//http://localhost:8082/api/expenses/get/
	@GetMapping(value = "/get/{id}")
	public ResponseEntity<ExpenseDTO> getExpenseById(@PathVariable("id")
	@Min(value = 1, message = "{expense.id.invalid}") Long expenseId) throws ExpenseException {
		ExpenseDTO expenseDto = expenseService.getExpenseById(expenseId);
		
		return new ResponseEntity<>(expenseDto, HttpStatus.OK);
	}
	
	@Operation(
			summary = "Get All Expenses REST API", 
			description = "to read all expenses")
	@ApiResponse(
			responseCode = "200",
			description = "HTTP STATUS 200 OK"
			)
	//http://localhost:8082/api/expenses/getAll
	@GetMapping(value = "/getAll")
	public ResponseEntity<List<ExpenseDTO>> getAllExpenses() throws ExpenseException {
		List<ExpenseDTO> expenseDTOs = expenseService.getAllExpenses();
		
		return new ResponseEntity<>(expenseDTOs, HttpStatus.OK);
	}
	
	@Operation(
			summary = "Put Expense REST API", 
			description = "to update an expense by Id")
	@ApiResponse(
			responseCode = "200",
			description = "HTTP STATUS 200 OK"
			)
	//http://localhost:8082/api/expenses/update/
	@PutMapping(value = "/update/{id}")
	public ResponseEntity<ExpenseDTO> updateExpenseById(@PathVariable("id")
	@Min(value = 1, message = "{expense.id.invalid}") Long expenseId, @RequestBody @Valid ExpenseDTO expenseDTO) throws ExpenseException {
		ExpenseDTO expenseDto = expenseService.updateExpenseById(expenseId, expenseDTO);
		
		return new ResponseEntity<>(expenseDto, HttpStatus.OK);
	}
	
	@Operation(
			summary = "Delete Expense REST API", 
			description = "to delete an expense by Id")
	@ApiResponse(
			responseCode = "200",
			description = "HTTP STATUS 200 OK"
			)
	//http://localhost:8082/api/expenses/delete/
	@DeleteMapping(value = "/delete/{id}")
	public ResponseEntity<String> deleteExpenseById(@PathVariable("id") 
	@Min(value = 1, message = "{expense.id.invalid}") Long expenseId) throws ExpenseException {
		expenseService.deleteExpenseById(expenseId);
		String message = environment.getProperty("API.EXPENSE_DELETE_SUCCESS");
		
		return new ResponseEntity<>(message, HttpStatus.OK);
	}
}
