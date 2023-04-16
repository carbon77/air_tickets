package com.zakat.air_tickets.components;

import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.validator.EmailValidator;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

public class RegisterForm extends FormLayout {
    private final TextField username;
    private final EmailField email;
    private final PasswordField password;
    private final PasswordField passwordConfirm;

    private final Binder<RegisterFormData> binder;

    public RegisterForm() {
        username = new TextField("Username");
        email = new EmailField("Email");
        password = new PasswordField("Password");
        passwordConfirm = new PasswordField("Password confirmation");

        binder = new BeanValidationBinder<>(RegisterFormData.class);

        binder.forField(username)
                .withValidator(username -> username.length() >= 4, "Username length must be minimum 4 characters")
                .asRequired()
                .bind("username");
        binder.forField(email)
                .withValidator(new EmailValidator("Invalid email"))
                .asRequired()
                .bind("email");
        binder.forField(password)
                .withValidator(password -> password.length() >= 4, "Password length must be minimum 4 characters")
                .asRequired()
                .bind("password");
        binder.forField(passwordConfirm)
                .withValidator(pc -> pc.equals(password.getValue()), "Passwords don't match")
                .bind("passwordConfirm");

        setResponsiveSteps(
                new ResponsiveStep("0", 1),
                new ResponsiveStep("500px", 2)
        );

        setColspan(password, 2);
        setColspan(passwordConfirm, 2);

        add(username, email, password, passwordConfirm);
    }

    public void setData(RegisterFormData data) {
        binder.setBean(data);
    }

    public RegisterFormData getData() {
        return binder.getBean();
    }

    @Data
    public static class RegisterFormData {
        private String username;
        private String email;
        private String password;
        private String passwordConfirm;
    }
}
