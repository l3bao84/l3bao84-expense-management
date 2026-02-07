package com.baold.expense_management.model.request;

import com.baold.expense_management.utils.DataUtils;
import lombok.Data;

@Data
public class CreateCategoryRequest {

    private String name;
    private String description;
    private String icon;
    private Long id;

    public void setName(String name) {
        this.name = DataUtils.safeTrim(name);
    }

    public void setDescription(String description) {
        this.description = DataUtils.safeTrim(description);
    }

    public void setIcon(String icon) {
        this.icon = DataUtils.safeTrim(icon);
    }
}
