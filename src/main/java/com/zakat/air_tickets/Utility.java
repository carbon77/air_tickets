package com.zakat.air_tickets;

import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.theme.lumo.LumoUtility;
import com.zakat.air_tickets.entity.Flight;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class Utility {
    public static String timestampToString(Timestamp timestamp) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss z");
        return dateFormat.format(timestamp);
    }

    public static ComponentRenderer<HorizontalLayout, Flight> getRenderer(String source) {
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
}
