package com.velebit.anippe.shared;

import com.velebit.anippe.shared.constants.Constants.Priority;
import org.eclipse.scout.rt.platform.text.TEXTS;

public enum PriorityEnum {
    LOW(Priority.LOW, TEXTS.get("Low"), Icons.GrayCircle),
    NORMAL(Priority.NORMAL, TEXTS.get("Normal"), Icons.YellowCircle),
    HIGH(Priority.HIGH, TEXTS.get("High"), Icons.OrangeCircle),
    URGENT(Priority.URGENT, TEXTS.get("Urgent"), Icons.RedCircle);

    private final int value;
    private final String name;
    private final String iconId;

    PriorityEnum(final int newValue, final String newName, final String newIconId) {
        value = newValue;
        name = newName;
        iconId = newIconId;
    }

    public static PriorityEnum fromValue(int value) {
        for (PriorityEnum status : PriorityEnum.values()) {
            if (status.getValue() == value) {
                return status;
            }
        }
        throw new IllegalArgumentException("Unknown value: " + value);
    }

    public int getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public String getIconId() {
        return iconId;
    }
}
