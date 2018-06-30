package tech.ugma.brorater;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.TableView;
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

        newPersonButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                newPersonDialog.showAndWait();
                Optional<Person> result = newPersonDialog.showAndWait();

                result.ifPresent(person -> {
                    // Add person to person list

                    // Persist person to storage

                });
            }
        });

    }

    private Dialog<Person> setUpNewPersonDialog() {

        // Return the dialog
        return null;
    }
}
