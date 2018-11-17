package event.handlers;

import java.util.UUID;

public class InventoryResp {
    private UUID apiId;
    private String item;
    private int totalQty;

    public InventoryResp() {
    }

    public InventoryResp(UUID apiId, String item, int totalQty) {
        this.apiId = apiId;
        this.item = item;
        this.totalQty = totalQty;
    }

    public UUID getApiId() {
        return apiId;
    }

    public void setApiId(UUID apiId) {
        this.apiId = apiId;
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
