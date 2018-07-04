package tech.ugma.ubs.about;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import tech.ugma.ubs.UtilityBillSplitter;

public class AboutController {


    @FXML
    void linkToFontAwesome(ActionEvent event) {
        UtilityBillSplitter.hostServices.showDocument("https://fontawesome.com/");
    }

    @FXML
    void linkToFontAwesomeFX(ActionEvent event) {
        UtilityBillSplitter.hostServices.showDocument("https://github.com/Jerady/fontawesomefx-examples");
    }

    @FXML
    void linkToIcons8(ActionEvent event) {
        UtilityBillSplitter.hostServices.showDocument("https://icons8.com/");

    }

    @FXML
    void linkToJFoenix(ActionEvent event) {
        UtilityBillSplitter.hostServices.showDocument("http://www.jfoenix.com/");

    }

    @FXML
    void linkToMaterialDesign(ActionEvent event) {
        UtilityBillSplitter.hostServices.showDocument("https://material.io/");

    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {

    }
}
