package travel;

import travel.builder.TripBuilder;
import travel.factory.FlightBookingCreator;
import travel.models.*;
import travel.observer.PriceObserver;
import travel.observer.PriceTracker;
import travel.state.CancelledState;
import org.junit.jupiter.api.Test;
import travel.strategy.BestRatedOfferStrategy;
import travel.strategy.CheapestOfferStrategy;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

class DesignPatternsTest {
    private TravelPackage packageOf(String flightPrice, double rating) {
        LocalDate start = LocalDate.of(2027, 1, 10);
        return new TripBuilder()
                .withFlight(new Flight("F", "Demo Air", "Astana", "Istanbul",
                        start, 5.0, new BigDecimal(flightPrice)))
                .withHotel(new Hotel("H", "Demo Hotel", "Istanbul", start, start.plusDays(2),
                        new BigDecimal("50"), rating))
                .withCarRental(new CarRental("C", "Demo Cars", "Sedan", "Istanbul",
                        start, start.plusDays(2), new BigDecimal("20")))
                .build();
    }

    @Test
    void builderCreatesPackageAndCalculatesTotal() {
        TravelPackage trip = packageOf("100", 4.5);
        assertEquals(new BigDecimal("240"), trip.totalPrice());
    }

    @Test
    void cheapestStrategyRanksByTotalPrice() {
        TravelPackage expensive = packageOf("200", 4.9);
        TravelPackage cheap = packageOf("100", 4.0);
        assertEquals(cheap, new CheapestOfferStrategy().rank(List.of(expensive, cheap)).get(0));
    }

    @Test
    void bestRatedStrategyRanksByHotelRating() {
        TravelPackage lower = packageOf("100", 4.0);
        TravelPackage higher = packageOf("200", 4.9);
        assertEquals(higher, new BestRatedOfferStrategy().rank(List.of(lower, higher)).get(0));
    }

    @Test
    void observerIsNotifiedOnlyWhenPriceDrops() {
        PriceTracker tracker = new PriceTracker();
        AtomicInteger notifications = new AtomicInteger();
        PriceObserver observer = (id, oldPrice, newPrice) -> notifications.incrementAndGet();
        tracker.setInitialPrice("F1", new BigDecimal("100"));
        tracker.subscribe("F1", observer);
        tracker.updatePrice("F1", new BigDecimal("110"));
        tracker.updatePrice("F1", new BigDecimal("90"));
        assertEquals(1, notifications.get());
    }

    @Test
    void factoryCreatesPendingBookingAndStateTransitionsWork() {
        Booking booking = new FlightBookingCreator().createBooking("Astana to Istanbul");
        assertEquals("PENDING", booking.getStatus());
        booking.confirm();
        assertEquals("CONFIRMED", booking.getStatus());
        booking.cancel();
        assertEquals("CANCELLED", booking.getStatus());
        assertThrows(IllegalStateException.class, booking::confirm);
        assertInstanceOf(CancelledState.class, getStateForCompileCheck());
    }

    private CancelledState getStateForCompileCheck() { return new CancelledState(); }

    @Test
    void builderRejectsMissingComponents() {
        assertThrows(IllegalStateException.class, () -> new TripBuilder().build());
    }
}
