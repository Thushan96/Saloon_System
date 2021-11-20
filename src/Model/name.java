package Model;

import java.util.Objects;

public class name {
    private String Name;

    public name(String name) {
        setName(name);
    }

    public name() {
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    @Override
    public String toString() {
        return "name{" +
                "Name='" + Name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof name)) return false;
        name name = (name) o;
        return Objects.equals(getName(), name.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }
}
