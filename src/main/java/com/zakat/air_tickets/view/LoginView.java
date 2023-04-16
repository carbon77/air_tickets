package com.zakat.air_tickets.view;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.zakat.air_tickets.components.HeaderLayout;
import com.zakat.air_tickets.components.LoginForm;
import com.zakat.air_tickets.entity.User;

@Route("/login")
@PageTitle("SkyWing | Login")
@AnonymousAllowed
public class LoginView extends HeaderLayout {
    private final LoginForm loginForm;

    public LoginView() {
        VerticalLayout layout = new VerticalLayout();
        layout.setWidth("30%");
        layout.setAlignItems(FlexComponent.Alignment.CENTER);
        layout.getStyle().set("margin", "0 auto");

        loginForm = new LoginForm();
        loginForm.setData(new User());

        Button submit = new Button("Sign in");
        submit.setIcon(new Icon(VaadinIcon.SIGN_IN));
        submit.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        submit.addClickListener(this::onSubmit);

        layout.add(
                new H2("Login"),
                loginForm,
                submit
        );

        setDrawerOpened(false);
        setContent(layout);
    }

    private void onSubmit(ClickEvent<Button> buttonClickEvent) {
        System.out.println(loginForm.getData());
    }
}