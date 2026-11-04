package travel.repository;

import travel.models.Booking;
import java.util.ArrayList;
import java.util.List;

public class InMemoryBookingRepository implements BookingRepository {
    private final List<Booking> bookings = new ArrayList<>();
    @Override public void save(Booking booking) { bookings.add(booking); }
    @Override public List<Booking> findAll() { return List.copyOf(bookings); }
}
