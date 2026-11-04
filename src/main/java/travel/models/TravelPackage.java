package travel.models;

import java.math.BigDecimal;
import java.util.Objects;

public record TravelPackage(Flight flight, Hotel hotel, CarRental carRental) {
    public TravelPackage {
        Objects.requireNonNull(flight, "flight");
        Objects.requireNonNull(hotel, "hotel");
        Objects.requireNonNull(carRental, "carRental");
        if (!flight.destination().equalsIgnoreCase(hotel.city())
                || !hotel.city().equalsIgnoreCase(carRental.city())) {
            throw new IllegalArgumentException("Flight destination, hotel city and car city must match");
        }
    }
    public BigDecimal totalPrice() {
        return flight.price().add(hotel.totalPrice()).add(carRental.totalPrice());
    }
    public double hotelRating() { return hotel.rating(); }
    @Override public String toString() {
        return "TRIP PACKAGE\n  Flight: " + flight + "\n  Hotel: " + hotel
                + "\n  Car: " + carRental + "\n  TOTAL: $" + totalPrice();
    }
}
