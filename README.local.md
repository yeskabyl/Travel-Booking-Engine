# Travel Booking Engine

A Java 17 console application demonstrating six design patterns in one cohesive travel-booking workflow.

## Features

- Search demo flights, hotels and car rentals.
- Compare complete trip offers using interchangeable strategies.
- Build a travel package from selected services.
- Create bookings through a Factory Method.
- Manage booking lifecycle through the State pattern.
- Subscribe to price changes and receive Observer notifications.
- View and cancel bookings.

All inventory and prices are **demo data**. No real airline, hotel, car-rental, payment or notification APIs are connected.

## Design patterns

| Pattern | Main types | Responsibility |
|---|---|---|
| Facade | `TravelBookingFacade` | One entry point to travel services |
| Strategy | `OfferStrategy`, `CheapestOfferStrategy`, `BestRatedOfferStrategy` | Interchangeable offer-ranking algorithms |
| Observer | `PriceTracker`, `PriceObserver`, `ConsolePriceObserver` | Notify subscribers when a tracked price drops |
| Factory Method | `BookingCreator`, `FlightBookingCreator`, `HotelBookingCreator`, `CarBookingCreator` | Create service-specific booking objects |
| Builder | `TripBuilder` | Assemble a trip package step by step |
| State | `BookingState`, `PendingState`, `ConfirmedState`, `CancelledState` | Control valid booking lifecycle operations |

## Requirements

- JDK 17 or newer
- Maven 3.8+ (or IntelliJ IDEA's bundled Maven)

## Run in IntelliJ IDEA

1. Extract the ZIP.
2. Open the `travel-booking-engine` folder in IntelliJ IDEA.
3. Trust the project and allow Maven to import dependencies.
4. Set Project SDK to JDK 17 or newer.
5. Run `src/main/java/com/travel/Main.java`.

## Run from terminal

```bash
mvn clean test
mvn package
mvn exec:java -Dexec.mainClass="travel.Main"
```

If the `exec:java` command is unavailable, run `travel.Main` from IntelliJ IDEA. The project does not require an exec Maven plugin.

## Suggested demo flow

1. Choose **Search and compare offers**.
2. Select a ranking strategy.
3. Choose **Build and book a trip**.
4. Confirm the booking.
5. Choose **Track a price** and subscribe.
6. Choose **Simulate a price drop** to trigger Observer notifications.
7. View bookings and try cancelling one.

## Project structure

```text
src/main/java/com/travel/
  Main.java
  builder/       TripBuilder.java
  facade/        TravelBookingFacade.java
  factory/       BookingCreator.java and concrete creators
  models/        Flight, Hotel, CarRental, TravelPackage, Booking
  observer/      PriceObserver, PriceTracker, ConsolePriceObserver
  repository/    BookingRepository, InMemoryBookingRepository
  services/      FlightService, HotelService, CarRentalService
  state/         BookingState and concrete states
  strategy/      OfferStrategy and ranking strategies
src/test/java/com/travel/
  DesignPatternsTest.java
docs/
  class-diagram.puml
  sequence-booking.puml
```

## Notes for the defense

- Facade delegates search to the three service classes.
- Strategy ranks complete package offers using different criteria.
- Builder assembles the selected flight, hotel and car into a `TravelPackage`.
- Factory Method creates the correct booking subtype for each service.
- State prevents invalid transitions, such as confirming a cancelled booking.
- Observer notifies registered listeners when the tracked price falls.

The demo uses in-memory repositories, so data resets when the application restarts. SQLite persistence and real external APIs are possible future extensions.
