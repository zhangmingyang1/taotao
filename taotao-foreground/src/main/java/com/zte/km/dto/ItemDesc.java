package com.zte.km.dto;

import java.sql.Timestamp;

public class ItemDesc {
    private Long itemId;

    private String itemDesc;

    private Timestamp created;

    private Timestamp updated;

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public String getItemDesc() {
        return itemDesc;
    }

    public void setItemDesc(String itemDesc) {
        this.itemDesc = itemDesc == null ? null : itemDesc.trim();
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    public void setUpdated(Timestamp updated) {
        this.updated = updated;
    }

    public Timestamp getCreated() {
        return created;
    }

    public Timestamp getUpdated() {
        return updated;
    }
}