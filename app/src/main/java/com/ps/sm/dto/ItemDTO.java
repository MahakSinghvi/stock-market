package com.ps.sm.dto;

public class ItemDTO {
    String id;
    String name;
    String code;
    String lot_size;
    String strike_gap;
    String total_strike;
    int image;

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLot_size() {
        return lot_size;
    }

    public void setLot_size(String lot_size) {
        this.lot_size = lot_size;
    }

    public String getStrike_gap() {
        return strike_gap;
    }

    public void setStrike_gap(String strike_gap) {
        this.strike_gap = strike_gap;
    }

    public String getTotal_strike() {
        return total_strike;
    }

    public void setTotal_strike(String total_strike) {
        this.total_strike = total_strike;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
