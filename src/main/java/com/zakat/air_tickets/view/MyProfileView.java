package com.zakat.air_tickets.view;

import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.zakat.air_tickets.components.HeaderAndNavbarLayout;
import jakarta.annotation.security.PermitAll;

@Route(value = "/profile", layout = HeaderAndNavbarLayout.class)
@RouteAlias("profile")
@PermitAll
public class MyProfileView extends VerticalLayout {

    public MyProfileView() {
        H2 title = new H2("My Profile");

        add(title);
    }
}
