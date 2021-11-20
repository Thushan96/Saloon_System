package Model;

import java.util.Objects;

public class Employee {
    private String Role;
    private String Id;
    private String Name;
    private String Address;
    private String Contact;
    private Double Salary;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Employee)) return false;
        Employee employee = (Employee) o;
        return Objects.equals(getRole(), employee.getRole()) &&
                Objects.equals(getId(), employee.getId()) &&
                Objects.equals(getName(), employee.getName()) &&
                Objects.equals(getAddress(), employee.getAddress()) &&
                Objects.equals(getContact(), employee.getContact()) &&
                Objects.equals(getSalary(), employee.getSalary()) &&
                Objects.equals(getPassword(), employee.getPassword());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getRole(), getId(), getName(), getAddress(), getContact(), getSalary(), getPassword());
    }

    private String Password;

    public Employee(String role, String id, String name, String address, String contact, Double salary, String password) {
        setRole(role);
        setId(id);
        setName(name);
        setAddress(address);
        setContact(contact);
        setSalary(salary);
        setPassword(password);
    }

    public Employee() {
    }

    public String getRole() {
        return Role;
    }

    public void setRole(String role) {
        Role = role;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getContact() {
        return Contact;
    }

    public void setContact(String contact) {
        Contact = contact;
    }

    public Double getSalary() {
        return Salary;
    }

    public void setSalary(Double salary) {
        Salary = salary;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "Role='" + Role + '\'' +
                ", Id='" + Id + '\'' +
                ", Name='" + Name + '\'' +
                ", Address='" + Address + '\'' +
                ", Contact='" + Contact + '\'' +
                ", Salary=" + Salary +
                ", Password='" + Password + '\'' +
                '}';
    }
}
