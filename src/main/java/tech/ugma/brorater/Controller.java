package tech.ugma.brorater;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import tech.ugma.brorater.model.Bill;
import tech.ugma.brorater.model.Person;
import tech.ugma.brorater.warehouse.Warehouse;

import java.io.File;
import java.net.URL;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    private MenuItem saveMenuItem;

    @FXML
    private MenuItem exportMenuButton;

    @FXML
    private MenuItem openMenuItem;

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

    private FileChooser fileChooser = new FileChooser();

    /**
     * This is the main window.
     */
    private Stage primaryStage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("I'm here!");

        setUpNewPersonButton();
        setUpPersonTable();

        setUpNewBillButton();
        setUpBillTable();

        setUpMenu();
    }

    private void setUpMenu() {

        saveMenuItem.setOnAction(event -> {
            fileChooser.setTitle("Save Brorate project");

            // This only allows the user to select "*.xml" files
            FileChooser.ExtensionFilter filter =
                    new FileChooser.ExtensionFilter("XML files", "*.xml");

            fileChooser.getExtensionFilters().clear();
            fileChooser.getExtensionFilters().add(filter);

            // Passing in the primaryStage makes it so that any input to the rest
            // of the windows is blocked: the user can only interact with the FileChoose
            File saveFile = fileChooser.showSaveDialog(primaryStage);

            Warehouse.savePersonDataToFile(saveFile, personTable, billTable);
        });

        exportMenuButton.setOnAction(event -> {
            fileChooser.setTitle("Export Location");
            fileChooser.showSaveDialog(primaryStage);
        });


        openMenuItem.setOnAction(event -> {
            fileChooser.setTitle("Choose an XML file containing Person and Bill data");

            // This only allows the user to select "*.xml" files
            FileChooser.ExtensionFilter filter =
                    new FileChooser.ExtensionFilter("XML files", "*.xml");

            fileChooser.getExtensionFilters().clear();
            fileChooser.getExtensionFilters().add(filter);

            // Passing in the primaryStage makes it so that any input to the rest
            // of the windows is blocked: the user can only interact with the FileChoose
            File file = fileChooser.showOpenDialog(primaryStage);

            Warehouse.loadDataFromFile(file, personTable, billTable);

        });

        openMenuItem.setOnAction(event -> {
            fileChooser.setTitle("Choose an XML file containing Person and Bill data");

            // This only allows the user to select "*.xml" files
            FileChooser.ExtensionFilter filter =
                    new FileChooser.ExtensionFilter("XML files", "*.xml");

            fileChooser.getExtensionFilters().clear();
            fileChooser.getExtensionFilters().add(filter);

            // Passing in the primaryStage makes it so that any input to the rest
            // of the windows is blocked: the user can only interact with the FileChoose
            File file = fileChooser.showOpenDialog(primaryStage);

            Warehouse.loadDataFromFile(file, personTable, billTable);

        });
    }

    private void setUpBillTable() {

        /*Cell Value Factories*/
        // The cell value factories tell the table which part of the person
        // to put in that column
        billNameColumn.setCellValueFactory(cellData ->
                cellData.getValue().nameProperty()
        );
        startDateColumn.setCellValueFactory(cellData ->
                cellData.getValue().startDateProperty()
        );
        endDateColumn.setCellValueFactory(cellData ->
                cellData.getValue().endDateProperty()
        );
        totalColumn.setCellValueFactory(cellData ->
                cellData.getValue().totalProperty().asObject()
        );

        /*Cell Factories*/
        // The cell factories tell the table how each cell in a given column
        // should look

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
        Dialog<Bill> newBillDialog = setUpNewBillDialog();

        newBillButton.setOnAction(event -> {
            Optional<Bill> result = newBillDialog.showAndWait();

            result.ifPresent(bill -> {
                // Add bill to bill list
                billTable.getItems().add(bill);
                // Persist bill to storage

            });
        });

    }

    private void setUpNewPersonButton() {
        Dialog<Person> newPersonDialog = setUpNewPersonDialog();

        newPersonButton.setOnAction(event -> {
            Optional<Person> result = newPersonDialog.showAndWait();

            result.ifPresent(person -> {
                // Add person to person list
                personTable.getItems().add(person);
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
                person.setMoveInDate(moveInDatePicker.getValue());
                person.setMoveOutDate(moveOutDatePicker.getValue());
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

    private Dialog<Bill> setUpNewBillDialog() {

        Dialog<Bill> dialog = new Dialog<>();
        TextField nameTF = new TextField();
        nameTF.setPromptText("Bill Name");

        DatePicker startDatePicker = new DatePicker();
        startDatePicker.setPromptText("Start Date");

        DatePicker endDatePicker = new DatePicker();
        endDatePicker.setPromptText("End Date");

        TextField totalTF = new TextField();
        totalTF.setPromptText("Total");

        VBox vBox = new VBox(nameTF, startDatePicker, endDatePicker, totalTF);
        vBox.setSpacing(10);

        dialog.getDialogPane().setContent(vBox);

        dialog.getDialogPane().getButtonTypes().addAll(
                ButtonType.OK,
                ButtonType.CANCEL
        );

        dialog.setResultConverter(param -> {
            if (ButtonType.OK.equals(param)) {
                Bill bill = new Bill();
                bill.setName(nameTF.getText().trim());
                bill.setStartDate(startDatePicker.getValue());
                bill.setEndDate(endDatePicker.getValue());
                bill.setTotal(Double.parseDouble(totalTF.getText().trim()));
                return bill;
            } else
                return null;
        });


        // Return the dialog
        return dialog;
    }

    void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    Stage getPrimaryStage() {
        return primaryStage;
    }
}
