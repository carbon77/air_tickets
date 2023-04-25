package com.zakat.air_tickets.view;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.*;
import com.vaadin.flow.theme.lumo.LumoUtility;
import com.zakat.air_tickets.Utility;
import com.zakat.air_tickets.components.HeaderAndNavbarLayout;
import com.zakat.air_tickets.entity.Booking;
import com.zakat.air_tickets.entity.Flight;
import com.zakat.air_tickets.entity.User;
import com.zakat.air_tickets.repository.BookingRepository;
import com.zakat.air_tickets.repository.FlightRepository;
import com.zakat.air_tickets.security.SecurityService;
import com.zakat.air_tickets.security.UserPrincipal;
import jakarta.annotation.security.PermitAll;
import org.springframework.security.core.userdetails.UserDetails;

@Route(value = "/flight", layout = HeaderAndNavbarLayout.class)
@RouteAlias("flight")
@PageTitle("SkyWing | Flight")
@PermitAll
public class FlightView extends VerticalLayout implements HasUrlParameter<Long> {
    private FlightRepository flightRepository;
    private SecurityService securityService;
    private BookingRepository bookingRepository;
    private Flight flight;
    private H2 title = new H2();
    private Grid<Flight> grid = new Grid<>();

    public FlightView(
            FlightRepository flightRepository,
            SecurityService securityService,
            BookingRepository bookingRepository
    ) {
        this.flightRepository = flightRepository;
        this.securityService = securityService;
        this.bookingRepository = bookingRepository;

        grid.addColumn(getRenderer("departure")).setHeader("Departure");
        grid.addColumn(getRenderer("arrival")).setHeader("Arrival");
        grid.addColumn(f -> f.getAirline().getName()).setAutoWidth(true).setHeader("Airline");
        grid.addColumn(Flight::getPrice).setAutoWidth(true).setHeader("Price, $");
        grid.setAllRowsVisible(true);
        grid.getStyle().set("width", "75%");
        grid.addThemeVariants(GridVariant.LUMO_WRAP_CELL_CONTENT);

        Button buyBtn = new Button("Buy ticket");
        buyBtn.setIcon(VaadinIcon.CART.create());
        buyBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buyBtn.addClickListener(this::onBuy);

        add(title, grid, buyBtn);
    }

    private void onBuy(ClickEvent<Button> e) {
        UserPrincipal userPrincipal = (UserPrincipal) securityService.getAuthenticatedUser();
        User user = userPrincipal.getUser();

        Booking booking = bookingRepository.findByUserAndFlight(user, flight);

        if (booking != null) {
            Notification.show("You have already bought this ticket");
            return;
        }

        booking = new Booking();
        booking.setUser(user);
        booking.setFlight(flight);
        bookingRepository.save(booking);

        Notification.show("Ticket has been bought");
    }

    private ComponentRenderer<HorizontalLayout, Flight> getRenderer(String source) {
        return new ComponentRenderer<>(HorizontalLayout::new, (horizontalLayout, flight) -> {
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
                city.setText(flight.getDepartureCity());
                date.setText(Utility.timestampToString(flight.getDepartureTime()));
            } else if (source.equals("arrival")) {
                city.setText(flight.getArrivalCity());
                date.setText(Utility.timestampToString(flight.getArrivalTime()));
            }

            layout.add(city, date);
            horizontalLayout.add(layout);
        });
    }

    @Override
    public void setParameter(BeforeEvent beforeEvent, Long flightId) {
        flight = flightRepository.findById(flightId).orElseThrow();
        title.setText(String.format("Flight: %s -> %s", flight.getDepartureCity(), flight.getArrivalCity()));
        grid.setItems(flight);
    }
}
