package com.vacationCalculator.demo;

import com.vacationCalculator.model.VacationRequest;
import com.vacationCalculator.service.VacationCalculatorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;

class VacationCalculatorServiceTest {

    private VacationCalculatorService vacationCalculatorService;

    @BeforeEach
    void setUp() {
        vacationCalculatorService = new VacationCalculatorService();
    }

    // Тест для расчета отпускных без учета дат
    @Test
    void testCalculateVacationPayWithoutDates() {
        VacationRequest request = new VacationRequest();
        request.setAvgSalary(60000);
        request.setDaysAmount(10);

        double vacationPay = vacationCalculatorService.calculateVacationPay(request);
        assertEquals(20477.81, vacationPay, 0.1);
    }

    // Тест для расчета отпускных с указанием дат
    @Test
    void testCalculateVacationPayWithDates() {
        VacationRequest request = new VacationRequest();
        request.setAvgSalary(60000);
        request.setVacationDates(List.of(
                LocalDate.of(2024, 1, 2), // Новогодние выходные
                LocalDate.of(2024, 1, 3), // Новогодние выходные
                LocalDate.of(2024, 1, 6), // Выходной день (суббота)
                LocalDate.of(2024, 1, 7), // Выходной день (воскресенье)
                LocalDate.of(2024, 1, 10) // Рабочий день
        ));

        double vacationPay = vacationCalculatorService.calculateVacationPay(request);
        assertEquals(2047.8, vacationPay, 0.1);
    }

    // Тест для зарплаты, равной нулю
    @Test
    void testCalculateVacationPayWithZeroSalary() {
        VacationRequest request = new VacationRequest();
        request.setAvgSalary(0);
        request.setDaysAmount(10);

        double vacationPay = vacationCalculatorService.calculateVacationPay(request);
        assertEquals(0, vacationPay);
    }

    // Тест для отрицательной зарплаты
    @Test
    void testCalculateVacationPayWithNegativeSalary() {
        VacationRequest request = new VacationRequest();
        request.setAvgSalary(-60000);
        request.setDaysAmount(10);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            vacationCalculatorService.calculateVacationPay(request);
        });

        String expectedMessage = "Average salary cannot be negative";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }
}