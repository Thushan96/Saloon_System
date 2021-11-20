package tm;

public class Customer {
    private String Nic;
    private String Name;
    private Integer Age;
    private String HairStyle;

    public Customer(String nic, String name, Integer age, String hairStyle) {
        setNic(nic);
        setName(name);
        setAge(age);
        setHairStyle(hairStyle);
    }

    public Customer(String nic, String name, Integer age) {
        setNic(nic);
        setName(name);
        setAge(age);
    }

    public Customer() {
    }

    public String getNic() {
        return Nic;
    }

    public void setNic(String nic) {
        Nic = nic;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public Integer getAge() {
        return Age;
    }

    public void setAge(Integer age) {
        Age = age;
    }

    public String getHairStyle() {
        return HairStyle;
    }

    public void setHairStyle(String hairStyle) {
        HairStyle = hairStyle;
    }
}
