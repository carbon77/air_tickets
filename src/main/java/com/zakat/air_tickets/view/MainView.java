package com.zakat.air_tickets.view;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.zakat.air_tickets.components.HeaderAndNavbarLayout;
import com.zakat.air_tickets.security.SecurityService;
import jakarta.annotation.security.PermitAll;

@Route(layout = HeaderAndNavbarLayout.class)
@RouteAlias("main")
@PageTitle("SkyWing | Home")
@PermitAll
public class MainView extends VerticalLayout {
    private SecurityService securityService;

    public MainView(SecurityService securityService) {
        this.securityService = securityService;
        H1 greeting = new H1();
        greeting.setText("Welcome to SkyWing!");

        Paragraph username = new Paragraph();
        username.setText(securityService.getAuthenticatedUser().getUsername());

        add(greeting, username);
    }
}
