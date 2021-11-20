package Model;

import tm.ItemTM;

import java.util.ArrayList;
import java.util.Objects;

public class Orders {
    private String OrderId;
    private Double cost;
    private ArrayList<ItemDetails> items;

    public Orders(String orderId,Double cost, ArrayList<ItemDetails> items) {
        OrderId = orderId;

        this.cost = cost;
        this.items = items;
    }

    public Orders() {
    }

    public String getOrderId() {
        return OrderId;
    }

    public void setOrderId(String orderId) {
        OrderId = orderId;
    }


    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Orders)) return false;
        Orders orders = (Orders) o;
        return Objects.equals(getOrderId(), orders.getOrderId()) &&
                Objects.equals(getCost(), orders.getCost()) &&
                Objects.equals(getItems(), orders.getItems());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getOrderId(), getCost(), getItems());
    }

    public ArrayList<ItemDetails> getItems() {
        return items;
    }

    public void setItems(ArrayList<ItemDetails> items) {
        this.items = items;
    }
}
