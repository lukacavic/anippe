package com.velebit.anippe.client.common.fields.colorfield;

import java.util.EventListener;

public interface IColorListener extends EventListener {
    void onChangeColor(String color);
}
