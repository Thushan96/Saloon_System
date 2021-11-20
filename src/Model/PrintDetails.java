package Model;

import java.util.Objects;

public class PrintDetails {
    private String AppNo;
    private String Nic;
    private String EmpId;
    private Double Price;
    private Double Discount;
    private String ServiceName;

    public PrintDetails(String appNo, String nic, String empId, Double price, Double discount, String serviceName) {
        setAppNo(appNo);
        setNic(nic);
        setEmpId(empId);
        setPrice(price);
        setDiscount(discount);
        setServiceName(serviceName);
    }

    public PrintDetails() {
    }

    public String getAppNo() {
        return AppNo;
    }

    public void setAppNo(String appNo) {
        AppNo = appNo;
    }

    public String getNic() {
        return Nic;
    }

    public void setNic(String nic) {
        Nic = nic;
    }

    public String getEmpId() {
        return EmpId;
    }

    public void setEmpId(String empId) {
        EmpId = empId;
    }

    public Double getPrice() {
        return Price;
    }

    public void setPrice(Double price) {
        Price = price;
    }

    public Double getDiscount() {
        return Discount;
    }

    public void setDiscount(Double discount) {
        Discount = discount;
    }

    public String getServiceName() {
        return ServiceName;
    }

    public void setServiceName(String serviceName) {
        ServiceName = serviceName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PrintDetails)) return false;
        PrintDetails that = (PrintDetails) o;
        return Objects.equals(getAppNo(), that.getAppNo()) &&
                Objects.equals(getNic(), that.getNic()) &&
                Objects.equals(getEmpId(), that.getEmpId()) &&
                Objects.equals(getPrice(), that.getPrice()) &&
                Objects.equals(getDiscount(), that.getDiscount()) &&
                Objects.equals(getServiceName(), that.getServiceName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAppNo(), getNic(), getEmpId(), getPrice(), getDiscount(), getServiceName());
    }

    @Override
    public String toString() {
        return "PrintDetails{" +
                "AppNo='" + AppNo + '\'' +
                ", Nic='" + Nic + '\'' +
                ", EmpId='" + EmpId + '\'' +
                ", Price=" + Price +
                ", Discount=" + Discount +
                ", ServiceName='" + ServiceName + '\'' +
                '}';
    }
}
