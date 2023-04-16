package com.zakat.air_tickets.components;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;

public class HeaderAndNavbarLayout extends HeaderLayout {

    public HeaderAndNavbarLayout() {
        DrawerToggle drawerToggle = new DrawerToggle();

        title.getStyle()
                .set("font-size", "var(--lumo-font-size-l)")
                .set("margin", "0");

        addToDrawer(getTabs());
        addToNavbar(drawerToggle, title);

        setPrimarySection(Section.DRAWER);
    }

    private Component getTabs() {
        Tabs tabs = new Tabs();
        tabs.setOrientation(Tabs.Orientation.VERTICAL);
        Tab profile = new Tab(VaadinIcon.USER.create(), new Span("Profile"));
        Tab home = new Tab(VaadinIcon.HOME.create(), new Span("Home"));
        Tab logout = new Tab(VaadinIcon.SIGN_OUT.create(), new Span("Log out"));

        tabs.add(profile, home, logout);

        return tabs;
    }
}
