package com.zakat.air_tickets.components;

import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.validator.EmailValidator;
import com.zakat.air_tickets.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

public class EditProfileForm extends FormLayout {

    private Binder<EditProfileFormData> binder;
    private TextField username;
    private TextField email;

    public EditProfileForm() {
        username = new TextField("Username");
        email = new TextField("Email");

        binder = new BeanValidationBinder<>(EditProfileFormData.class);

        binder.forField(username)
                .asRequired("This field can't be empty")
                .bind("username");

        binder.forField(email)
                .asRequired("This field can't be empty")
                .withValidator(new EmailValidator("Invalid email"))
                .bind("email");

        setResponsiveSteps(
                new ResponsiveStep("0", 1)
        );

        add(
                new H3("Edit profile"),
                username,
                email
        );
    }

    public void setData(User user) {
        binder.setBean(
                EditProfileFormData.builder()
                        .username(user.getUsername())
                        .email(user.getEmail())
                        .build()
        );
    }

    public EditProfileFormData getData() {
        return binder.getBean();
    }

    @Data
    @Builder
    @AllArgsConstructor
    public static class EditProfileFormData {
        private String username;
        private String email;
    }
}
