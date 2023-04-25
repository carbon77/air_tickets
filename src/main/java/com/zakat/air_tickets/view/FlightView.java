package com.zakat.air_tickets.view;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.*;
import com.zakat.air_tickets.Utility;
import com.zakat.air_tickets.components.HeaderAndNavbarLayout;
import com.zakat.air_tickets.entity.Flight;
import com.zakat.air_tickets.repository.FlightRepository;
import jakarta.annotation.security.PermitAll;

@Route(value = "/flight", layout = HeaderAndNavbarLayout.class)
@RouteAlias("flight")
@PageTitle("SkyWing | Flight")
@PermitAll
public class FlightView extends VerticalLayout implements HasUrlParameter<Long> {
    private FlightRepository flightRepository;
    private Flight flight;
    private H2 title = new H2();
    private TextField price;
    private Grid<Flight> grid = new Grid<>();

    public FlightView(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;

        grid.addColumn(getRenderer("departure")).setHeader("Departure");
        grid.addColumn(getRenderer("arrival")).setHeader("Arrival");
        grid.addColumn(f -> f.getAirline().getName()).setHeader("Airline");
        grid.setAllRowsVisible(true);

        price = new TextField();
        price.setReadOnly(true);
        price.setLabel("Price");
        price.setPrefixComponent(new Span("$"));

        add(title, grid, price);
    }

    private ComponentRenderer<VerticalLayout, Flight> getRenderer(String source) {
        return new ComponentRenderer<>(VerticalLayout::new, (layout, flight) -> {
            Span city = new Span();
            Span date = new Span();

            if (source.equals("departure")) {
                city.setText(flight.getDepartureCity());
                date.setText(Utility.timestampToString(flight.getDepartureTime()));
            } else if (source.equals("arrival")) {
                city.setText(flight.getArrivalCity());
                date.setText(Utility.timestampToString(flight.getArrivalTime()));
            }


            layout.add(city, date);
        });
    }

    @Override
    public void setParameter(BeforeEvent beforeEvent, Long flightId) {
        flight = flightRepository.findById(flightId).orElseThrow();

        price.setValue(flight.getPrice().toString());

        title.setText(String.format("Flight: %s -> %s", flight.getDepartureCity(), flight.getArrivalCity()));

        grid.setItems(flight);
    }
}
