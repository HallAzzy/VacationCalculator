package com.vacationCalculator.model;

import java.time.LocalDate;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VacationRequest {
    private double avgSalary;
    private int daysAmount;
    private List<LocalDate> vacationDates;
}
