package Model;

import java.util.Objects;

public class Appointment {
    private String AppNo;
    private String EmpId;
    private Double Total;
    private Double discount;

    public Appointment() {
    }

    public Appointment(String appNo, String empId, Double total, Double discount) {
        setAppNo(appNo);
        setEmpId(empId);
        setTotal(total);
        this.setDiscount(discount);
    }

    public String getAppNo() {
        return AppNo;
    }

    public void setAppNo(String appNo) {
        AppNo = appNo;
    }

    public String getEmpId() {
        return EmpId;
    }

    public void setEmpId(String empId) {
        EmpId = empId;
    }

    public Double getTotal() {
        return Total;
    }

    public void setTotal(Double total) {
        Total = total;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    @Override
    public String toString() {
        return "Appointment{" +
                "AppNo='" + AppNo + '\'' +
                ", EmpId='" + EmpId + '\'' +
                ", Total=" + Total +
                ", discount=" + discount +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Appointment)) return false;
        Appointment that = (Appointment) o;
        return Objects.equals(getAppNo(), that.getAppNo()) &&
                Objects.equals(getEmpId(), that.getEmpId()) &&
                Objects.equals(getTotal(), that.getTotal()) &&
                Objects.equals(getDiscount(), that.getDiscount());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAppNo(), getEmpId(), getTotal(), getDiscount());
    }
}
