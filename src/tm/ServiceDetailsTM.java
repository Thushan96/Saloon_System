package tm;

import java.util.Objects;

public class ServiceDetailsTM {
    private String AppNo;
    private String ServiceName;
    private String ServiceId;
    private Double Price;
    private Double Discount;

    public ServiceDetailsTM(String appNo, String serviceName, String serviceId, Double price, Double discount) {
        setAppNo(appNo);
        setServiceName(serviceName);
        setServiceId(serviceId);
        setPrice(price);
        setDiscount(discount);
    }

    public ServiceDetailsTM() {
    }

    public String getAppNo() {
        return AppNo;
    }

    public void setAppNo(String appNo) {
        AppNo = appNo;
    }

    public String getServiceName() {
        return ServiceName;
    }

    public void setServiceName(String serviceName) {
        ServiceName = serviceName;
    }

    public String getServiceId() {
        return ServiceId;
    }

    public void setServiceId(String serviceId) {
        ServiceId = serviceId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ServiceDetailsTM)) return false;
        ServiceDetailsTM that = (ServiceDetailsTM) o;
        return Objects.equals(getAppNo(), that.getAppNo()) &&
                Objects.equals(getServiceName(), that.getServiceName()) &&
                Objects.equals(getServiceId(), that.getServiceId()) &&
                Objects.equals(getPrice(), that.getPrice()) &&
                Objects.equals(getDiscount(), that.getDiscount());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAppNo(), getServiceName(), getServiceId(), getPrice(), getDiscount());
    }

    @Override
    public String toString() {
        return "ServiceDetailsTM{" +
                "AppNo='" + AppNo + '\'' +
                ", ServiceName='" + ServiceName + '\'' +
                ", ServiceId='" + ServiceId + '\'' +
                ", Price=" + Price +
                ", Discount=" + Discount +
                '}';
    }
}
