package tech.ugma.brorater;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Brorater extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/MainLayout.fxml"));
        Parent root = fxmlLoader.load();
        Controller controller = fxmlLoader.getController();
        controller.setPrimaryStage(primaryStage);
        Scene scene = new Scene(root);

        // Apply CSS
        final ObservableList<String> stylesheets = scene.getStylesheets();
        stylesheets.addAll(Brorater.class.getResource("/css/jfoenix-fonts.css").toExternalForm(),
                Brorater.class.getResource("/css/colors.css").toExternalForm(),
                Brorater.class.getResource("/css/brotater-styles.css").toExternalForm());

        primaryStage.setScene(scene);



        primaryStage.show();
    }
}
