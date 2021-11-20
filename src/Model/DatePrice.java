package Model;

import java.util.Date;
import java.util.Objects;

public class DatePrice {
    private double price;
    private Date date;

    public DatePrice() {
    }

    public DatePrice(double price, Date date) {
        this.setPrice(price);
        this.setDate(date);
    }


    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DatePrice)) return false;
        DatePrice datePrice = (DatePrice) o;
        return Double.compare(datePrice.getPrice(), getPrice()) == 0 &&
                Objects.equals(getDate(), datePrice.getDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPrice(), getDate());
    }

    @Override
    public String toString() {
        return "DatePrice{" +
                "price=" + price +
                ", date=" + date +
                '}';
    }
}
