package tech.ugma.ubs.table;

import com.jfoenix.controls.JFXDatePicker;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.Callback;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

/**
 * Based on the idea of James_D: https://stackoverflow.com/a/34701925/5432315
 */
public class EditableDateCell<T> extends TableCell<T, LocalDate> {

    private JFXDatePicker datePicker;

    private DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT);

    public EditableDateCell() {

        datePicker = new JFXDatePicker();

        datePicker.addEventFilter(KeyEvent.KEY_RELEASED, e -> {
            if (e.getCode() == KeyCode.ESCAPE) {
                cancelEdit();
            }
        });

        datePicker.setOnAction(e -> commitEdit(datePicker.getValue()));

        setGraphic(datePicker);
        setContentDisplay(ContentDisplay.TEXT_ONLY);
    }

    public static <T> Callback<TableColumn<T, LocalDate>, TableCell<T, LocalDate>> forTableColumn() {
        return tableColumn -> new EditableDateCell<>();
    }

    @Override
    protected void updateItem(LocalDate value, boolean empty) {
        super.updateItem(value, empty);

        if (empty || value == null) {
            setText(null);
        } else {
            setText(formatter.format(value));
        }

        if (isEditing()) {
            datePicker.requestFocus();
            datePicker.show();
            setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        } else {
            setContentDisplay(ContentDisplay.TEXT_ONLY);
        }
    }

    @Override
    public void startEdit() {
        super.startEdit();
        datePicker.setValue(getItem());
        setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        datePicker.requestFocus();
        datePicker.show();
    }

    @Override
    public void commitEdit(LocalDate newValue) {
        super.commitEdit(newValue);

        setContentDisplay(ContentDisplay.TEXT_ONLY);

    }

    @Override
    public void cancelEdit() {
        super.cancelEdit();
        setContentDisplay(ContentDisplay.TEXT_ONLY);
    }

}
