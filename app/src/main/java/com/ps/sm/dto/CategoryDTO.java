package com.ps.sm.dto;

import androidx.annotation.NonNull;

public class CategoryDTO {

    String id;
    String name;
    boolean isSelected = false;

    public CategoryDTO() {
    }

    public CategoryDTO(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public CategoryDTO(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
    //    @NonNull
//    @Override
//    public String toString() {
//        return name.toString();
//    }
}
