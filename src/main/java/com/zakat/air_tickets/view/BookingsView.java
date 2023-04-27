package com.zakat.air_tickets.view;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.theme.lumo.LumoUtility;
import com.zakat.air_tickets.Utility;
import com.zakat.air_tickets.components.HeaderAndNavbarLayout;
import com.zakat.air_tickets.entity.Booking;
import com.zakat.air_tickets.entity.User;
import com.zakat.air_tickets.repository.BookingRepository;
import com.zakat.air_tickets.security.SecurityService;
import com.zakat.air_tickets.security.UserPrincipal;
import jakarta.annotation.security.PermitAll;

import java.util.List;

@Route(value = "/bookings", layout = HeaderAndNavbarLayout.class)
@RouteAlias("bookings")
@PageTitle("SkyWing | Bookings")
@PermitAll
public class BookingsView extends VerticalLayout {
    private SecurityService securityService;
    private BookingRepository bookingRepository;
    private User user;
    private List<Booking> bookings;
    private Grid<Booking> grid;

    public BookingsView(SecurityService securityService, BookingRepository bookingRepository) {
        this.securityService = securityService;
        this.bookingRepository = bookingRepository;
        UserPrincipal userPrincipal = (UserPrincipal) this.securityService.getAuthenticatedUser();
        user = userPrincipal.getUser();
        bookings = this.bookingRepository.findAllByUser(user);

        grid = new Grid<>();
        grid.setAllRowsVisible(true);
        grid.addColumn(getRenderer("departure")).setHeader("Departure");
        grid.addColumn(getRenderer("arrival")).setHeader("Arrival");

        grid.setItems(bookings);

        H2 title = new H2("My Bookings");
        add(title, grid);
    }

    public static ComponentRenderer<HorizontalLayout, Booking> getRenderer(String source) {
        return new ComponentRenderer<>(HorizontalLayout::new, (horizontalLayout, booking) -> {
            VerticalLayout layout = new VerticalLayout();

            layout.addClassNames(
                    LumoUtility.LineHeight.MEDIUM,
                    LumoUtility.Padding.NONE,
                    LumoUtility.Gap.SMALL
            );
            Span city = new Span();
            city.addClassNames(
                    LumoUtility.FontWeight.BOLD
            );
            Span date = new Span();
            date.addClassNames(
                    LumoUtility.FontSize.SMALL,
                    LumoUtility.TextColor.SECONDARY
            );

            if (source.equals("departure")) {
                city.setText(booking.getFlight().getDepartureCity());
                date.setText(Utility.timestampToString(booking.getFlight().getDepartureTime()));
            } else if (source.equals("arrival")) {
                city.setText(booking.getFlight().getArrivalCity());
                date.setText(Utility.timestampToString(booking.getFlight().getArrivalTime()));
            }

            layout.add(city, date);
            horizontalLayout.add(layout);
        });
    }
}
