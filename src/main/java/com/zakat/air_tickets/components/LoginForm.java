package com.zakat.air_tickets.components;

import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.zakat.air_tickets.entity.User;

public class LoginForm extends FormLayout {
    private Binder<User> binder;
    private TextField username;
    private PasswordField password;

    public LoginForm() {
        username = new TextField("Username");
        password = new PasswordField("Password");

        binder = new BeanValidationBinder<>(User.class);

        binder.forField(username)
                .asRequired("This field is required")
                .bind("username");

        binder.forField(password)
                .asRequired("This field is required")
                .bind("password");

        setResponsiveSteps(
                new ResponsiveStep("0", 1)
        );

        add(username, password);
    }

    public void setData(User data) {
        binder.setBean(data);
    }

    public User getData() {
        return binder.getBean();
    }
}
