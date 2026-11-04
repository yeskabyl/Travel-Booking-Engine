package travel.builder;

import travel.models.CarRental;
import travel.models.Flight;
import travel.models.Hotel;
import travel.models.TravelPackage;

public class TripBuilder {
    private Flight flight;
    private Hotel hotel;
    private CarRental carRental;

    public TripBuilder withFlight(Flight flight) { this.flight = flight; return this; }
    public TripBuilder withHotel(Hotel hotel) { this.hotel = hotel; return this; }
    public TripBuilder withCarRental(CarRental carRental) { this.carRental = carRental; return this; }

    public TravelPackage build() {
        if (flight == null || hotel == null || carRental == null) {
            throw new IllegalStateException("A trip requires a flight, hotel and car rental");
        }
        return new TravelPackage(flight, hotel, carRental);
    }
}
