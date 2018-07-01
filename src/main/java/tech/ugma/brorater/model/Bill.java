package tech.ugma.brorater.model;

import javafx.beans.binding.ObjectBinding;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

import static java.time.temporal.ChronoUnit.DAYS;

public class Bill {
    private StringProperty name = new SimpleStringProperty();
    private ObjectProperty<LocalDate> startDate = new SimpleObjectProperty<>();
    private ObjectProperty<LocalDate> endDate = new SimpleObjectProperty<>();
    private ObjectProperty<BigDecimal> total = new SimpleObjectProperty<>();
    private ObjectBinding<BigDecimal> costPerDay = new ObjectBinding<BigDecimal>() {
        @Override
        protected BigDecimal computeValue() {
            long days = DAYS.between(startDate.getValue(), endDate.getValue());
            return total.getValue().divide(BigDecimal.valueOf(days), 2, RoundingMode.HALF_UP);
        }

        @Override
        public ObservableList<?> getDependencies() {
            return FXCollections.observableArrayList(startDate, endDate, total);
        }
    };

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

    public BigDecimal getTotal() {
        return total.get();
    }

    public ObjectProperty<BigDecimal> totalProperty() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total.set(total);
    }
    public void setTotal(double total) {
        this.total.set(BigDecimal.valueOf(total));
    }

    public BigDecimal getCostPerDay() {
        return costPerDay.get();
    }

    public ObjectBinding<BigDecimal> costPerDayProperty() {
        return costPerDay;
    }
}
