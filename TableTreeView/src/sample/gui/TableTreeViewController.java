package sample.gui;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import sample.DoubleFieldTableCellImpl;
import sample.Company;
import sample.Department;
import sample.Employee;

import java.net.URL;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

public class TableTreeViewController implements Initializable, Observer {

    @FXML
    private TreeView<Object> treeView;

    @FXML
    private TextField tbDepartment;

    @FXML
    private TextField tbEmployee;

    @FXML
    private Button btnDepartment;

    @FXML
    private Button btnEmployee;

    @FXML
    private TableView<Employee> table = new TableView<>();

    private TreeItem<Object> lastSelectedItem = null;

    private Company company = new Company("Fontys");

    private ObservableList<Employee> data;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initTreeView();
        initTable();
        btnDepartment.setOnAction(this::btnDepartmentAction);
        btnEmployee.setOnAction(this::btnEmployeeAction);
    }

    private void initTable() {
        TableColumn<Employee, String> name = new TableColumn<>("Name");
        table.setEditable(true);

        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        name.setCellFactory(TextFieldTableCell.forTableColumn());
        name.setOnEditCommit(t -> (t.getTableView().getItems().get(t.getTablePosition().getRow())).setName(t.getNewValue()));

        TableColumn<Employee, String> email = new TableColumn<>("Email");
        email.setCellValueFactory(new PropertyValueFactory<>("email"));
        email.setCellFactory(TextFieldTableCell.forTableColumn());

        TableColumn<Employee, Double> salary = new TableColumn<>("Salary");
        salary.setCellValueFactory(new PropertyValueFactory<>("salary"));
        salary.setCellFactory(employeeDoubleTableColumn -> new DoubleFieldTableCellImpl());

        table.getColumns().addAll(name, email, salary);
    }

    private void initTreeView() {
        company.addObserver(this);

        Department ictDepartment = new Department("ICT");
        Department ISSD = new Department("ISSD");

        company.addDepartment(ictDepartment);
        company.addDepartment(ISSD);

        Employee teacher = new Employee("Harry", "harry@fontys.nl", 100.00);
        Employee teacher2 = new Employee("Henk", "henk@fontys.nl", 110.10);
        ictDepartment.addEmployee(teacher);
        ictDepartment.addEmployee(teacher2);

        updateTreeView();
        treeView.setShowRoot(true);

        // TreeView lastSelectedItem observer
        treeView.getSelectionModel().selectedItemProperty().addListener((observable, oldVal, newVal) -> {
            lastSelectedItem = newVal;

            // Update table if selected item is a department
            if(lastSelectedItem != null) {
                Department dep = null;
                if (lastSelectedItem.getValue().getClass() == Employee.class) {
                    dep = (Department) lastSelectedItem.getParent().getValue();
                }
                else if (lastSelectedItem.getValue().getClass() == Department.class) {
                    dep = (Department) lastSelectedItem.getValue();
                }
                if(dep!=null)
                {
                    data = dep.getEmployees();
                    table.setItems(data);
                }
            }
        });
    }

    /* btnDepartment action handler
     Adds the department to the companies department list.
     */
    private void btnDepartmentAction(ActionEvent e)
    {
        String text = tbDepartment.getText();

        if(!text.isEmpty()) {
            Department d = new Department(text);
            company.addDepartment(d);
        }
    }

    private void btnEmployeeAction(ActionEvent e)
    {
        String text = tbEmployee.getText();
        Department department = null;

        if(lastSelectedItem != null) {
            if (lastSelectedItem.getValue().getClass() == Department.class) {
                department = (Department) lastSelectedItem.getValue();
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText("Please select a department!");
                alert.show();
                return;
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Please select a department!");
            alert.show();
        }

        // If everything is okay, add employee to selected department
        if(!text.isEmpty() && department != null) {
            Employee employee = new Employee(text, text +" @fontys.nl", 1000);
            data.add(employee);
        }
    }

    private void updateTreeView() {
        treeView.setRoot(company.toTreeItem());
        treeView.getRoot().setExpanded(true);
    }

    @Override
    public void update(Observable observable, Object o) {
        updateTreeView();
    }


}