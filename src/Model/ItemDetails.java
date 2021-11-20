package Model;

import java.util.Objects;

public class ItemDetails {
    private String itemCode;
    private double unitPrice;
    private int qtyOfSell;

    public ItemDetails(String itemCode, double unitPrice, int qtyOfSell) {
        this.setItemCode(itemCode);
        this.setUnitPrice(unitPrice);
        this.setQtyOfSell(qtyOfSell);
    }

    public ItemDetails() {
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getQtyOfSell() {
        return qtyOfSell;
    }

    public void setQtyOfSell(int qtyOfSell) {
        this.qtyOfSell = qtyOfSell;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ItemDetails)) return false;
        ItemDetails that = (ItemDetails) o;
        return Double.compare(that.getUnitPrice(), getUnitPrice()) == 0 &&
                getQtyOfSell() == that.getQtyOfSell() &&
                Objects.equals(getItemCode(), that.getItemCode());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getItemCode(), getUnitPrice(), getQtyOfSell());
    }

    @Override
    public String toString() {
        return "ItemDetails{" +
                "itemCode='" + itemCode + '\'' +
                ", unitPrice=" + unitPrice +
                ", qtyOfSell=" + qtyOfSell +
                '}';
    }
}
