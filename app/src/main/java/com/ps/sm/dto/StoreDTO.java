package com.ps.sm.dto;

public class StoreDTO {

    String id = "";
    String stock_id = "";
    String cmp = "";
    String call_val = "";
    int price_val = 0;
    String put_val = "";
    String cat_id = ""; 
    String strategy_id = "";

    public StoreDTO() {
    }

    public StoreDTO(String id, String stock_id, String cmp,
                    String call_val,int  price_val, String put_val, String cat_id, String strategy_id) {
        this.id = id;
        this.stock_id = stock_id;
        this.cmp = cmp;
        this.call_val = call_val;
        this.price_val = price_val;
        this.put_val = put_val;
        this.cat_id = cat_id;
        this.strategy_id = strategy_id;
    }

    public StoreDTO(String call_val, int price_val, String put_val) {
        this.call_val = call_val;
        this.price_val = price_val;
        this.put_val = put_val;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStock_id() {
        return stock_id;
    }

    public void setStock_id(String stock_id) {
        this.stock_id = stock_id;
    }

    public String getCmp() {
        return cmp;
    }

    public void setCmp(String cmp) {
        this.cmp = cmp;
    }

    public String getCall_val() {
        return call_val;
    }

    public void setCall_val(String call_val) {
        this.call_val = call_val;
    }

    public int getPrice_val() {
        return price_val;
    }

    public void setPrice_val(int price_val) {
        this.price_val = price_val;
    }

    public String getPut_val() {
        return put_val;
    }

    public void setPut_val(String put_val) {
        this.put_val = put_val;
    }

    public String getCat_id() {
        return cat_id;
    }

    public void setCat_id(String cat_id) {
        this.cat_id = cat_id;
    }

    public String getStrategy_id() {
        return strategy_id;
    }

    public void setStrategy_id(String strategy_id) {
        this.strategy_id = strategy_id;
    }
}
