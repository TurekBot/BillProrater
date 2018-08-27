package tech.ugma.ubs.table;

import com.jfoenix.controls.JFXTextField;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TableCell;
import javafx.scene.control.TextFormatter;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.StringConverter;
import tech.ugma.ubs.model.Bill;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.function.UnaryOperator;

public class EditableCurrencyCell extends TableCell<Bill, BigDecimal> {

    private JFXTextField textField;
    private TextFormatter<BigDecimal> textFormatter;

    private NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();

    public EditableCurrencyCell() {

        textField = new JFXTextField();

        UnaryOperator<TextFormatter.Change> filter = c -> {
            String newText = c.getControlNewText();
            String currencySymbol = currencyFormat.getCurrency().getSymbol();

            // always allow deleting all characters:
            if (newText.isEmpty()) {
                return c;
            }

            // Allow just the currency symbol
            if (newText.equals(currencySymbol)) {
                return c;
            }

            // If there's not a currency symbol, put one
            if (!newText.startsWith(currencySymbol)) {
                c.setText(currencySymbol + c.getControlNewText());
                c.setCaretPosition(c.getCaretPosition() + 1);
                c.setAnchor(c.getAnchor() + 1);
                newText = c.getControlNewText();
            }

            // otherwise, must match currency format
            Number parsedNumber = null;
            try {
                parsedNumber = currencyFormat.parse(newText);
            } catch (ParseException e) {
                System.err.println("That's not a valid input.");
            }
            if (parsedNumber == null) {
                return null;
            }


            return c;
        };

        StringConverter<BigDecimal> converter = new StringConverter<BigDecimal>() {

            @Override
            public String toString(BigDecimal value) {
                return value == null ? "$0" : currencyFormat.format(value);
            }

            @Override
            public BigDecimal fromString(String string) {


                Number parsedNumber = null;
                try {
                    parsedNumber = currencyFormat.parse(string);
                } catch (ParseException e) {
                    System.err.println("Unable to parse the input as a BigDecimal!");
                    e.printStackTrace();
                }

                if (parsedNumber != null) {
                    return BigDecimal.valueOf(parsedNumber.doubleValue());
                } else {
                    // otherwise, just return the current value of the cell:
                    return getItem();
                }
            }

        };

        textFormatter = new TextFormatter<>(converter, BigDecimal.ZERO, filter);
        textField.setTextFormatter(textFormatter);

        textField.addEventFilter(KeyEvent.KEY_RELEASED, e -> {
            if (e.getCode() == KeyCode.ESCAPE) {
                cancelEdit();
            }
        });

        textField.setOnAction(e -> commitEdit(converter.fromString(textField.getText())));

        setGraphic(textField);
        setContentDisplay(ContentDisplay.TEXT_ONLY);
    }

    @Override
    protected void updateItem(BigDecimal value, boolean empty) {
        super.updateItem(value, empty);

        if (empty || value == null) {
            setText(null);
        } else {
            setText(textFormatter.getValueConverter().toString(value));
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
        textFormatter.setValue(getItem());
        setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        textField.requestFocus();
        textField.selectAll();
    }

    @Override
    public void commitEdit(BigDecimal newValue) {
        super.commitEdit(newValue);
        setContentDisplay(ContentDisplay.TEXT_ONLY);
    }

    @Override
    public void cancelEdit() {
        super.cancelEdit();
        setContentDisplay(ContentDisplay.TEXT_ONLY);
    }

}
