package tm;

import java.util.Objects;

public class EmoloyeeTM {
    private String Id;
    private String Name;
    private String Contact;
    private String Address;
    private Double Salary;

    public EmoloyeeTM(String id, String name, String contact, String address, Double salary) {
        setId(id);
        setName(name);
        setContact(contact);
        setAddress(address);
        setSalary(salary);
    }

    public EmoloyeeTM(String id, String name) {
        Id = id;
        Name = name;
    }

    public EmoloyeeTM() {
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

    public String getContact() {
        return Contact;
    }

    public void setContact(String contact) {
        Contact = contact;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public Double getSalary() {
        return Salary;
    }

    public void setSalary(Double salary) {
        Salary = salary;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EmoloyeeTM)) return false;
        EmoloyeeTM that = (EmoloyeeTM) o;
        return Objects.equals(getId(), that.getId()) &&
                Objects.equals(getName(), that.getName()) &&
                Objects.equals(getContact(), that.getContact()) &&
                Objects.equals(getAddress(), that.getAddress()) &&
                Objects.equals(getSalary(), that.getSalary());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getContact(), getAddress(), getSalary());
    }

    @Override
    public String toString() {
        return "EmoloyeeTM{" +
                "Id='" + Id + '\'' +
                ", Name='" + Name + '\'' +
                ", Contact='" + Contact + '\'' +
                ", Address='" + Address + '\'' +
                ", Salary=" + Salary +
                '}';
    }
}
