package com.zakat.air_tickets.view;

import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.zakat.air_tickets.components.HeaderLayout;

@Route(value = "/login", layout = HeaderLayout.class)
@RouteAlias("login")
@PageTitle("SkyWing | Login")
@AnonymousAllowed
public class LoginView extends VerticalLayout {
    private final LoginForm loginForm;

    public LoginView() {
        HorizontalLayout layout = new HorizontalLayout();
        layout.setWidthFull();
        layout.setJustifyContentMode(JustifyContentMode.CENTER);

        loginForm = new LoginForm();
        loginForm.setAction("login");
        loginForm.setForgotPasswordButtonVisible(false);

        layout.add(loginForm);

        add(layout);
    }
}
