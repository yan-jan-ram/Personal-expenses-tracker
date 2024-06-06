package com.project.tracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.tracker.entity.CategoryEntity;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {

}
