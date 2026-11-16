package com.mayara.cashflow.dto;

import com.mayara.cashflow.model.Category;

public record CategoryResponse(
        Long id,
        String name
) {
    public static CategoryResponse from(Category category) {
        return new CategoryResponse(category.getId(), category.getName());
    }
}
