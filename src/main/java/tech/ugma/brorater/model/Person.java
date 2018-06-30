package tech.ugma.brorater.model;

import javafx.beans.property.*;

import java.time.LocalDate;

public class Person {
    private StringProperty name = new SimpleStringProperty();
    private ObjectProperty<LocalDate> startDate = new SimpleObjectProperty<>();
    private ObjectProperty<LocalDate> endDate = new SimpleObjectProperty<>();
    private DoubleProperty balanceDue = new SimpleDoubleProperty();

    ///////////////////////////////////////////////////////////////////////////
    // Getters and Setters
    ///////////////////////////////////////////////////////////////////////////


    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public LocalDate getStartDate() {
        return startDate.get();
    }

    public ObjectProperty<LocalDate> startDateProperty() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate.set(startDate);
    }

    public LocalDate getEndDate() {
        return endDate.get();
    }

    public ObjectProperty<LocalDate> endDateProperty() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate.set(endDate);
    }

    public double getBalanceDue() {
        return balanceDue.get();
    }

    public DoubleProperty balanceDueProperty() {
        return balanceDue;
    }

    public void setBalanceDue(double balanceDue) {
        this.balanceDue.set(balanceDue);
    }
}
