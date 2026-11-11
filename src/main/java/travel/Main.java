package travel;

import travel.strategy.*;
import travel.models.*;
import travel.repository.*;
import travel.decorator.*;
import travel.builder.*;
import travel.facade.*;
import travel.factory.*;
import travel.observer.*;
import travel.services.*;
import travel.state.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final LocalDate DEPARTURE = LocalDate.now().plusDays(30);
    private static final LocalDate RETURN_DATE = DEPARTURE.plusDays(5);

    private static final List<Flight> FLIGHTS = List.of(
            // Astana -> Istanbul
            flight("F001", "Air Astana", "Astana", "Istanbul", 5.5, "450.00"),
            flight("F002", "Turkish Airlines", "Astana", "Istanbul", 6.0, "520.00"),
            flight("F003", "Pegasus", "Astana", "Istanbul", 7.0, "390.00"),
            // Almaty -> Istanbul
            flight("F004", "Air Astana", "Almaty", "Istanbul", 6.5, "420.00"),
            flight("F005", "Pegasus", "Almaty", "Istanbul", 7.0, "360.00"),
            // Astana -> Dubai
            flight("F006", "Air Astana", "Astana", "Dubai", 4.5, "380.00"),
            flight("F007", "flydubai", "Astana", "Dubai", 4.5, "310.00"),
            // Almaty -> Dubai
            flight("F008", "Air Astana", "Almaty", "Dubai", 4.0, "350.00"),
            flight("F009", "FlyArystan", "Almaty", "Dubai", 4.0, "240.00"),
            // Almaty -> Tbilisi
            flight("F010", "Air Astana", "Almaty", "Tbilisi", 3.5, "280.00"),
            flight("F011", "FlyArystan", "Almaty", "Tbilisi", 3.5, "190.00"),
            // Almaty -> Seoul
            flight("F012", "Air Astana", "Almaty", "Seoul", 6.0, "460.00"),
            flight("F013", "Asiana Airlines", "Almaty", "Seoul", 6.0, "510.00"),
            // Astana -> Antalya
            flight("F014", "Pegasus", "Astana", "Antalya", 6.5, "340.00"),
            flight("F015", "SCAT", "Astana", "Antalya", 6.5, "300.00")
    );

    private static final List<Hotel> HOTELS = List.of(
            hotel("H001", "Bosphorus Hotel", "Istanbul", "120.00", 4.8),
            hotel("H002", "Old Town Stay", "Istanbul", "75.00", 4.2),
            hotel("H003", "Marina View Hotel", "Dubai", "160.00", 4.7),
            hotel("H004", "Deira City Inn", "Dubai", "70.00", 4.0),
            hotel("H005", "Old Tbilisi Boutique", "Tbilisi", "85.00", 4.6),
            hotel("H006", "Rustaveli Hostel", "Tbilisi", "30.00", 3.9),
            hotel("H007", "Gangnam Central Hotel", "Seoul", "140.00", 4.5),
            hotel("H008", "Myeongdong Guesthouse", "Seoul", "60.00", 4.1),
            hotel("H009", "Lara Beach Resort", "Antalya", "130.00", 4.6),
            hotel("H010", "Kaleici Pension", "Antalya", "50.00", 4.3)
    );

    private static final List<CarRental> CARS = List.of(
            // Istanbul
            car("C001", "Hertz", "Toyota Corolla (Compact)", "Istanbul", "45.00"),
            car("C002", "Avis", "Hyundai Elantra (Compact)", "Istanbul", "40.00"),
            car("C003", "Sixt", "Kia Rio (Economy)", "Istanbul", "28.00"),
            car("C004", "Europcar", "Toyota RAV4 (SUV)", "Istanbul", "65.00"),
            car("C005", "Sixt", "Mercedes E-Class (Premium)", "Istanbul", "120.00"),
            // Dubai
            car("C006", "Hertz", "Kia Rio (Economy)", "Dubai", "30.00"),
            car("C007", "Avis", "Kia Sportage (SUV)", "Dubai", "60.00"),
            car("C008", "Sixt", "BMW 5 Series (Premium)", "Dubai", "130.00"),
            car("C009", "Europcar", "Hyundai Staria (Van)", "Dubai", "80.00"),
            // Tbilisi
            car("C010", "Avis", "Chevrolet Cobalt (Economy)", "Tbilisi", "25.00"),
            car("C011", "Hertz", "Toyota RAV4 (SUV)", "Tbilisi", "62.00"),
            car("C012", "Europcar", "Hyundai Staria (Van)", "Tbilisi", "78.00"),
            // Seoul
            car("C013", "Hertz", "Hyundai Elantra (Compact)", "Seoul", "42.00"),
            car("C014", "Avis", "Kia Sportage (SUV)", "Seoul", "65.00"),
            car("C015", "Sixt", "Hyundai Staria (Van)", "Seoul", "85.00"),
            // Antalya
            car("C016", "Europcar", "Kia Rio (Economy)", "Antalya", "27.00"),
            car("C017", "Sixt", "Toyota Corolla (Compact)", "Antalya", "40.00"),
            car("C018", "Avis", "Toyota RAV4 (SUV)", "Antalya", "60.00")
    );

    private static String origin = "Astana";
    private static String destination = "Istanbul";

    public static void main(String[] args) {
        TravelBookingFacade app = createApplication();
        runMenu(app);
    }

    private static Flight flight(String id, String airline, String from, String to,
                                 double hours, String price) {
        return new Flight(id, airline, from, to, DEPARTURE, hours, new BigDecimal(price));
    }

    private static Hotel hotel(String id, String name, String city, String pricePerNight, double rating) {
        return new Hotel(id, name, city, DEPARTURE, RETURN_DATE, new BigDecimal(pricePerNight), rating);
    }

    private static CarRental car(String id, String company, String model, String city, String pricePerDay) {
        return new CarRental(id, company, model, city, DEPARTURE, RETURN_DATE, new BigDecimal(pricePerDay));
    }

    private static TravelBookingFacade createApplication() {
        PriceTracker tracker = new PriceTracker();
        FLIGHTS.forEach(f -> tracker.setInitialPrice(f.id(), f.price()));

        return new TravelBookingFacade(
                new FlightService(FLIGHTS),
                new HotelService(HOTELS),
                new CarRentalService(CARS),
                new InMemoryBookingRepository(),
                tracker
        );
    }

    private static void runMenu(TravelBookingFacade app) {
        try (Scanner scanner = new Scanner(System.in)) {
            boolean running = true;
            while (running) {
                System.out.println("\n========== TRAVEL BOOKING ENGINE ==========");
                System.out.println("Route: " + origin + " -> " + destination
                        + " | " + DEPARTURE + " - " + RETURN_DATE);
                System.out.println("1. Search flights");
                System.out.println("2. Search hotels");
                System.out.println("3. Search car rentals");
                System.out.println("4. Search and compare complete trip offers");
                System.out.println("5. Book a trip (Factory Method + State)");
                System.out.println("6. Track a flight price (Observer)");
                System.out.println("7. Simulate a price drop");
                System.out.println("8. View bookings");
                System.out.println("9. Cancel a booking");
                System.out.println("10. Change route");
                System.out.println("0. Exit");
                System.out.print("Choose: ");

                String choice = scanner.nextLine().trim();
                try {
                    switch (choice) {
                        case "1" -> printList(app.searchFlights(origin, destination));
                        case "2" -> printList(app.searchHotels(destination));
                        case "3" -> printList(app.searchCars(destination));
                        case "4" -> showOffers(app, scanner);
                        case "5" -> bookTrip(app, scanner);
                        case "6" -> {
                            String id = chooseFlightId(app, scanner);
                            app.getPriceTracker().subscribe(id, new ConsolePriceObserver("Demo User"));
                            System.out.println("Subscribed to price changes for flight " + id + ".");
                        }
                        case "7" -> {
                            String id = chooseFlightId(app, scanner);
                            System.out.println("Current price: $" + app.getPriceTracker().getCurrentPrice(id));
                            System.out.print("Enter new price for " + id + ": ");
                            BigDecimal newPrice = new BigDecimal(scanner.nextLine().trim());
                            app.getPriceTracker().updatePrice(id, newPrice);
                            System.out.println("Price updated. Current price: $"
                                    + app.getPriceTracker().getCurrentPrice(id));
                        }
                        case "8" -> {
                            if (app.getBookings().isEmpty()) System.out.println("No bookings yet.");
                            else app.getBookings().forEach(System.out::println);
                        }
                        case "9" -> cancelBooking(app, scanner);
                        case "10" -> changeRoute(scanner);
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

    private static void printList(List<?> items) {
        if (items.isEmpty()) System.out.println("Nothing found for this route.");
        else items.forEach(System.out::println);
    }

    private static void changeRoute(Scanner scanner) {
        List<String[]> routes = new ArrayList<>();
        for (Flight f : FLIGHTS) {
            boolean exists = routes.stream()
                    .anyMatch(r -> r[0].equals(f.origin()) && r[1].equals(f.destination()));
            if (!exists) routes.add(new String[]{f.origin(), f.destination()});
        }
        for (int i = 0; i < routes.size(); i++) {
            System.out.println((i + 1) + ". " + routes.get(i)[0] + " -> " + routes.get(i)[1]);
        }
        System.out.print("Choose route: ");
        int selected = Integer.parseInt(scanner.nextLine().trim()) - 1;
        if (selected < 0 || selected >= routes.size()) throw new IllegalArgumentException("Invalid route number");

        origin = routes.get(selected)[0];
        destination = routes.get(selected)[1];
        System.out.println("Route changed to " + origin + " -> " + destination);
    }

    private static String chooseFlightId(TravelBookingFacade app, Scanner scanner) {
        List<Flight> flights = app.searchFlights(origin, destination);
        if (flights.isEmpty()) throw new IllegalStateException("No flights on this route");
        flights.forEach(f -> System.out.println(f.id() + " | " + f.airline()
                + " | $" + app.getPriceTracker().getCurrentPrice(f.id())));
        System.out.print("Enter flight ID: ");
        String id = scanner.nextLine().trim().toUpperCase();
        return flights.stream().map(Flight::id).filter(id::equals).findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Flight not found on this route"));
    }

    private static void showOffers(TravelBookingFacade app, Scanner scanner) {
        System.out.println("1. Lowest total price");
        System.out.println("2. Highest hotel rating");
        System.out.print("Select strategy: ");
        OfferStrategy strategy = switch (scanner.nextLine().trim()) {
            case "2" -> new BestRatedOfferStrategy();
            default -> new CheapestOfferStrategy();
        };
        List<TravelPackage> offers = app.findOffers(origin, destination, destination, strategy);
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
                origin, destination, destination, new CheapestOfferStrategy());
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

        BookingComponent item = new BasicBooking(
                "Complete trip: " + trip.flight().origin() + " to " + trip.flight().destination(),
                trip.totalPrice().doubleValue());
        System.out.print("Add travel insurance (+$50)? (y/n): ");
        if (scanner.nextLine().trim().equalsIgnoreCase("y")) {
            item = new InsuranceDecorator(item);
        }
        System.out.print("Add extra baggage (+$30)? (y/n): ");
        if (scanner.nextLine().trim().equalsIgnoreCase("y")) {
            item = new BaggageDecorator(item);
        }

        BookingCreator creator = new FlightBookingCreator();
        Booking booking = app.book(creator,
                item.getDescription() + ", total $" + String.format("%.2f", item.getPrice()));
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