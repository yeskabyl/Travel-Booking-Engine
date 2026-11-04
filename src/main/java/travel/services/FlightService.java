package travel.services;

import travel.models.Flight;
import java.util.List;

public class FlightService {
    private final List<Flight> flights;
    public FlightService(List<Flight> flights) { this.flights = List.copyOf(flights); }
    public List<Flight> search(String origin, String destination) {
        return flights.stream()
                .filter(f -> f.origin().equalsIgnoreCase(origin)
                        && f.destination().equalsIgnoreCase(destination))
                .toList();
    }
    public List<Flight> all() { return flights; }
}
