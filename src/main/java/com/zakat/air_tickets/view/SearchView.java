package com.zakat.air_tickets.view;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.zakat.air_tickets.Utility;
import com.zakat.air_tickets.components.HeaderAndNavbarLayout;
import com.zakat.air_tickets.entity.Flight;
import com.zakat.air_tickets.repository.FlightRepository;
import jakarta.annotation.security.PermitAll;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;

@Route(value = "/", layout = HeaderAndNavbarLayout.class)
@RouteAlias("search")
@PageTitle("SkyWing | Search")
@PermitAll
public class SearchView extends VerticalLayout {
    private ComboBox<String> departureCity;
    private ComboBox<String> arrivalCity;
    private DatePicker departureDate;
    private DatePicker arrivalDate;
    private Button submitBtn;
    private List<Flight> flights;
    private Set<String> departureCities;
    private Set<String> arrivalCities;
    private Grid<Flight> grid;
    private FlightRepository flightRepository;

    public SearchView(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
        H2 title = new H2("Search");

        departureCities = flightRepository.getAllDepartureCities();
        arrivalCities = flightRepository.getAllArrivalCities();

        FormLayout form = generateForm();
        submitBtn = new Button("Find tickets");
        submitBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        submitBtn.setIcon(VaadinIcon.SEARCH.create());
        submitBtn.addClickListener(this::onSubmit);

        grid = new Grid<>();
        grid.addColumn(Utility.getRenderer("departure")).setHeader("Departure");
        grid.addColumn(Utility.getRenderer("departure")).setHeader("Arrival");
        grid.addColumn(f -> f.getAirline().getName()).setHeader("Airline");
        grid.addColumn(getActionRenderer()).setHeader("Action");

        add(title, form, submitBtn, grid);
    }

    private ComponentRenderer<VerticalLayout, Flight> getActionRenderer() {
        return new ComponentRenderer<>(VerticalLayout::new, (layout, flight) -> {
            Button button = new Button("Purchase ticket");
            button.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
            button.setIcon(VaadinIcon.CART.create());
            button.addClickListener(e -> {
                getUI().ifPresent(ui -> ui.navigate(FlightView.class, flight.getId()));
            });

            layout.setPadding(false);
            layout.add(button);
        });
    }

    private void onSubmit(ClickEvent<Button> buttonClickEvent) {
        Timestamp departureTimestamp = Timestamp.valueOf(LocalDateTime.of(2000, 1, 1, 0, 0, 0, 0));
        Timestamp arrivalTimestamp = Timestamp.valueOf(LocalDateTime.of(2100, 12, 31, 0, 0, 0));

        if (departureDate.getValue() != null) {
            departureTimestamp = Timestamp.valueOf(departureDate.getValue().atTime(LocalTime.MIDNIGHT));
        }

        if (arrivalDate.getValue() != null) {
            arrivalTimestamp = Timestamp.valueOf(arrivalDate.getValue().atTime(LocalTime.MIDNIGHT));
        }

        flights = flightRepository.search(
                departureCity.getValue() == null ? "" : departureCity.getValue(),
                arrivalCity.getValue() == null ? "" : arrivalCity.getValue(),
                departureTimestamp,
                arrivalTimestamp
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

        departureCity = new ComboBox<>();
        departureCity.setPlaceholder("From");
        departureCity.setItems(departureCities);
        departureCity.setAllowCustomValue(true);
        departureCity.setClearButtonVisible(true);

        arrivalCity = new ComboBox<>();
        arrivalCity.setPlaceholder("To");
        arrivalCity.setItems(arrivalCities);
        arrivalCity.setAllowCustomValue(true);
        arrivalCity.setClearButtonVisible(true);

        departureDate = new DatePicker();
        departureDate.setPlaceholder("When");
        departureDate.addValueChangeListener(e -> {
            arrivalDate.setMin(e.getValue());
        });
        departureDate.setClearButtonVisible(true);

        arrivalDate = new DatePicker();
        arrivalDate.setPlaceholder("Back");
        arrivalDate.addValueChangeListener(e -> {
            departureDate.setMax(e.getValue());
        });
        arrivalDate.setClearButtonVisible(true);

        formLayout.add(departureCity, arrivalCity, departureDate, arrivalDate);

        return formLayout;
    }
}
