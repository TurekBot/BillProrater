package tech.ugma.brorater;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import tech.ugma.brorater.model.Person;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML // fx:id="newPersonButton"
    private Button newPersonButton; // Value injected by FXMLLoader

    @FXML // fx:id="personTable"
    private TableView<?> personTable; // Value injected by FXMLLoader

    @FXML // fx:id="newBillButton"
    private Button newBillButton; // Value injected by FXMLLoader

    @FXML // fx:id="billTable"
    private TableView<?> billTable; // Value injected by FXMLLoader


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

    }

    private void setUpNewBillButton() {

    }

    private void setUpNewPersonButton() {
        Dialog<Person> newPersonDialog = setUpNewPersonDialog();

        newPersonButton.setOnAction(event -> {
            Optional<Person> result = newPersonDialog.showAndWait();

            result.ifPresent(person -> {
                // Add person to person list

                // Persist person to storage

            });
        });

    }

    private Dialog<Person> setUpNewPersonDialog() {
        Dialog<Person> dialog = new Dialog<>();

        TextField nameTextField = new TextField();
        nameTextField.setPromptText("Name");

        DatePicker moveInDatePicker = new DatePicker();
        moveInDatePicker.setPromptText("Move-In Date");

        DatePicker moveOutDatePicker = new DatePicker();
        moveOutDatePicker.setPromptText("Move-Out Date");

        VBox vBox = new VBox(nameTextField, moveInDatePicker, moveOutDatePicker);
        vBox.setSpacing(10);


        dialog.getDialogPane().setContent(vBox);

        dialog.setResultConverter(buttonType -> {

            if (ButtonType.OK.equals(buttonType)) {
                // Make and return a person
                Person person = new Person();
                person.setName(nameTextField.getText());
                person.setStartDate(moveInDatePicker.getValue());
                person.setEndDate(moveInDatePicker.getValue());
                return person;
            }

            return null;
        });

        dialog.getDialogPane().getButtonTypes().addAll(
                ButtonType.OK,
                ButtonType.CANCEL
        );
        // Return the dialog
        return dialog;
    }
}
