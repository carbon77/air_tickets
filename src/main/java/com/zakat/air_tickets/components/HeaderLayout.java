package com.zakat.air_tickets.components;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.html.H1;

public class HeaderLayout extends AppLayout {
    protected final H1 title;

    public HeaderLayout() {
        title = new H1("SkyWing");
        title.getStyle()
                .set("font-size", "var(--lumo-font-size-l)")
                .set("margin-left", "10%");

        addToNavbar(title);
    }
}
