package travel.factory;

import travel.models.Booking;

public class FlightBookingCreator extends BookingCreator {
    @Override
    protected Booking createProduct(String description) {
        return new Booking(nextId(), "Flight booking: " + description);
    }
}
