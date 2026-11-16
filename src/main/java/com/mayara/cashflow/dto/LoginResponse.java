package com.mayara.cashflow.dto;

public record LoginResponse(
        String token,
        String tokenType
) {
}
