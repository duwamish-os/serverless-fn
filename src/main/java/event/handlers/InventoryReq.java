package event.handlers;

import java.util.UUID;

public class InventoryReq {
    private UUID reqId;
    private String item;
    private int qty;
    private boolean useDb;

    public InventoryReq() {
    }

    public InventoryReq(UUID reqId, String item, int qty, boolean useDb) {
        this.reqId = reqId;
        this.item = item;
        this.qty = qty;
        this.useDb = useDb;
    }

    public UUID getReqId() {
        return reqId;
    }

    public void setReqId(UUID reqId) {
        this.reqId = reqId;
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

    public boolean isUseDb() {
        return useDb;
    }

    public void setUseDb(boolean useDb) {
        this.useDb = useDb;
    }
}
