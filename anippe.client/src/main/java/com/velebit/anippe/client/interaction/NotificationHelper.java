package com.velebit.anippe.client.interaction;

import com.velebit.anippe.client.ClientSession;
import com.velebit.anippe.shared.icons.FontIcons;
import org.eclipse.scout.rt.client.ui.desktop.notification.DesktopNotification;
import org.eclipse.scout.rt.platform.status.IStatus;
import org.eclipse.scout.rt.platform.status.Status;
import org.eclipse.scout.rt.platform.text.TEXTS;


public class NotificationHelper {

    public static void showNotification(String text) {
        showNotification(text, IStatus.INFO, FontIcons.Info);
    }

    public static void showWarningNotification(String text) {
        showNotification(text, IStatus.WARNING, FontIcons.ExclamationMarkCircle);
    }

    public static void showErrorNotification(String text) {
        showNotification(text, IStatus.ERROR, FontIcons.ExclamationMarkBold);
    }

    public static void showSuccessNotification(String text) {
        showNotification(text, IStatus.OK, FontIcons.Check);
    }

    public static void showInfoNotification(String text) {
        showNotification(text, IStatus.INFO, FontIcons.Info);
    }

    public static void showDeleteSuccessNotification() {
        showSuccessNotification(TEXTS.get("DeleteSuccess"));
    }

    public static void showSaveSuccessNotification() {
        showSuccessNotification(TEXTS.get("SaveSuccess"));
    }

    private static void showNotification(String text, int priority, String icon) {
        IStatus status = new Status(text, priority, icon);

        DesktopNotification notification = new DesktopNotification(status);
        ClientSession.get().getDesktop().addNotification(notification);
    }

}
