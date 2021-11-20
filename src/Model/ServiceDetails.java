package Model;

import javafx.collections.ObservableArray;

import java.util.Objects;

public class ServiceDetails {
    private String Sid;
    private String AppNo;
    private Double Price;

    public ServiceDetails(String sid, String appNo, Double price) {
        setSid(sid);
        setAppNo(appNo);
        setPrice(price);
    }

    public ServiceDetails() {
    }

    public String getSid() {
        return Sid;
    }

    public void setSid(String sid) {
        Sid = sid;
    }

    public String getAppNo() {
        return AppNo;
    }

    public void setAppNo(String appNo) {
        AppNo = appNo;
    }

    public Double getPrice() {
        return Price;
    }

    public void setPrice(Double price) {
        Price = price;
    }

    @Override
    public String toString() {
        return "ServiceDetails{" +
                "Sid='" + Sid + '\'' +
                ", AppNo='" + AppNo + '\'' +
                ", Price=" + Price +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ServiceDetails)) return false;
        ServiceDetails that = (ServiceDetails) o;
        return Objects.equals(getSid(), that.getSid()) &&
                Objects.equals(getAppNo(), that.getAppNo()) &&
                Objects.equals(getPrice(), that.getPrice());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getSid(), getAppNo(), getPrice());
    }
}
