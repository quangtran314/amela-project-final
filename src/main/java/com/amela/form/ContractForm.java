package com.amela.form;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

public class ContractForm {

    @NotNull
    private LocalDate startDay;

    @NotNull
    private LocalDate endDay;

    @NotNull
    @Min(1)
    @Max(10)
    private int maxPerson;

    public ContractForm() {
    }

    public ContractForm(LocalDate startDay, LocalDate endDay, int maxPerson) {
        this.startDay = startDay;
        this.endDay = endDay;
        this.maxPerson = maxPerson;
    }

    public LocalDate getStartDay() {
        return startDay;
    }

    public void setStartDay(LocalDate startDay) {
        this.startDay = startDay;
    }

    public LocalDate getEndDay() {
        return endDay;
    }

    public void setEndDay(LocalDate endDay) {
        this.endDay = endDay;
    }

    public int getMaxPerson() {
        return maxPerson;
    }

    public void setMaxPerson(int maxPerson) {
        this.maxPerson = maxPerson;
    }
}
