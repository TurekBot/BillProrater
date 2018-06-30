package tech.ugma.brorater.model;

import javafx.beans.property.*;

import java.time.LocalDate;

public class Person {
    private StringProperty name = new SimpleStringProperty();
    private ObjectProperty<LocalDate> moveInDate = new SimpleObjectProperty<>();
    private ObjectProperty<LocalDate> moveOutDate = new SimpleObjectProperty<>();
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
