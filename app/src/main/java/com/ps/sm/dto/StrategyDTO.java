package com.ps.sm.dto;

public class StrategyDTO {

    String id;
    String name;
    String category_id;
    boolean isSelected = false;

    public StrategyDTO(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public StrategyDTO(String id, String name, String category_id) {
        this.id = id;
        this.name = name;
        this.category_id = category_id;
    }

    public StrategyDTO() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
