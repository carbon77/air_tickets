package com.zakat.air_tickets.view;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.router.*;
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

import java.sql.Timestamp;

@Route(value = "/flight", layout = HeaderAndNavbarLayout.class)
@RouteAlias("flight")
@PageTitle("SkyWing | Flight")
@PermitAll
public class FlightView extends VerticalLayout implements HasUrlParameter<Long> {
    private Button buyBtn;
    private IntegerField amountField;
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

        grid.addColumn(Utility.getRenderer("departure")).setHeader("Departure");
        grid.addColumn(Utility.getRenderer("arrival")).setHeader("Arrival");
        grid.addColumn(f -> f.getAirline().getName()).setAutoWidth(true).setHeader("Airline");
        grid.addColumn(Flight::getPrice).setAutoWidth(true).setHeader("Price, $");
        grid.setAllRowsVisible(true);
        grid.getStyle().set("width", "75%");
        grid.addThemeVariants(GridVariant.LUMO_WRAP_CELL_CONTENT);

        HorizontalLayout submitLayout = new HorizontalLayout();
        submitLayout.setAlignItems(Alignment.END);

        buyBtn = new Button("Buy ticket");
        buyBtn.setIcon(VaadinIcon.CART.create());
        buyBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buyBtn.addClickListener(this::onBuy);

        amountField = new IntegerField();
        amountField.setLabel("Amount");
        amountField.setStepButtonsVisible(true);
        amountField.setMin(1);
        amountField.setValue(1);
        amountField.addValueChangeListener(e -> {
            buyBtn.setText(String.format("Buy: %.2f$", flight.getPrice() * amountField.getValue()));
        });

        submitLayout.add(amountField, buyBtn);
        add(title, grid, submitLayout);
    }

    private void onBuy(ClickEvent<Button> e) {
        UserPrincipal userPrincipal = (UserPrincipal) securityService.getAuthenticatedUser();
        User user = userPrincipal.getUser();

        Booking booking = bookingRepository.findByUserAndFlight(user, flight);

        if (booking != null) {
            booking.setAmount(booking.getAmount() + amountField.getValue());
            booking.setPrice(booking.getAmount() * flight.getPrice());
        } else {
            booking = Booking.builder()
                    .user(user)
                    .flight(flight)
                    .amount(amountField.getValue())
                    .price(amountField.getValue() * flight.getPrice())
                    .createdAt(new Timestamp(System.currentTimeMillis()))
                    .build();
        }

        bookingRepository.save(booking);
        Notification.show("Tickets have been bought");
    }

    @Override
    public void setParameter(BeforeEvent beforeEvent, Long flightId) {
        flight = flightRepository.findById(flightId).orElseThrow();
        title.setText(String.format("Flight: %s -> %s", flight.getDepartureCity(), flight.getArrivalCity()));
        grid.setItems(flight);
        buyBtn.setText(String.format("Buy: %s$", flight.getPrice()));
    }
}
