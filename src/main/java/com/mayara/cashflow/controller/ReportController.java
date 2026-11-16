package com.mayara.cashflow.controller;

import com.mayara.cashflow.dto.MonthlySummaryResponse;
import com.mayara.cashflow.service.ReportService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reports")
public class ReportController {
    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/monthly")
    public MonthlySummaryResponse monthly(@RequestParam int year, @RequestParam int month) {
        return reportService.getMonthlySummary(year, month);
    }
}