package tech.ugma.ubs.table;

import com.jfoenix.controls.JFXTextField;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.Callback;

public class EditableTextCell<T> extends TableCell<T, String> {

    private JFXTextField textField;

    public EditableTextCell() {

        textField = new JFXTextField();


        textField.addEventFilter(KeyEvent.KEY_RELEASED, e -> {
            if (e.getCode() == KeyCode.ESCAPE) {
                cancelEdit();
            }
        });

        textField.setOnAction(e -> commitEdit(textField.getText()));

        setGraphic(textField);
        setContentDisplay(ContentDisplay.TEXT_ONLY);
    }

    public static <T> Callback<TableColumn<T, String>, TableCell<T, String>> forTableColumn() {
        return tableColumn -> new EditableTextCell<>();
    }

    @Override
    protected void updateItem(String value, boolean empty) {
        super.updateItem(value, empty);

        if (empty || value == null) {
            setText(null);
        } else {
            setText(value);
        }

        if (isEditing()) {
            textField.requestFocus();
            textField.selectAll();
            setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        } else {
            setContentDisplay(ContentDisplay.TEXT_ONLY);
        }
    }

    @Override
    public void startEdit() {
        super.startEdit();
        textField.setText(getItem());
        setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        textField.requestFocus();
        textField.selectAll();
    }

    @Override
    public void commitEdit(String newValue) {
        super.commitEdit(newValue);
        setContentDisplay(ContentDisplay.TEXT_ONLY);
    }

    @Override
    public void cancelEdit() {
        super.cancelEdit();
        setContentDisplay(ContentDisplay.TEXT_ONLY);
    }

}
