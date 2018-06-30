package tech.ugma.brorater.model;

import java.time.LocalDate;

public class Range {

    private LocalDate startDate;
    private LocalDate endDate;

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }
}
