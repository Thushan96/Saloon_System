package tm;

import java.util.Objects;

public class ServiceTM {
    private String Sid;
    private String Sname;
    private Double Sprice;
    private String Sdescription;

    public ServiceTM(String sid, String sname, Double sprice, String sdescription) {
        setSid(sid);
        setSname(sname);
        setSprice(sprice);
        setSdescription(sdescription);
    }

    public ServiceTM() {
    }

    public String getSid() {
        return Sid;
    }

    public void setSid(String sid) {
        Sid = sid;
    }

    public String getSname() {
        return Sname;
    }

    public void setSname(String sname) {
        Sname = sname;
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
        if (!(o instanceof ServiceTM)) return false;
        ServiceTM service = (ServiceTM) o;
        return Objects.equals(getSid(), service.getSid()) &&
                Objects.equals(getSname(), service.getSname()) &&
                Objects.equals(getSprice(), service.getSprice()) &&
                Objects.equals(getSdescription(), service.getSdescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getSid(), getSname(), getSprice(), getSdescription());
    }

    @Override
    public String toString() {
        return "ServiceTM{" +
                "Sid='" + Sid + '\'' +
                ", Sname='" + Sname + '\'' +
                ", Sprice=" + Sprice +
                ", Sdescription='" + Sdescription + '\'' +
                '}';
    }
}
