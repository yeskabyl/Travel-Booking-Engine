package travel.models;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public record Hotel(String id, String name, String city, LocalDate checkIn,
                    LocalDate checkOut, BigDecimal pricePerNight, double rating) {
    public Hotel {
        if (blank(id) || blank(name) || blank(city) || checkIn == null || checkOut == null
                || !checkOut.isAfter(checkIn) || pricePerNight == null
                || pricePerNight.signum() < 0 || rating < 0 || rating > 5) {
            throw new IllegalArgumentException("Invalid hotel data");
        }
    }
    private static boolean blank(String value) { return value == null || value.isBlank(); }
    public long nights() { return ChronoUnit.DAYS.between(checkIn, checkOut); }
    public BigDecimal totalPrice() { return pricePerNight.multiply(BigDecimal.valueOf(nights())); }
    @Override public String toString() {
        return id + " | " + name + " | " + city + " | rating " + rating
                + " | " + nights() + " nights | $" + totalPrice();
    }
}
