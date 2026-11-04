package travel.services;

import travel.models.CarRental;
import java.util.List;

public class CarRentalService {
    private final List<CarRental> rentals;
    public CarRentalService(List<CarRental> rentals) { this.rentals = List.copyOf(rentals); }
    public List<CarRental> search(String city) {
        return rentals.stream().filter(c -> c.city().equalsIgnoreCase(city)).toList();
    }
    public List<CarRental> all() { return rentals; }
}
