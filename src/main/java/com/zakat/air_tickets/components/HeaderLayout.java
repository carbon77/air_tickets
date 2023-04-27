package com.zakat.air_tickets.components;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.theme.lumo.LumoUtility;

public class HeaderLayout extends AppLayout {

    public HeaderLayout() {
        HorizontalLayout layout = new HorizontalLayout();
        layout.setJustifyContentMode(FlexComponent.JustifyContentMode.AROUND);
        layout.setAlignItems(FlexComponent.Alignment.CENTER);
        layout.setWidthFull();

        H1 title = new H1("SkyWing");
        title.addClassNames(
                LumoUtility.FontSize.LARGE
        );

        Button registerBtn = new Button("Register");
        registerBtn.addClickListener(e -> this.getUI().ifPresent(ui -> ui.navigate("register")));

        Button loginBtn = new Button("Login");
        loginBtn.addClickListener(e -> this.getUI().ifPresent(ui -> ui.navigate("login")));

        layout.add(title, new HorizontalLayout(registerBtn, loginBtn));

        addToNavbar(layout);
    }
}
