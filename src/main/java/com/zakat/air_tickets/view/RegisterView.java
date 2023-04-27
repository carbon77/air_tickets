package com.zakat.air_tickets.view;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.zakat.air_tickets.components.HeaderLayout;
import com.zakat.air_tickets.components.RegisterForm;
import com.zakat.air_tickets.security.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "/register", layout = HeaderLayout.class)
@RouteAlias("register")
@PageTitle("SkyWing | Register")
@AnonymousAllowed
public class RegisterView extends VerticalLayout {
    private final RegisterForm registerForm;
    private final SecurityService securityService;

    public RegisterView(@Autowired SecurityService securityService) {
        this.securityService = securityService;
        VerticalLayout layout = new VerticalLayout();
        layout.setWidth("40%");
        layout.setAlignItems(Alignment.CENTER);

        setAlignItems(Alignment.CENTER);

        registerForm = new RegisterForm();
        registerForm.setData(new RegisterForm.RegisterFormData());

        Button submitBtn = new Button("Register");
        submitBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        submitBtn.setIcon(VaadinIcon.SIGN_IN.create());
        submitBtn.addClickListener(this::onSubmitClick);

        layout.setAlignSelf(Alignment.START, submitBtn);
        layout.add(new H2("Register"), registerForm, submitBtn);
        add(layout);
    }

    public void onSubmitClick(ClickEvent<Button> buttonClickEvent) {
        RegisterForm.RegisterFormData data = registerForm.getData();
        securityService.createUser(data.getUsername(), data.getEmail(), data.getPassword());
        getUI().ifPresent(ui -> ui.navigate("login"));
    }
}
