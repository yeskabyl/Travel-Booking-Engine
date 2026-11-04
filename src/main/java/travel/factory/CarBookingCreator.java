package travel.factory;

import travel.models.Booking;

public class CarBookingCreator extends BookingCreator {
    @Override
    protected Booking createProduct(String description) {
        return new Booking(nextId(), "Car rental booking: " + description);
    }
}
