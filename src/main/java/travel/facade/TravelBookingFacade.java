package travel.facade;

import travel.builder.TripBuilder;
import travel.factory.BookingCreator;
import travel.models.*;
import travel.models.*;
import travel.models.*;
import travel.observer.PriceTracker;
import travel.repository.BookingRepository;
import travel.services.*;
import travel.services.CarRentalService;
import travel.services.FlightService;
import travel.services.HotelService;
import travel.strategy.OfferStrategy;

import java.util.ArrayList;
import java.util.List;

public class TravelBookingFacade {
    private final FlightService flightService;
    private final HotelService hotelService;
    private final CarRentalService carRentalService;
    private final BookingRepository bookingRepository;
    private final PriceTracker priceTracker;

    public TravelBookingFacade(FlightService flightService, HotelService hotelService,
                               CarRentalService carRentalService,
                               BookingRepository bookingRepository, PriceTracker priceTracker) {
        this.flightService = flightService;
        this.hotelService = hotelService;
        this.carRentalService = carRentalService;
        this.bookingRepository = bookingRepository;
        this.priceTracker = priceTracker;
    }

    public List<Flight> searchFlights(String origin, String destination) {
        return flightService.search(origin, destination);
    }
    public List<Hotel> searchHotels(String city) { return hotelService.search(city); }
    public List<CarRental> searchCars(String city) { return carRentalService.search(city); }

    public List<TravelPackage> findOffers(String origin, String destination, String city,
                                          OfferStrategy strategy) {
        List<TravelPackage> offers = new ArrayList<>();
        for (Flight flight : flightService.search(origin, destination)) {
            for (Hotel hotel : hotelService.search(city)) {
                for (CarRental car : carRentalService.search(city)) {
                    try {
                        offers.add(new TripBuilder().withFlight(flight).withHotel(hotel)
                                .withCarRental(car).build());
                    } catch (IllegalArgumentException ignored) {
                        // Incompatible city/date combinations are not valid offers.
                    }
                }
            }
        }
        return strategy.rank(offers);
    }

    public Booking book(BookingCreator creator, String description) {
        Booking booking = creator.createBooking(description);
        bookingRepository.save(booking);
        return booking;
    }

    public List<Booking> getBookings() { return bookingRepository.findAll(); }
    public PriceTracker getPriceTracker() { return priceTracker; }
}
