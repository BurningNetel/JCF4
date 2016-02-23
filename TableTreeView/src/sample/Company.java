package sample;

import javafx.scene.control.TreeItem;

import java.util.*;

/**
 * Created by joep on 2/15/16.
 */
public class Company extends Observable implements Observer {
    private String name;
    private Set<Department> departments;

    public Company(String name)
    {
        this.name = name;
        this.departments = new HashSet<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        push();
    }

    public Set<Department> getDepartments() {
        return departments;
    }

    public void setDepartments(HashSet<Department> departments) {
        this.departments = departments;
        departments.forEach(d -> d.addObserver(this));
        push();
    }

    @Override
    public String toString() {
        return this.name;
    }

    public void addDepartment(Department ictDepartment) {
        this.departments.add(ictDepartment);
        ictDepartment.addObserver(this);
        push();
    }

    private void push() {
        setChanged();
        notifyObservers();
    }

    public TreeItem<Object> toTreeItem() {
        TreeItem<Object> rootItem = new TreeItem<>(this);
        departments.forEach(d -> rootItem.getChildren().add(d.toTreeItem()));
        return rootItem;
    }

    @Override
    public void update(Observable observable, Object o) {
        push();
    }
}
