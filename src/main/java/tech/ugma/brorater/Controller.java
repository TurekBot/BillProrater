package tech.ugma.brorater;

import com.jfoenix.controls.*;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import tech.ugma.brorater.model.Bill;
import tech.ugma.brorater.model.Person;
import tech.ugma.brorater.model.Range;
import tech.ugma.brorater.warehouse.Warehouse;

import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class Controller implements Initializable {


    @FXML // fx:id="wholeApp"
    private StackPane wholeApp; // Value injected by FXMLLoader

    @FXML
    private MenuItem saveMenuItem;

    public JFXButton newCalculateButton;
    @FXML
    private MenuItem exportMenuButton;

    @FXML
    private MenuItem openMenuItem;

    @FXML // fx:id="addPersonButton"
    private JFXButton addPersonButton; // Value injected by FXMLLoader

    @FXML // fx:id="removePersonButton"
    private JFXButton removePersonButton; // Value injected by FXMLLoader

    @FXML // fx:id="personTable"
    private TableView<Person> personTable; // Value injected by FXMLLoader

    @FXML // fx:id="personNameColumn"
    private TableColumn<Person, String> personNameColumn; // Value injected by FXMLLoader

    @FXML // fx:id="moveInDateColumn"
    private TableColumn<Person, LocalDate> moveInDateColumn; // Value injected by FXMLLoader

    @FXML // fx:id="moveoutDateColumn"
    private TableColumn<Person, LocalDate> moveoutDateColumn; // Value injected by FXMLLoader

    @FXML // fx:id="balanceDueColumn"
    private TableColumn<Person, BigDecimal> balanceDueColumn; // Value injected by FXMLLoader

    @FXML // fx:id="addBillButton"
    private JFXButton addBillButton; // Value injected by FXMLLoader

    @FXML // fx:id="removeBillButton"
    private JFXButton removeBillButton; // Value injected by FXMLLoader

    @FXML // fx:id="billTable"
    private TableView<Bill> billTable; // Value injected by FXMLLoader

    @FXML // fx:id="billNameColumn"
    private TableColumn<Bill, String> billNameColumn; // Value injected by FXMLLoader

    @FXML // fx:id="startDateColumn"
    private TableColumn<Bill, LocalDate> startDateColumn; // Value injected by FXMLLoader

    @FXML // fx:id="endDateColumn"
    private TableColumn<Bill, LocalDate> endDateColumn; // Value injected by FXMLLoader

    @FXML // fx:id="totalColumn"
    private TableColumn<Bill, BigDecimal> totalColumn; // Value injected by FXMLLoader

    private FileChooser fileChooser = new FileChooser();

    private final NumberFormat moneyFormat = new DecimalFormat("$0.00");

    /**
     * This is the main window.
     */
    private Stage primaryStage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("I'm here!");

        setUpAddPersonButton();
        setUpRemovePersonButton();
        setUpPersonTable();

        setUpAddBillButton();
        setUpRemoveBillButton();
        setUpBillTable();

        setUpMenu();
        setUpCalculateButton();
    }

    private void setUpRemoveBillButton() {
        removeBillButton.setOnAction(event -> {
            Bill selectedItem = billTable.getSelectionModel().getSelectedItem();
            billTable.getItems().remove(selectedItem);
        });
    }

    private void setUpRemovePersonButton() {
        removePersonButton.setOnAction(event -> {
            Person selectedItem = personTable.getSelectionModel().getSelectedItem();
            personTable.getItems().remove(selectedItem);
        });
    }


    private Dialog<Range> setUpCalculateDialog() {
        Dialog<Range> dialog = new Dialog<>();


        Label label = new Label("Please specify a date range for which to calculate the Broration (Bill Proration).\n" +
                "This is probably the duration of a whole month—at least that's what normal landlords do.");


        JFXDatePicker startDatePicker = new JFXDatePicker();
        startDatePicker.setPromptText("Start Date");

        JFXDatePicker endDatePicker = new JFXDatePicker();
        endDatePicker.setPromptText("End Date");

        VBox vBox = new VBox(label, startDatePicker, endDatePicker);
        vBox.setSpacing(10);


        dialog.getDialogPane().setContent(vBox);

        dialog.setResultConverter(buttonType -> {

            if (ButtonType.OK.equals(buttonType)) {
                // Make and return a range
                Range range = new Range();
                range.setStartDate(startDatePicker.getValue());
                range.setEndDate(endDatePicker.getValue());
                return range;
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

    private void setUpCalculateButton() {
        Dialog<Range> newCalculateDialog = setUpCalculateDialog();

        newCalculateButton.setOnAction(event -> {
            Optional<Range> result = newCalculateDialog.showAndWait();

            result.ifPresent(range ->
                    calculateBroration(range, personTable.getItems(), billTable.getItems()));
        });
        /*
        personTable.getItems().size();
        while(personTable.getItems().size() != 0){

        }*/
    }

    private void calculateBroration(Range range, ObservableList<Person> people, ObservableList<Bill> bills) {
        // Reset every person's balance due because we're doing a brand new calculation
        people.forEach(person -> person.setBalanceDue(BigDecimal.ZERO));


        // Start at the beginning of the range and go all the way to the end of the range
        LocalDate current = LocalDate.parse(range.getStartDate().toString());
        List<Person> inHouse = new ArrayList<>();
        while (current.isBefore(range.getEndDate().plusDays(1))) {
            System.out.println("Current day: " + current.toString());
            inHouse.clear();
            BigDecimal costOfUtilitiesPerPersonForToday = BigDecimal.valueOf(0.00);

            // On each day,
            // Check which people are still in the house
            for (Person person : people) {
                if ((current.isEqual(person.getMoveInDate()) || current.isAfter(person.getMoveInDate())) &&
                        (current.isEqual(person.getMoveOutDate()) || current.isBefore(person.getMoveOutDate()))) {
                    // Put them in a list
                    inHouse.add(person);
                }
            }
            System.out.println(inHouse.size() + " people in house today.");


            // Check which bills fall within the day
            for (Bill bill : bills) {
                if ((bill.getStartDate().isEqual(current) || bill.getStartDate().isBefore(current))
                        && (bill.getEndDate().isEqual(current) || bill.getEndDate().isAfter(current))) {
                    System.out.println(bill.getName() + " cost per day: " + bill.getCostPerDay());
                    // Get the bills cost per day and divide it by the number of people
                    BigDecimal costPerPersonPerDay =
                            bill.getCostPerDay().divide(BigDecimal.valueOf(inHouse.size()), 10, RoundingMode.HALF_UP);
                    System.out.println("Divided among " + inHouse.size() + " people: " + costPerPersonPerDay);

                    costOfUtilitiesPerPersonForToday = costOfUtilitiesPerPersonForToday.add(costPerPersonPerDay);
                }
            }


            // Add that number to each of the person's amount due
            BigDecimal finalCostOfUtilitiesPerPersonForToday = costOfUtilitiesPerPersonForToday;
            inHouse.forEach(person ->
                    person.setBalanceDue(
                            person.getBalanceDue().add(finalCostOfUtilitiesPerPersonForToday)));


            // Go to the next day
            current = current.plusDays(1);
            System.out.println();
        }

        // At the end, round each balance due to 2 decimal places
        for (Person person : people) {
            person.setBalanceDue(person.getBalanceDue().setScale(2, RoundingMode.HALF_UP));
        }
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
            fileChooser.getExtensionFilters().clear();
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PDF ", "*.pdf"));
            fileChooser.setTitle("Export Location");
            File saveFile = fileChooser.showSaveDialog(primaryStage);
            FirstPdf.generate(saveFile);

        });


        openMenuItem.setOnAction(event -> {
            fileChooser.setTitle("Choose an XML file containing Person and Bill data");

            // This only allows the user to select "*.xml" files
            FileChooser.ExtensionFilter filter =
                    new FileChooser.ExtensionFilter("XML files", "*.xml");

            fileChooser.getExtensionFilters().clear();
            fileChooser.getExtensionFilters().add(filter);

            // Passing in the primaryStage makes it so that any input to the rest
            // of the windows is blocked: the user can only interact with the FileChooser
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
                cellData.getValue().totalProperty()
        );

        /*Cell Factories*/
        // The cell factories tell the table how each cell in a given column
        // should look

        totalColumn.setCellFactory(tc -> new TableCell<Bill, BigDecimal>() {
            @Override
            protected void updateItem(BigDecimal value, boolean empty) {
                super.updateItem(value, empty);
                if (value == null || empty) {
                    setText("");
                } else {
                    setText(moneyFormat.format(value));
                }
            }
        });

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
                cellData.getValue().balanceDueProperty()
        );

        /*Cell Factories*/
        // The cell factories tell the table how each cell in a given column
        // should look
        balanceDueColumn.setCellFactory(tc -> new TableCell<Person, BigDecimal>() {
            @Override
            protected void updateItem(BigDecimal value, boolean empty) {
                super.updateItem(value, empty);
                if (value == null || empty) {
                    setText("");
                } else {
                    setText(moneyFormat.format(value));
                }
            }
        });


    }

    private void setUpAddBillButton() {
        JFXDialog newBillDialog = setUpNewBillDialog();

        addBillButton.setOnAction(event -> newBillDialog.show());

    }

    private void setUpAddPersonButton() {
        JFXDialog newPersonDialog = setUpNewPersonDialog();

        addPersonButton.setOnAction(event -> newPersonDialog.show());
    }

    private JFXDialog setUpNewPersonDialog() {
        JFXDialog dialog = new JFXDialog();

        dialog.setDialogContainer(wholeApp);

        JFXDialogLayout layout = new JFXDialogLayout();

        layout.setHeading(new Label("New Person"));

        JFXTextField nameTextField = new JFXTextField();
        nameTextField.setPromptText("Name");

        JFXDatePicker moveInDatePicker = new JFXDatePicker();
        moveInDatePicker.setPromptText("Move-In Date");

        JFXDatePicker moveOutDatePicker = new JFXDatePicker();
        moveOutDatePicker.setPromptText("Move-Out Date");

        VBox vBox = new VBox(nameTextField, moveInDatePicker, moveOutDatePicker);
        vBox.setSpacing(20);

        layout.setBody(vBox);

        JFXButton okButton = new JFXButton("OK");
        okButton.setOnAction(event -> {
            // Make and return a person
            Person person = new Person();
            person.setName(nameTextField.getText());
            person.setMoveInDate(moveInDatePicker.getValue());
            person.setMoveOutDate(moveOutDatePicker.getValue());
            personTable.getItems().add(person);

            dialog.close();
        });
        okButton.getStyleClass().add("flat-button");
        layout.getActions().add(okButton);

        JFXButton cancelButton = new JFXButton("CANCEL");
        cancelButton.setOnAction(event -> dialog.close());
        cancelButton.getStyleClass().add("flat-button");
        layout.getActions().add(cancelButton);


        dialog.setContent(layout);

        // Return the dialog
        return dialog;
    }

    private JFXDialog setUpNewBillDialog() {

        JFXDialog dialog = new JFXDialog();

        dialog.setDialogContainer(wholeApp);

        JFXDialogLayout layout = new JFXDialogLayout();

        layout.setHeading(new Label("New Bill"));

        JFXTextField nameTextField = new JFXTextField();
        nameTextField.setPromptText("Bill Name");

        JFXDatePicker startDatePicker = new JFXDatePicker();
        startDatePicker.setPromptText("Start Date");

        JFXDatePicker endDatePicker = new JFXDatePicker();
        endDatePicker.setPromptText("End Date");

        JFXTextField totalTextField = new JFXTextField();
        totalTextField.setPromptText("Total");

        VBox vBox = new VBox(nameTextField, startDatePicker, endDatePicker, totalTextField);
        vBox.setSpacing(20);

        layout.setBody(vBox);

        JFXButton okButton = new JFXButton("OK");
        okButton.setOnAction(event -> {
            Bill bill = new Bill();
            bill.setName(nameTextField.getText().trim());
            bill.setStartDate(startDatePicker.getValue());
            bill.setEndDate(endDatePicker.getValue());
            bill.setTotal(Double.parseDouble(totalTextField.getText().trim()));
            billTable.getItems().add(bill);

            dialog.close();
        });
        okButton.getStyleClass().add("flat-button");
        layout.getActions().add(okButton);

        JFXButton cancelButton = new JFXButton("CANCEL");
        cancelButton.setOnAction(event -> dialog.close());
        cancelButton.getStyleClass().add("flat-button");
        layout.getActions().add(cancelButton);

        dialog.setContent(layout);

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
