package com.zakat.air_tickets.view;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.zakat.air_tickets.components.HeaderAndNavbarLayout;
import jakarta.annotation.security.PermitAll;

@Route(value = "/bookings", layout = HeaderAndNavbarLayout.class)
@RouteAlias("bookings")
@PermitAll
public class BookingsView extends VerticalLayout {
    public BookingsView() {
        H1 title = new H1("My Bookings");
        add(title);
    }
}
