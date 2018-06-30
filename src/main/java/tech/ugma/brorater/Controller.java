package tech.ugma.brorater;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import tech.ugma.brorater.model.Bill;
import tech.ugma.brorater.model.Person;

import java.net.URL;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML // fx:id="newPersonButton"
    private Button newPersonButton; // Value injected by FXMLLoader

    @FXML // fx:id="personTable"
    private TableView<Person> personTable; // Value injected by FXMLLoader

    @FXML // fx:id="personNameColumn"
    private TableColumn<Person, String> personNameColumn; // Value injected by FXMLLoader

    @FXML // fx:id="moveInDateColumn"
    private TableColumn<Person, LocalDate> moveInDateColumn; // Value injected by FXMLLoader

    @FXML // fx:id="moveoutDateColumn"
    private TableColumn<Person, LocalDate> moveoutDateColumn; // Value injected by FXMLLoader

    @FXML // fx:id="balanceDueColumn"
    private TableColumn<Person, Double> balanceDueColumn; // Value injected by FXMLLoader

    @FXML // fx:id="newBillButton"
    private Button newBillButton; // Value injected by FXMLLoader

    @FXML // fx:id="billTable"
    private TableView<Bill> billTable; // Value injected by FXMLLoader

    @FXML // fx:id="billNameColumn"
    private TableColumn<Bill, String> billNameColumn; // Value injected by FXMLLoader

    @FXML // fx:id="startDateColumn"
    private TableColumn<Bill, LocalDate> startDateColumn; // Value injected by FXMLLoader

    @FXML // fx:id="endDateColumn"
    private TableColumn<Bill, LocalDate> endDateColumn; // Value injected by FXMLLoader

    @FXML // fx:id="totalColumn"
    private TableColumn<Bill, Double> totalColumn; // Value injected by FXMLLoader

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("I'm here!");

        setUpNewPersonButton();
        setUpPersonTable();

        setUpNewBillButton();
        setUpBillTable();
    }

    private void setUpBillTable() {

    }

    private void setUpPersonTable() {

        /*Cell Value Factories*/
        // The cell value factories tell the table which part of the person
        // to put in that column
        personNameColumn.setCellValueFactory(cellData ->
                cellData.getValue().nameProperty()
        );
        moveInDateColumn.setCellValueFactory(cellData ->
                cellData.getValue().moveInDateProperty()
        );
        moveoutDateColumn.setCellValueFactory(cellData ->
                cellData.getValue().moveOutDateProperty()
        );
        balanceDueColumn.setCellValueFactory(cellData ->
                cellData.getValue().balanceDueProperty().asObject()
        );

        /*Cell Factories*/
        // The cell factories tell the table how each cell in a given column
        // should look


    }

    private void setUpNewBillButton() {

    }

    private void setUpNewPersonButton() {
        Dialog<Person> newPersonDialog = setUpNewPersonDialog();

        newPersonButton.setOnAction(event -> {
            newPersonDialog.showAndWait();
            Optional<Person> result = newPersonDialog.showAndWait();

            result.ifPresent(person -> {
                // Add person to person list

                // Persist person to storage

            });
        });

    }

    private Dialog<Person> setUpNewPersonDialog() {

        // Return the dialog
        return null;
    }
}
