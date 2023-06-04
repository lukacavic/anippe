package com.velebit.anippe.client.administration;

import com.velebit.anippe.client.Desktop;
import com.velebit.anippe.client.search.SearchOutline;
import com.velebit.anippe.client.settings.SettingsOutline;
import com.velebit.anippe.client.work.WorkOutline;
import com.velebit.anippe.shared.Icons;
import org.eclipse.scout.rt.client.session.ClientSessionProvider;
import org.eclipse.scout.rt.client.ui.action.keystroke.IKeyStroke;
import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.client.ui.action.menu.IMenu;
import org.eclipse.scout.rt.client.ui.desktop.AbstractDesktop;
import org.eclipse.scout.rt.client.ui.desktop.notification.NativeNotificationDefaults;
import org.eclipse.scout.rt.client.ui.desktop.outline.AbstractOutlineViewButton;
import org.eclipse.scout.rt.client.ui.desktop.outline.IOutline;
import org.eclipse.scout.rt.client.ui.form.ScoutInfoForm;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.platform.util.CollectionUtility;
import org.eclipse.scout.rt.platform.util.StringUtility;
import org.eclipse.scout.rt.security.IAccessControlService;

import java.beans.PropertyChangeEvent;
import java.util.List;

public class AdministrationDesktop extends AbstractDesktop {

    @Override
    protected String getConfiguredTitle() {
        return TEXTS.get("ApplicationTitle");
    }

    @Override
    protected String getConfiguredLogoId() {
        return Icons.AppLogo;
    }

    @Override
    protected NativeNotificationDefaults getConfiguredNativeNotificationDefaults() {
        return super.getConfiguredNativeNotificationDefaults().withIconId("application_logo.png");
    }

    @Override
    protected List<Class<? extends IOutline>> getConfiguredOutlines() {
        return CollectionUtility.<Class<? extends IOutline>>arrayList(
                AdministrationOutline.class);
    }

    @Override
    protected void execDefaultView() {
        selectFirstVisibleOutline();
    }

    protected void selectFirstVisibleOutline() {
        for (IOutline outline : getAvailableOutlines()) {
            if (outline.isEnabled() && outline.isVisible()) {
                setOutline(outline.getClass());
                return;
            }
        }
    }


    @Order(1000)
    public class UserProfileMenu extends AbstractMenu {

        @Override
        protected String getConfiguredKeyStroke() {
            return IKeyStroke.F10;
        }

        @Override
        protected String getConfiguredIconId() {
            return Icons.PersonSolid;
        }

        @Override
        protected String getConfiguredText() {
            String userId = BEANS.get(IAccessControlService.class).getUserIdOfCurrentSubject();
            return StringUtility.uppercaseFirst(userId);
        }

        @Order(3000)
        public class LogoutMenu extends AbstractMenu {

            @Override
            protected String getConfiguredText() {
                return TEXTS.get("Logout");
            }

            @Override
            protected void execAction() {
                ClientSessionProvider.currentSession().stop();
            }
        }
    }

    @Order(1000)
    public class AdministrationOutlineViewButton extends AbstractOutlineViewButton {

        public AdministrationOutlineViewButton() {
            this(AdministrationOutline.class);
        }

        protected AdministrationOutlineViewButton(Class<? extends AdministrationOutline> outlineClass) {
            super(AdministrationDesktop.this, outlineClass);
        }

        @Override
        protected String getConfiguredKeyStroke() {
            return IKeyStroke.F2;
        }
    }


}
