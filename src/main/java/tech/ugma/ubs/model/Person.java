package tech.ugma.ubs.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Person {
    private StringProperty name = new SimpleStringProperty();
    private ObjectProperty<LocalDate> moveInDate = new SimpleObjectProperty<>();
    private ObjectProperty<LocalDate> moveOutDate = new SimpleObjectProperty<>();
    private ObjectProperty<BigDecimal> balanceDue = new SimpleObjectProperty<>();


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

    public LocalDate getMoveInDate() {
        return moveInDate.get();
    }

    public ObjectProperty<LocalDate> moveInDateProperty() {
        return moveInDate;
    }

    public void setMoveInDate(LocalDate moveInDate) {
        this.moveInDate.set(moveInDate);
    }

    public LocalDate getMoveOutDate() {
        return moveOutDate.get();
    }

    public ObjectProperty<LocalDate> moveOutDateProperty() {
        return moveOutDate;
    }

    public void setMoveOutDate(LocalDate moveOutDate) {
        this.moveOutDate.set(moveOutDate);
    }

    public BigDecimal getBalanceDue() {
        return balanceDue.get();
    }

    public ObjectProperty<BigDecimal> balanceDueProperty() {
        return balanceDue;
    }

    public void setBalanceDue(BigDecimal balanceDue) {
        this.balanceDue.set(balanceDue);
    }

}
