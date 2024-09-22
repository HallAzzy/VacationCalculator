package com.vacationCalculator.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VacationResponse {
    private double vacationPay;

    public VacationResponse(double vacationPay) {
        this.vacationPay = vacationPay;
    }
}
