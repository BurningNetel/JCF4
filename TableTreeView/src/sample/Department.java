package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;

import java.util.Observable;

/**
 * Created by joep on 2/15/16.
 */
public class Department extends Observable{

    private String name;
    private ObservableList<Employee> employees;

    public Department(String name)
    {
        this.name = name;
        this.employees = FXCollections.observableArrayList();
    }

    public ObservableList<Employee> getEmployees() {
        return employees;
    }

    public String getName() {
        return name;
    }

    private void update() {
        setChanged();
        notifyObservers();
    }

    public void setName(String name) {
        this.name = name;
        update();
    }

    @Override
    public String toString() {
        return this.name;
    }

    public void addEmployee(Employee teacher) {
        this.employees.add(teacher);
        update();
    }

    public TreeItem<Object> toTreeItem() {
        TreeItem<Object> departmentItem = new TreeItem<>(this);
        employees.forEach(e -> departmentItem.getChildren().add(new TreeItem<>(e)));
        return departmentItem;
    }
}
