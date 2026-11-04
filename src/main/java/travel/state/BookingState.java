package travel.state;

import travel.models.Booking;

public interface BookingState {
    String name();
    void confirm(Booking booking);
    void cancel(Booking booking);
}
