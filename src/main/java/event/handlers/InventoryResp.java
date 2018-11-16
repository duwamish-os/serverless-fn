package event.handlers;

public class InventoryResp {
    private String item;
    private int totalQty;

    public InventoryResp() {
    }

    public InventoryResp(String item, int totalQty) {
        this.item = item;
        this.totalQty = totalQty;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public int getTotalQty() {
        return totalQty;
    }

    public void setTotalQty(int totalQty) {
        this.totalQty = totalQty;
    }
}
