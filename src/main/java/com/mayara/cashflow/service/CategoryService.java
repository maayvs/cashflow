package com.mayara.cashflow.service;

import com.mayara.cashflow.dto.CategoryRequest;
import com.mayara.cashflow.dto.CategoryResponse;
import com.mayara.cashflow.model.Category;
import com.mayara.cashflow.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<CategoryResponse> findAll() {
        List<CategoryResponse> responses = new ArrayList<>();
        for (Category category : categoryRepository.findAll()) {
            responses.add(CategoryResponse.from(category));
        }
        return responses;
    }

    public CategoryResponse create(CategoryRequest request) {
        if (categoryRepository.existsByNameIgnoreCase(request.name())) {
            throw new IllegalArgumentException("A categoria com este nome já existe.");
        }
        Category category = new Category(request.name());
        return CategoryResponse.from(categoryRepository.save(category));
    }
}
