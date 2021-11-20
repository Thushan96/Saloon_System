package tm;

import javafx.scene.control.Button;

import java.util.Objects;

public class ItemSellTM {
    private String ItemId;
    private String ItemName;
    private Double ItemPrice;
    private int Quantity;
    private Double total;
    private Button btn;

    public ItemSellTM(String itemId, String itemName, Double itemPrice, int quantity, Double total, Button btn) {
        ItemId = itemId;
        ItemName = itemName;
        ItemPrice = itemPrice;
        Quantity = quantity;
        this.total = total;
        this.btn = btn;
    }

    public ItemSellTM() {
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

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Button getBtn() {
        return btn;
    }

    public void setBtn(Button btn) {
        this.btn = btn;
    }

    @Override
    public String toString() {
        return "ItemSellTM{" +
                "ItemId='" + ItemId + '\'' +
                ", ItemName='" + ItemName + '\'' +
                ", ItemPrice=" + ItemPrice +
                ", Quantity=" + Quantity +
                ", total=" + total +
                ", btn=" + btn +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ItemSellTM)) return false;
        ItemSellTM that = (ItemSellTM) o;
        return getQuantity() == that.getQuantity() &&
                Objects.equals(getItemId(), that.getItemId()) &&
                Objects.equals(getItemName(), that.getItemName()) &&
                Objects.equals(getItemPrice(), that.getItemPrice()) &&
                Objects.equals(getTotal(), that.getTotal()) &&
                Objects.equals(getBtn(), that.getBtn());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getItemId(), getItemName(), getItemPrice(), getQuantity(), getTotal(), getBtn());
    }
}
