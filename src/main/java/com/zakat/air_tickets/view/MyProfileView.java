package com.zakat.air_tickets.view;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.zakat.air_tickets.components.EditProfileForm;
import com.zakat.air_tickets.components.HeaderAndNavbarLayout;
import com.zakat.air_tickets.entity.User;
import com.zakat.air_tickets.repository.UserRepository;
import com.zakat.air_tickets.security.SecurityService;
import com.zakat.air_tickets.security.UserPrincipal;
import jakarta.annotation.security.PermitAll;

@Route(value = "/profile", layout = HeaderAndNavbarLayout.class)
@RouteAlias("profile")
@PageTitle("SkyWing | Profile")
@PermitAll
public class MyProfileView extends VerticalLayout {
    private final UserRepository userRepository;
    private final SecurityService securityService;
    private final User user;

    private EditProfileForm editProfileForm;
    private Button submitBtn;
    private Button logoutBtn;
    private Dialog logoutDialog;

    public MyProfileView(UserRepository userRepository, SecurityService securityService) {
        this.userRepository = userRepository;
        this.securityService = securityService;

        setWidth("50%");

        UserPrincipal userPrincipal = (UserPrincipal) securityService.getAuthenticatedUser();
        user = userPrincipal.getUser();

        editProfileForm = new EditProfileForm();
        editProfileForm.setData(user);

        submitBtn = new Button("Submit");
        submitBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        submitBtn.setIcon(VaadinIcon.EDIT.create());
        submitBtn.addClickListener(this::onSubmitClick);

        logoutBtn = new Button("Logout");
        logoutBtn.addThemeVariants(ButtonVariant.LUMO_ERROR);
        logoutBtn.setIcon(VaadinIcon.SIGN_OUT.create());
        logoutBtn.addClickListener(e -> {
            logoutDialog.open();
        });

        logoutDialog = getDialog();

        add(
                logoutDialog,
                new H2("My Profile"),
                editProfileForm,
                submitBtn,
                logoutBtn
        );
    }

    private void onSubmitClick(ClickEvent<Button> buttonClickEvent) {
        EditProfileForm.EditProfileFormData data = editProfileForm.getData();

        user.setUsername(data.getUsername());
        user.setEmail(data.getEmail());

        userRepository.save(user);

        Notification.show("User has been changed");
    }

    private Dialog getDialog() {
        Dialog dialog = new Dialog();
        dialog.setHeaderTitle("Logout");
        dialog.add("Are you sure you want to logout?");

        Button cancelBtn = new Button("Cancel", e -> dialog.close());
        cancelBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        cancelBtn.getStyle().set("margin-right", "auto");
        dialog.getFooter().add(cancelBtn);

        Button logoutBtn = new Button("Logout", e -> securityService.logout());
        logoutBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_ERROR);
        dialog.getFooter().add(logoutBtn);

        return dialog;
    }
}
