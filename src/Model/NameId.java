package Model;

import java.util.Objects;

public class NameId {
    private String name;
    private String id;

    public NameId() {
    }

    public NameId(String name, String id) {
        this.setName(name);
        this.setId(id);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "NameId{" +
                "name='" + name + '\'' +
                ", id='" + id + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NameId)) return false;
        NameId nameId = (NameId) o;
        return Objects.equals(getName(), nameId.getName()) &&
                Objects.equals(getId(), nameId.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getId());
    }
}
