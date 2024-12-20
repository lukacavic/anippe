package com.velebit.anippe.shared.tasks;

import com.velebit.anippe.shared.constants.Constants;
import com.velebit.anippe.shared.icons.FontIcons;
import org.eclipse.scout.rt.platform.text.TEXTS;

public enum TaskStatusEnum {
    CREATED(Constants.TaskStatus.CREATED, TEXTS.get("Created"), FontIcons.Pencil),
    IN_PROGRESS(Constants.TaskStatus.IN_PROGRESS, TEXTS.get("InProgress"), FontIcons.Spinner1),
    AWAITING_FEEDBACK(Constants.TaskStatus.AWAITING_FEEDBACK, TEXTS.get("AwaitingFeedback"), FontIcons.Note),
    TESTING(Constants.TaskStatus.TESTING, TEXTS.get("Testing"), FontIcons.Users1),
    COMPLETED(Constants.TaskStatus.COMPLETED, TEXTS.get("Completed"), FontIcons.Check);

    private final int value;
    private final String name;
    private final String iconId;

    TaskStatusEnum(final int newValue, final String newName, final String newIconId) {
        value = newValue;
        name = newName;
        iconId = newIconId;
    }

    public static TaskStatusEnum fromValue(int value) {
        for (TaskStatusEnum status : TaskStatusEnum.values()) {
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
