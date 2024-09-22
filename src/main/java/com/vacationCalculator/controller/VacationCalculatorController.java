package com.vacationCalculator.controller;

import com.vacationCalculator.model.VacationRequest;
import com.vacationCalculator.model.VacationResponse;
import com.vacationCalculator.service.VacationCalculatorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/calculate")
public class VacationCalculatorController {

    private final VacationCalculatorService vacationCalculatorService;

    public VacationCalculatorController(VacationCalculatorService vacationCalculatorService) {
        this.vacationCalculatorService = vacationCalculatorService;
    }

    @GetMapping
    public ResponseEntity<VacationResponse> calculateVacationPay(
            @RequestParam double avgSalary,
            @RequestParam int daysAmount,
            @RequestParam(required = false) List<LocalDate> vacationDates) {

        if (avgSalary < 0) {
            throw new IllegalArgumentException("Average salary cannot be negative");
        }

        VacationRequest request = new VacationRequest();
        request.setAvgSalary(avgSalary);
        request.setDaysAmount(daysAmount);
        request.setVacationDates(vacationDates);

        double vacationPay = vacationCalculatorService.calculateVacationPay(request);
        return ResponseEntity.ok(new VacationResponse(vacationPay));
    }

    @ControllerAdvice
    public static class GlobalExceptionHandler {

        @ExceptionHandler(IllegalArgumentException.class)
        @ResponseStatus(HttpStatus.BAD_REQUEST)
        public ResponseEntity<String> handleIllegalArgument(IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
}