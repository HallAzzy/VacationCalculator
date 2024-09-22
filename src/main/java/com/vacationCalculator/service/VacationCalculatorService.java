package com.vacationCalculator.service;

import com.vacationCalculator.model.VacationRequest;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

@Service
public class VacationCalculatorService {

    private static final double AVERAGE_DAYS_IN_MONTH = 29.3;

    public double calculateVacationPay(VacationRequest request) {
        double avgSalary = request.getAvgSalary();

        if (avgSalary < 0) {
            throw new IllegalArgumentException("Average salary cannot be negative");
        }

        int daysAmount = request.getDaysAmount();
        List<LocalDate> vacationDates = request.getVacationDates();

        // Если даты отпуска указаны
        if (vacationDates != null && !vacationDates.isEmpty()) {
            daysAmount = calculateWorkingDays(vacationDates);
        }

        // Формула для расчета отпускных
        return (avgSalary / AVERAGE_DAYS_IN_MONTH) * daysAmount;
    }

    // Метод для расчета рабочих дней (без учета выходных и праздников)
    private int calculateWorkingDays(List<LocalDate> vacationDates) {
        int workingDays = 0;

        for (LocalDate date : vacationDates) {
            if (!isWeekend(date) && !isHoliday(date)) {
                workingDays++;
            }
        }

        return workingDays;
    }

    // Метод для проверки на выходной день (суббота или воскресенье)
    private boolean isWeekend(LocalDate date) {
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        return dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY;
    }

    // Метод для проверки на государственный праздник
    private boolean isHoliday(LocalDate date) {
        List<LocalDate> holidays = List.of(
                LocalDate.of(date.getYear(), 1, 1),  // Новый год
                LocalDate.of(date.getYear(), 1, 2),  // Новогодние выходные
                LocalDate.of(date.getYear(), 1, 3),  // Новогодние выходные
                LocalDate.of(date.getYear(), 1, 4),  // Новогодние выходные
                LocalDate.of(date.getYear(), 1, 5),  // Новогодние выходные
                LocalDate.of(date.getYear(), 1, 6),  // Новогодние выходные
                LocalDate.of(date.getYear(), 1, 7),  // Рождетсво
                LocalDate.of(date.getYear(), 1, 8),  // Новогодние выходные
                LocalDate.of(date.getYear(), 2, 23), // День защитника Отечества
                LocalDate.of(date.getYear(), 3, 8),  // Международный женский день
                LocalDate.of(date.getYear(), 5, 1),  // Праздник Весны и Труда
                LocalDate.of(date.getYear(), 5, 9),  // День Победы
                LocalDate.of(date.getYear(), 6, 12), // День России
                LocalDate.of(date.getYear(), 11, 4)  // День народного единства
        );

        return holidays.contains(date);
    }
}