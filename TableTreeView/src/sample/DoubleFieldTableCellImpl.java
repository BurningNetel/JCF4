package sample;

import javafx.scene.control.Alert;
import javafx.scene.control.TableCell;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;

/**
 *  Implementation for a textfield that converts text to double.
 */
public class DoubleFieldTableCellImpl extends TableCell<Employee, Double> {
    private TextField textfield;

    @Override
    public void startEdit() {
        super.startEdit();

        if(textfield == null){
            createTextField();
        }

        setText(null);
        setGraphic(textfield);
        textfield.selectAll();
    }

    @Override
    protected void updateItem(Double item, boolean empty) {
        super.updateItem(item, empty);

        if(empty){
            setText(null);
            setGraphic(null);
        } else {
            if (isEditing()){
                if (textfield != null){
                    textfield.setText(getString());
                }
                setText(null);
                setGraphic(textfield);
            } else {
                setText(getString());
                setGraphic(getTableColumn().getGraphic());
            }
        }
    }

    private String getString() {
        return getItem() == null ? "" : getItem().toString();
    }

    @Override
    public void cancelEdit() {
        super.cancelEdit();
        setText(getItem().toString());
        setGraphic(getTableColumn().getGraphic());
    }

    @Override
    public void commitEdit(Double newValue) {
        super.commitEdit(newValue);
        ((Employee) getTableRow().getItem()).setSalary(newValue);
    }

    private void createTextField() {
        textfield = new TextField(getText());
        textfield.setOnKeyReleased(t -> {
            if (t.getCode() == KeyCode.ENTER){
                Double result;
                try {
                    result = Double.valueOf(textfield.getText());
                } catch (NumberFormatException e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Please fill in a double! Ex: 1.20");
                    alert.showAndWait();
                    return;
                }
                commitEdit(result);

            }
            else if (t.getCode() == KeyCode.ESCAPE)
            {
                cancelEdit();
            }
        });
    }
}
