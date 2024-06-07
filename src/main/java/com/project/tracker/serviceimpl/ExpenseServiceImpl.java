package com.project.tracker.serviceimpl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import com.project.tracker.dto.ExpenseDTO;
import com.project.tracker.entity.CategoryEntity;
import com.project.tracker.entity.ExpenseEntity;
import com.project.tracker.exception.CategoryException;
import com.project.tracker.exception.ExpenseException;
import com.project.tracker.repository.CategoryRepository;
import com.project.tracker.repository.ExpenseRepository;
import com.project.tracker.service.ExpenseService;
import com.project.tracker.validator.ExpenseValidator;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service(value = "expenseService")
@Transactional
public class ExpenseServiceImpl implements ExpenseService {

	private final ExpenseRepository expenseRepository;
	
	private final CategoryRepository categoryRepository;
	
	@Override
	public ExpenseDTO createExpense(ExpenseDTO expenseDTO) throws ExpenseException {
		// TODO Auto-generated method stub
		ExpenseValidator.validate(expenseDTO);
		ExpenseEntity expenseEntity = ExpenseDTO.prepareExpenseEntity(expenseDTO);
		ExpenseEntity savedEntity = expenseRepository.save(expenseEntity);
		
		return ExpenseDTO.prepareExpenseDTO(savedEntity);
	}

	@Override
	public List<ExpenseDTO> createExpenses(List<ExpenseDTO> expenseDTOs) throws ExpenseException {
		// TODO Auto-generated method stub
		List<ExpenseEntity> expenseEntities = expenseDTOs
				.stream()
				.map(ExpenseDTO::prepareExpenseEntity)
				.collect(Collectors.toList());
		List<ExpenseEntity> savedEntities = expenseRepository.saveAll(expenseEntities);
		
		return savedEntities
				.stream()
				.map(ExpenseDTO::prepareExpenseDTO)
				.collect(Collectors.toList());
	}

	@Override
	public ExpenseDTO getExpenseById(Long expenseId) throws ExpenseException {
		// TODO Auto-generated method stub
		ExpenseEntity expenseEntity = expenseRepository
				.findById(expenseId)
				.orElseThrow(() -> new ExpenseException("service.EXPENSE_NOT_FOUND"));
		
		return ExpenseDTO.prepareExpenseDTO(expenseEntity);
	}

	@Override
	public List<ExpenseDTO> getAllExpenses() throws ExpenseException {
		// TODO Auto-generated method stub
		List<ExpenseEntity> expenseEntities = expenseRepository.findAll();
		if (expenseEntities.isEmpty()) {
			throw new ExpenseException("service.NO_EXPENSES_FOUND");
		}
		
		return expenseEntities
				.stream()
				.map(ExpenseDTO::prepareExpenseDTO)
				.collect(Collectors.toList());
	}

	@Override
	public ExpenseDTO updateExpenseById(Long expenseId, ExpenseDTO expenseDTO) throws ExpenseException {
		// TODO Auto-generated method stub
		ExpenseValidator.validate(expenseDTO);
		ExpenseEntity expenseEntity = expenseRepository
				.findById(expenseId)
				.orElseThrow(() -> new ExpenseException("service.EXPENSE_NOT_FOUND"));
		expenseEntity.setAmount(expenseDTO.getAmount());
		expenseEntity.setExpenseDate(expenseDTO.getExpenseDate());
		
		if (expenseDTO.getCategory() != null) {
			CategoryEntity categoryEntity = categoryRepository
					.findById(expenseDTO.getCategory().getCategoryId())
					.orElseThrow(() -> new CategoryException("service.CATEGORY_NOT_FOUND"));
			expenseEntity.setCategory(categoryEntity);
		}
		ExpenseEntity updatedEntity = expenseRepository.save(expenseEntity);
		
		return ExpenseDTO.prepareExpenseDTO(updatedEntity);
	}

	@Override
	public void deleteExpenseById(Long expenseId) throws ExpenseException {
		// TODO Auto-generated method stub
		ExpenseEntity expenseEntity = expenseRepository
				.findById(expenseId)
				.orElseThrow(() -> new ExpenseException("service.EXPENSE_NOT_FOUND"));
		expenseRepository.delete(expenseEntity);
	}

}
