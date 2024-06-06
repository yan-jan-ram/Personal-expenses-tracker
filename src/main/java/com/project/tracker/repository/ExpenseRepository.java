package com.project.tracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.tracker.entity.ExpenseEntity;

public interface ExpenseRepository extends JpaRepository<ExpenseEntity, Long> {

}
