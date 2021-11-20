package Model;

import java.util.Objects;

public class Services {
    private String SId;
    private String SName;
    private Double Sprice;
    private String Sdescription;

    public Services(String SId, String SName, Double sprice, String sdescription) {
        this.setSId(SId);
        this.setSName(SName);
        setSprice(sprice);
        setSdescription(sdescription);
    }

    public Services() {
    }

    public Services(String SId, String SName, Double sprice) {
        this.SId = SId;
        this.SName = SName;
        Sprice = sprice;
    }

    public String getSId() {
        return SId;
    }

    public void setSId(String SId) {
        this.SId = SId;
    }

    public String getSName() {
        return SName;
    }

    public void setSName(String SName) {
        this.SName = SName;
    }

    public Double getSprice() {
        return Sprice;
    }

    public void setSprice(Double sprice) {
        Sprice = sprice;
    }

    public String getSdescription() {
        return Sdescription;
    }

    public void setSdescription(String sdescription) {
        Sdescription = sdescription;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Services)) return false;
        Services services = (Services) o;
        return Objects.equals(getSId(), services.getSId()) &&
                Objects.equals(getSName(), services.getSName()) &&
                Objects.equals(getSprice(), services.getSprice()) &&
                Objects.equals(getSdescription(), services.getSdescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getSId(), getSName(), getSprice(), getSdescription());
    }

    @Override
    public String toString() {
        return "Services{" +
                "SId='" + SId + '\'' +
                ", SName='" + SName + '\'' +
                ", Sprice=" + Sprice +
                ", Sdescription='" + Sdescription + '\'' +
                '}';
    }
}
