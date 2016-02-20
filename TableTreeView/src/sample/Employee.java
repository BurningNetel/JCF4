package sample;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
/**
 * Created by joep on 2/15/16.
 */
public class Employee {

    private SimpleStringProperty name;
    private SimpleStringProperty email;
    private SimpleDoubleProperty salary;

    public Employee(String name, String email, double salary){
        this.name = new SimpleStringProperty(name);
        this.email = new SimpleStringProperty(email);
        this.salary = new SimpleDoubleProperty(salary);
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name){
        this.name.set(name);
    }

    public String getEmail() {
        return email.get();
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public double getSalary() {
        return salary.get();
    }

    public void setSalary(double salary) {
        this.salary.set(salary);
    }

    @Override
    public String toString() {
        return this.name.getValueSafe();
    }
}
