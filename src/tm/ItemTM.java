package tm;

import java.util.Objects;

public class ItemTM {
    private String ItemId;
    private String ItemName;
    private int QtyOnHand;
    private Double ItemPrice;

    public ItemTM(String itemId, String itemName, int qtyOnHand, Double itemPrice) {
        setItemId(itemId);
        setItemName(itemName);
        setQtyOnHand(qtyOnHand);
        setItemPrice(itemPrice);
    }

    public ItemTM() {
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

    public int getQtyOnHand() {
        return QtyOnHand;
    }

    public void setQtyOnHand(int qtyOnHand) {
        QtyOnHand = qtyOnHand;
    }

    public Double getItemPrice() {
        return ItemPrice;
    }

    public void setItemPrice(Double itemPrice) {
        ItemPrice = itemPrice;
    }

    @Override
    public String toString() {
        return "ItemTM{" +
                "ItemId='" + ItemId + '\'' +
                ", ItemName='" + ItemName + '\'' +
                ", QtyOnHand=" + QtyOnHand +
                ", ItemPrice=" + ItemPrice +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ItemTM)) return false;
        ItemTM itemTM = (ItemTM) o;
        return getQtyOnHand() == itemTM.getQtyOnHand() &&
                Objects.equals(getItemId(), itemTM.getItemId()) &&
                Objects.equals(getItemName(), itemTM.getItemName()) &&
                Objects.equals(getItemPrice(), itemTM.getItemPrice());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getItemId(), getItemName(), getQtyOnHand(), getItemPrice());
    }
}
