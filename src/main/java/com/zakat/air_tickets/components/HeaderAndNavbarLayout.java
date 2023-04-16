package com.zakat.air_tickets.components;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.theme.lumo.LumoUtility;
import com.zakat.air_tickets.security.SecurityService;
import org.springframework.security.core.userdetails.UserDetails;

public class HeaderAndNavbarLayout extends AppLayout {
    private final SecurityService securityService;
    private final UserDetails user;

    public HeaderAndNavbarLayout(SecurityService securityService) {
        this.securityService = securityService;
        user = securityService.getAuthenticatedUser();

        DrawerToggle drawerToggle = new DrawerToggle();
        H1 title = new H1("SkyWing");
        title.addClassNames(
                LumoUtility.FontSize.LARGE,
                LumoUtility.Margin.NONE
        );

        H2 username = new H2(user.getUsername());
        username.addClassNames(
                LumoUtility.Margin.Horizontal.AUTO,
                LumoUtility.Margin.Vertical.MEDIUM
        );

        Tabs tabs = getTabs();

        addToDrawer(username, tabs);
        addToNavbar(drawerToggle, title);

        setPrimarySection(Section.DRAWER);
    }

    private Tabs getTabs() {
        Tabs tabs = new Tabs();
        tabs.setOrientation(Tabs.Orientation.VERTICAL);
        Tab profile = new Tab(VaadinIcon.USER.create(), new Span("Profile"));
        Tab home = new Tab(VaadinIcon.HOME.create(), new Span("Home"));
        Tab logout = new Tab(VaadinIcon.SIGN_OUT.create(), new Span("Log out"));

        tabs.addSelectedChangeListener(e -> {
            if (e.getSelectedTab() == logout) {
                securityService.logout();
            }
        });

        tabs.add(home, profile, logout);

        return tabs;
    }
}
