package travel.models;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public record CarRental(String id, String company, String model, String city,
                        LocalDate pickupDate, LocalDate returnDate, BigDecimal pricePerDay) {
    public CarRental {
        if (blank(id) || blank(company) || blank(model) || blank(city)
                || pickupDate == null || returnDate == null || !returnDate.isAfter(pickupDate)
                || pricePerDay == null || pricePerDay.signum() < 0) {
            throw new IllegalArgumentException("Invalid car rental data");
        }
    }
    private static boolean blank(String value) { return value == null || value.isBlank(); }
    public long rentalDays() { return ChronoUnit.DAYS.between(pickupDate, returnDate); }
    public BigDecimal totalPrice() { return pricePerDay.multiply(BigDecimal.valueOf(rentalDays())); }
    @Override public String toString() {
        return id + " | " + company + " " + model + " | " + city
                + " | " + rentalDays() + " days | $" + totalPrice();
    }
}
