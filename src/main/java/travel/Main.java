package travel;

import travel.facade.TravelBookingFacade;
import travel.factory.*;
import travel.models.*;
import travel.factory.BookingCreator;
import travel.factory.FlightBookingCreator;
import travel.models.*;
import travel.models.*;
import travel.observer.ConsolePriceObserver;
import travel.observer.PriceTracker;
import travel.repository.InMemoryBookingRepository;
import travel.services.*;
import travel.strategy.*;
import travel.services.CarRentalService;
import travel.services.FlightService;
import travel.services.HotelService;
import travel.strategy.BestRatedOfferStrategy;
import travel.strategy.CheapestOfferStrategy;
import travel.strategy.OfferStrategy;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final String ORIGIN = "Astana";
    private static final String DESTINATION = "Istanbul";

    public static void main(String[] args) {
        TravelBookingFacade app = createApplication();
        runMenu(app);
    }

    private static TravelBookingFacade createApplication() {
        LocalDate departure = LocalDate.now().plusDays(30);
        LocalDate returnDate = departure.plusDays(5);

        List<Flight> flights = List.of(
                new Flight("F001", "Air Astana", ORIGIN, DESTINATION, departure, 5.5, new BigDecimal("450.00")),
                new Flight("F002", "Turkish Airlines", ORIGIN, DESTINATION, departure, 6.0, new BigDecimal("520.00")),
                new Flight("F003", "Demo Air", ORIGIN, DESTINATION, departure, 7.0, new BigDecimal("390.00"))
        );
        List<Hotel> hotels = List.of(
                new Hotel("H001", "Bosphorus Hotel", DESTINATION, departure, returnDate, new BigDecimal("120.00"), 4.8),
                new Hotel("H002", "Old Town Stay", DESTINATION, departure, returnDate, new BigDecimal("75.00"), 4.2)
        );
        List<CarRental> cars = List.of(
                new CarRental("C001", "Hertz", "Toyota Corolla", DESTINATION, departure, returnDate, new BigDecimal("45.00")),
                new CarRental("C002", "Avis", "Hyundai Elantra", DESTINATION, departure, returnDate, new BigDecimal("40.00"))
        );

        PriceTracker tracker = new PriceTracker();
        tracker.setInitialPrice("F001", new BigDecimal("450.00"));

        return new TravelBookingFacade(
                new FlightService(flights),
                new HotelService(hotels),
                new CarRentalService(cars),
                new InMemoryBookingRepository(),
                tracker
        );
    }

    private static void runMenu(TravelBookingFacade app) {
        try (Scanner scanner = new Scanner(System.in)) {
            boolean running = true;
            while (running) {
                System.out.println("\n========== TRAVEL BOOKING ENGINE ==========");
                System.out.println("1. Search flights");
                System.out.println("2. Search hotels");
                System.out.println("3. Search car rentals");
                System.out.println("4. Search and compare complete trip offers");
                System.out.println("5. Book a trip (Factory Method + State)");
                System.out.println("6. Track a flight price (Observer)");
                System.out.println("7. Simulate a price drop");
                System.out.println("8. View bookings");
                System.out.println("9. Cancel a booking");
                System.out.println("0. Exit");
                System.out.print("Choose: ");

                String choice = scanner.nextLine().trim();
                try {
                    switch (choice) {
                        case "1" -> app.searchFlights(ORIGIN, DESTINATION).forEach(System.out::println);
                        case "2" -> app.searchHotels(DESTINATION).forEach(System.out::println);
                        case "3" -> app.searchCars(DESTINATION).forEach(System.out::println);
                        case "4" -> showOffers(app, scanner);
                        case "5" -> bookTrip(app, scanner);
                        case "6" -> {
                            app.getPriceTracker().subscribe("F001", new ConsolePriceObserver("Demo User"));
                            System.out.println("Subscribed to price changes for flight F001.");
                        }
                        case "7" -> {
                            System.out.print("Enter new demo price for F001: ");
                            BigDecimal newPrice = new BigDecimal(scanner.nextLine().trim());
                            app.getPriceTracker().updatePrice("F001", newPrice);
                            System.out.println("Price updated. Current price: $" +
                                    app.getPriceTracker().getCurrentPrice("F001"));
                        }
                        case "8" -> {
                            if (app.getBookings().isEmpty()) System.out.println("No bookings yet.");
                            else app.getBookings().forEach(System.out::println);
                        }
                        case "9" -> cancelBooking(app, scanner);
                        case "0" -> running = false;
                        default -> System.out.println("Unknown option. Try again.");
                    }
                } catch (RuntimeException ex) {
                    System.out.println("Operation failed: " + ex.getMessage());
                }
            }
            System.out.println("Thank you for using Travel Booking Engine!");
        }
    }

    private static void showOffers(TravelBookingFacade app, Scanner scanner) {
        System.out.println("1. Lowest total price");
        System.out.println("2. Highest hotel rating");
        System.out.print("Select strategy: ");
        OfferStrategy strategy = switch (scanner.nextLine().trim()) {
            case "2" -> new BestRatedOfferStrategy();
            default -> new CheapestOfferStrategy();
        };
        List<TravelPackage> offers = app.findOffers(ORIGIN, DESTINATION, DESTINATION, strategy);
        System.out.println("Ranking: " + strategy.name());
        if (offers.isEmpty()) System.out.println("No compatible offers found.");
        else {
            for (int i = 0; i < offers.size(); i++) {
                System.out.println("\nOffer #" + (i + 1));
                System.out.println(offers.get(i));
            }
        }
    }

    private static void bookTrip(TravelBookingFacade app, Scanner scanner) {
        List<TravelPackage> offers = app.findOffers(
                ORIGIN, DESTINATION, DESTINATION, new CheapestOfferStrategy());
        if (offers.isEmpty()) {
            System.out.println("No offers available.");
            return;
        }
        for (int i = 0; i < offers.size(); i++) {
            System.out.println((i + 1) + ". " + offers.get(i).flight().airline()
                    + " + " + offers.get(i).hotel().name()
                    + " + " + offers.get(i).carRental().model()
                    + " | $" + offers.get(i).totalPrice());
        }
        System.out.print("Choose offer number: ");
        int selected = Integer.parseInt(scanner.nextLine().trim()) - 1;
        if (selected < 0 || selected >= offers.size()) throw new IllegalArgumentException("Invalid offer number");

        TravelPackage trip = offers.get(selected);
        BookingCreator creator = new FlightBookingCreator();
        Booking booking = app.book(creator, "Complete trip: " + trip.flight().origin()
                + " to " + trip.flight().destination() + ", total $" + trip.totalPrice());
        System.out.println("Created booking: " + booking);
        System.out.print("Confirm now? (y/n): ");
        if (scanner.nextLine().trim().equalsIgnoreCase("y")) {
            booking.confirm();
            System.out.println("Booking confirmed: " + booking.getStatus());
        }
    }

    private static void cancelBooking(TravelBookingFacade app, Scanner scanner) {
        List<Booking> bookings = app.getBookings();
        if (bookings.isEmpty()) {
            System.out.println("No bookings to cancel.");
            return;
        }
        bookings.forEach(System.out::println);
        System.out.print("Enter booking ID: ");
        String id = scanner.nextLine().trim();
        Booking booking = bookings.stream().filter(b -> b.getId().equalsIgnoreCase(id))
                .findFirst().orElseThrow(() -> new IllegalArgumentException("Booking not found"));
        booking.cancel();
        System.out.println("Booking cancelled: " + booking.getStatus());
    }
}
