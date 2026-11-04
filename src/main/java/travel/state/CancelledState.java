package travel.state;

import travel.models.Booking;

public class CancelledState implements BookingState {
    @Override public String name() { return "CANCELLED"; }
    @Override public void confirm(Booking booking) {
        throw new IllegalStateException("A cancelled booking cannot be confirmed");
    }
    @Override public void cancel(Booking booking) {
        throw new IllegalStateException("Booking is already cancelled");
    }
}
