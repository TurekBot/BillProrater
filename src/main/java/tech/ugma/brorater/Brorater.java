package tech.ugma.brorater;

import javafx.application.Application;
import javafx.application.HostServices;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.prefs.Preferences;

public class Brorater extends Application {
    public static HostServices hostServices;

    /**
     * Saves the last opened or saved file to open next time.
     */
    public static Preferences preferences = Preferences.userNodeForPackage(Brorater.class);


    @Override
    public void start(Stage primaryStage) throws Exception {

        hostServices = getHostServices();

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
        primaryStage.setTitle("Bill Prorater");

        // Add icons; JavaFX will decide which one is best, depending on where the icon is showing
        try {
            primaryStage.getIcons().addAll(
                    new Image(this.getClass().getResourceAsStream(
                            "/images/app-icons/icons8-rent-16.png")),
                    new Image(this.getClass().getResourceAsStream(
                            "/images/app-icons/icons8-rent-24.png")),
                    new Image(this.getClass().getResourceAsStream(
                            "/images/app-icons/icons8-rent-32.png")),
                    new Image(this.getClass().getResourceAsStream(
                            "/images/app-icons/icons8-rent-48.png")),
                    new Image(this.getClass().getResourceAsStream(
                            "/images/app-icons/icons8-rent-64.png"))
            );
        } catch (NullPointerException npe) {
            System.err.println("Could not locate application's icons!");
            npe.printStackTrace();
//            failGracefully("Could not find some important icons. Something's wrong.");
        }


        primaryStage.show();
    }
}
