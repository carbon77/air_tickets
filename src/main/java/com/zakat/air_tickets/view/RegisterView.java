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
import com.zakat.air_tickets.components.RegisterForm;

@Route("/register")
@PageTitle("SkyWing | Register")
@AnonymousAllowed
public class RegisterView extends HeaderLayout {
    private final RegisterForm registerForm;
    private final Button submitButton;

    public RegisterView() {
        VerticalLayout layout = new VerticalLayout();
        layout.setWidth("40%");
        layout.setAlignItems(FlexComponent.Alignment.CENTER);
        layout.getStyle().set("margin", "0 auto");

        registerForm = new RegisterForm();
        registerForm.setData(new RegisterForm.RegisterFormData());

        submitButton = new Button("Register");
        submitButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        submitButton.setIcon(new Icon(VaadinIcon.SIGN_IN));
        submitButton.setWidthFull();
        submitButton.addClickListener(this::onSubmitClick);

        layout.add(new H2("Register"), registerForm, submitButton);
        setContent(layout);
    }

    public void onSubmitClick(ClickEvent<Button> buttonClickEvent) {
        System.out.println(registerForm.getData());
    }
}
