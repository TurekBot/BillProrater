package tech.ugma.brorater.warehouse;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableView;
import tech.ugma.brorater.model.Bill;
import tech.ugma.brorater.model.Person;
import tech.ugma.brorater.model.StorageContainer;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;

public class Warehouse {

    /**
     * Loads data from the specified file. The current data will
     * be replaced.
     *
     */
    public static void loadDataFromFile(File file, TableView<Person> personTable, TableView<Bill> billTable) {
        try {
            JAXBContext context = JAXBContext
                    .newInstance(StorageContainer.class);
            Unmarshaller um = context.createUnmarshaller();

            // Reading XML from the file and unmarshalling.
            StorageContainer storageContainer = (StorageContainer) um.unmarshal(file);

            personTable.getItems().setAll(storageContainer.getPersons());

            billTable.getItems().setAll(storageContainer.getBills());

            // Save the file path to the registry.
            // TODO: 6/30/2018  setPersonFilePath(file);

        } catch (Exception e) { // catches ANY exception
            e.printStackTrace();
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Could not load data");
            alert.setContentText("Could not load data from file:\n" + file.getPath() + "\n" +
                    "Error: " + e.getLocalizedMessage());


            alert.showAndWait();
        }
    }

    /**
     * Saves the current person data to the specified file.
     *
     * @param file the file location where the data should be saved
     */
    public static void savePersonDataToFile(File file, TableView<Person> personTable, TableView<Bill> billTable) {
        try {
            JAXBContext context = JAXBContext
                    .newInstance(StorageContainer.class);
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            // Wrapping our data.
            StorageContainer storageContainer = new StorageContainer();
            storageContainer.setPersons(personTable.getItems());
            storageContainer.setBills(billTable.getItems());

            // Marshalling and saving XML to the file.
            m.marshal(storageContainer, file);

            // Save the file path to the registry.
            // TODO: 6/30/2018  setPersonFilePath(file);
        } catch (Exception e) { // catches ANY exception
            e.printStackTrace();
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Could not save data");
            alert.setContentText("Could not save data to file:\n" + file.getPath() + "\n" +
                    "Error: " + e.getLocalizedMessage());

            alert.showAndWait();
        }
    }
}
