package com.velebit.anippe.shared;

import com.velebit.anippe.shared.constants.Constants.Related;
import com.velebit.anippe.shared.icons.FontIcons;
import org.eclipse.scout.rt.platform.text.TEXTS;

public enum RelatedEnum {
    CLIENT(Related.CLIENT, TEXTS.get("Client"), FontIcons.UserCheck),
    TICKET(Related.TICKET, TEXTS.get("Ticket"), FontIcons.Info),
    PROJECT(Related.PROJECT, TEXTS.get("Project"), FontIcons.Tasks),
    LEAD(Related.LEAD, TEXTS.get("Lead"), FontIcons.UserPlus);

    private final int value;
    private final String name;
    private final String iconId;

    RelatedEnum(final int newValue, final String newName, final String newIconId) {
        value = newValue;
        name = newName;
        iconId = newIconId;
    }

    public static RelatedEnum fromValue(int value) {
        for (RelatedEnum status : RelatedEnum.values()) {
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
