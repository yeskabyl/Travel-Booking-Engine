package travel.factory;

import travel.models.Booking;
import java.util.UUID;

public abstract class BookingCreator {
    public final Booking createBooking(String description) {
        return createProduct(description);
    }
    protected abstract Booking createProduct(String description);
    protected String nextId() { return "B-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase(); }
}
