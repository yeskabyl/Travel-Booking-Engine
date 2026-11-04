package travel.repository;

import travel.models.Booking;
import java.util.List;

public interface BookingRepository {
    void save(Booking booking);
    List<Booking> findAll();
}
