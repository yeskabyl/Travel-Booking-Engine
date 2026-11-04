package travel.factory;

import travel.models.Booking;

public class HotelBookingCreator extends BookingCreator {
    @Override
    protected Booking createProduct(String description) {
        return new Booking(nextId(), "Hotel booking: " + description);
    }
}
