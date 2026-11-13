package com.mayara.cashflow.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StatusController {

    @GetMapping("/")
    public String status() {
        return "Cashflow está no ar!";
    }

    @GetMapping("/about")
    public String about() {
        return "Cashflow - API de controle financeiro pessoal. Versão 0.1";
    }
}
