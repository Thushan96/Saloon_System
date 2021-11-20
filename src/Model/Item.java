package Model;

import java.util.Objects;

public class Item {
    private String ItemId;
    private String ItemName;
    private Double ItemPrice;
    private int QtyOnHand;

    public Item(String itemId, String itemName, Double itemPrice, int qtyOnHand) {
        setItemId(itemId);
        setItemName(itemName);
        setItemPrice(itemPrice);
        setQtyOnHand(qtyOnHand);
    }

    public Item() {
    }

    public String getItemId() {
        return ItemId;
    }

    public void setItemId(String itemId) {
        ItemId = itemId;
    }

    public String getItemName() {
        return ItemName;
    }

    public void setItemName(String itemName) {
        ItemName = itemName;
    }

    public Double getItemPrice() {
        return ItemPrice;
    }

    public void setItemPrice(Double itemPrice) {
        ItemPrice = itemPrice;
    }

    public int getQtyOnHand() {
        return QtyOnHand;
    }

    public void setQtyOnHand(int qtyOnHand) {
        QtyOnHand = qtyOnHand;
    }

    @Override
    public String toString() {
        return "Item{" +
                "ItemId='" + ItemId + '\'' +
                ", ItemName='" + ItemName + '\'' +
                ", ItemPrice=" + ItemPrice +
                ", QtyOnHand=" + QtyOnHand +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Item)) return false;
        Item item = (Item) o;
        return getQtyOnHand() == item.getQtyOnHand() &&
                Objects.equals(getItemId(), item.getItemId()) &&
                Objects.equals(getItemName(), item.getItemName()) &&
                Objects.equals(getItemPrice(), item.getItemPrice());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getItemId(), getItemName(), getItemPrice(), getQtyOnHand());
    }
}
