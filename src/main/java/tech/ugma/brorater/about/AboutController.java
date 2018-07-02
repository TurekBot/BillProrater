package tech.ugma.brorater.about;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import tech.ugma.brorater.Brorater;

public class AboutController {


    @FXML
    void linkToFontAwesome(ActionEvent event) {
        Brorater.hostServices.showDocument("https://fontawesome.com/");
    }

    @FXML
    void linkToFontAwesomeFX(ActionEvent event) {
        Brorater.hostServices.showDocument("https://github.com/Jerady/fontawesomefx-examples");
    }

    @FXML
    void linkToIcons8(ActionEvent event) {
        Brorater.hostServices.showDocument("https://icons8.com/");

    }

    @FXML
    void linkToJFoenix(ActionEvent event) {
        Brorater.hostServices.showDocument("http://www.jfoenix.com/");

    }

    @FXML
    void linkToMaterialDesign(ActionEvent event) {
        Brorater.hostServices.showDocument("https://material.io/");

    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {

    }
}
