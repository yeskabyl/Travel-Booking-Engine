package travel.models;

import java.math.BigDecimal;
import java.time.LocalDate;

public record Flight(String id, String airline, String origin, String destination,
                     LocalDate departureDate, double durationHours, BigDecimal price) {
    public Flight {
        if (blank(id) || blank(airline) || blank(origin) || blank(destination)
                || departureDate == null || durationHours <= 0
                || price == null || price.signum() < 0) {
            throw new IllegalArgumentException("Invalid flight data");
        }
    }
    private static boolean blank(String value) { return value == null || value.isBlank(); }
    @Override public String toString() {
        return id + " | " + airline + " | " + origin + " -> " + destination
                + " | " + departureDate + " | " + durationHours + "h | $" + price;
    }
}
