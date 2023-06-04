package com.velebit.anippe.client.interaction;

import com.velebit.anippe.shared.icons.FontIcons;
import org.eclipse.scout.rt.client.ui.notification.Notification;
import org.eclipse.scout.rt.platform.Bean;
import org.eclipse.scout.rt.platform.status.IStatus;
import org.eclipse.scout.rt.platform.status.Status;

@Bean
public class FormNotificationHelper {

	public Notification createInfoNotification(String text) {
		return createNotification(text, IStatus.INFO, FontIcons.Info, false);
	}

	public Notification createInfoNotification(String text, boolean closable) {
		return createNotification(text, IStatus.INFO, FontIcons.Info, closable);
	}

	public Notification createWarningNotification(String text) {
		return createNotification(text, IStatus.WARNING, FontIcons.ExclamationMarkCircle, false);
	}

	public Notification createWarningNotification(String text, boolean closable) {
		return createNotification(text, IStatus.WARNING, FontIcons.ExclamationMarkCircle, closable);
	}

	public Notification createSuccessNotification(String text) {
		return createNotification(text, IStatus.OK, FontIcons.Check, false);
	}

	public Notification createSuccessNotification(String text, boolean closable) {
		return createNotification(text, IStatus.OK, FontIcons.Check, closable);
	}

	private Notification createNotification(String text, int status, String icon, boolean closable) {
		return new Notification(new Status(text, status, icon), closable);
	}

}
