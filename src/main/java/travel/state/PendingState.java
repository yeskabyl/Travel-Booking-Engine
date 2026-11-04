package travel.state;

import travel.models.Booking;

public class PendingState implements BookingState {
    @Override public String name() { return "PENDING"; }
    @Override public void confirm(Booking booking) { booking.setState(new ConfirmedState()); }
    @Override public void cancel(Booking booking) { booking.setState(new CancelledState()); }
}
