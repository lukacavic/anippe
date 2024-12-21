package com.velebit.anippe.shared.tasks;

import java.io.Serializable;

public class TaskCheckList implements Serializable {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
