package com.zakat.air_tickets.view;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.zakat.air_tickets.components.HeaderAndNavbarLayout;
import com.zakat.air_tickets.entity.Flight;
import com.zakat.air_tickets.repository.FlightRepository;
import jakarta.annotation.security.PermitAll;

import java.sql.Timestamp;
import java.time.LocalTime;
import java.util.List;

@Route(value = "/search", layout = HeaderAndNavbarLayout.class)
@RouteAlias("search")
@PermitAll
public class SearchView extends VerticalLayout {
    private TextField departureCity;
    private TextField arrivalCity;
    private DatePicker departureDate;
    private DatePicker arrivalDate;
    private Button submitBtn;
    private List<Flight> flights;
    private Grid<Flight> grid;
    private FlightRepository flightRepository;

    public SearchView(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
        H2 title = new H2("Tickets Search");

        FormLayout form = generateForm();
        submitBtn = new Button("Find tickets");
        submitBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        submitBtn.addClickListener(this::onSubmit);

        grid = new Grid<>();
        grid.addColumn(Flight::getDepartureCity).setHeader("Departure city");
        grid.addColumn(Flight::getArrivalCity).setHeader("Arrival city");
        grid.addColumn(Flight::getDepartureTime).setHeader("Departure time");
        grid.addColumn(Flight::getArrivalTime).setHeader("Arrival time");

        add(title, form, submitBtn, grid);
    }

    private void onSubmit(ClickEvent<Button> buttonClickEvent) {
        Timestamp arrivalTimestamp = null;
        Timestamp departureTimestamp = null;

        if (departureDate.getValue() != null) {
            arrivalTimestamp = Timestamp.valueOf(departureDate.getValue().atTime(LocalTime.MIDNIGHT));
        }

        if (arrivalDate.getValue() != null) {
            departureTimestamp = Timestamp.valueOf(arrivalDate.getValue().atTime(LocalTime.MIDNIGHT));
        }


        flights = flightRepository.search(
                departureCity.getValue(),
                arrivalCity.getValue(),
                arrivalTimestamp,
                departureTimestamp
        );

        grid.setItems(flights);
    }

    private FormLayout generateForm() {
        FormLayout formLayout = new FormLayout();
        formLayout.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0", 1),
                new FormLayout.ResponsiveStep("500px", 2),
                new FormLayout.ResponsiveStep("1000px", 4)
        );

        departureCity = new TextField();
        departureCity.setPlaceholder("From");

        arrivalCity = new TextField();
        arrivalCity.setPlaceholder("To");

        departureDate = new DatePicker();
        departureDate.setPlaceholder("When");
        departureDate.addValueChangeListener(e -> {
            arrivalDate.setMin(e.getValue());
        });

        arrivalDate = new DatePicker();
        arrivalDate.setPlaceholder("Back");
        arrivalDate.addValueChangeListener(e -> {
            departureDate.setMax(e.getValue());
        });

        formLayout.add(departureCity, arrivalCity, departureDate, arrivalDate);

        return formLayout;
    }
}
