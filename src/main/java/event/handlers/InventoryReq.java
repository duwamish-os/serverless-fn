package event.handlers;

public class InventoryReq {
    private String item;
    private int qty;

    public InventoryReq() {
    }

    public InventoryReq(String item, int qty) {
        this.item = item;
        this.qty = qty;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }
}
