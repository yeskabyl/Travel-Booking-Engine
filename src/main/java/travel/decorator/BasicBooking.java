package travel.decorator;

public class BasicBooking implements BookingComponent {

    private final String description;
    private final double price;

    public BasicBooking() {
        this("Basic Flight Booking", 300.0);
    }

    public BasicBooking(String description, double price) {
        if (description == null || description.isBlank()) {
            throw new IllegalArgumentException("Description is required");
        }
        if (price < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }
        this.description = description;
        this.price = price;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public double getPrice() {
        return price;
    }
}