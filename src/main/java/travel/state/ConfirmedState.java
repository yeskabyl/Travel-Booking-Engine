package travel.state;

import travel.models.Booking;

public class ConfirmedState implements BookingState {
    @Override public String name() { return "CONFIRMED"; }
    @Override public void confirm(Booking booking) {
        throw new IllegalStateException("Booking is already confirmed");
    }
    @Override public void cancel(Booking booking) { booking.setState(new CancelledState()); }
}
