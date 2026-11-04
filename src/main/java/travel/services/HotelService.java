package travel.services;

import travel.models.Hotel;
import java.util.List;

public class HotelService {
    private final List<Hotel> hotels;
    public HotelService(List<Hotel> hotels) { this.hotels = List.copyOf(hotels); }
    public List<Hotel> search(String city) {
        return hotels.stream().filter(h -> h.city().equalsIgnoreCase(city)).toList();
    }
    public List<Hotel> all() { return hotels; }
}
