# Travel-Booking-Engine

<img width="2562" height="1068" alt="hLRBRXen5DqZyGzMLg08r7LKHIdGg5cWej0VCFRDOCNO8zkWqZR_Njlpz6E6BZehy3s-zvmxzp7JoPCsM9dpYKydXXaEw8V25-1e9UKx4mNwAWec02tGccEjqORXGk7JT3ATQ6iliVs3Y3DzCKQnmzd02v5Yptu1-kHG9IRlk01qjpepJa6X7JO6bBX3lwSJP1z_sKPbPOEe3UvP80hQC8" src="https://github.com/user-attachments/assets/09f3a969-7b5b-46e1-858a-ae62af1d1c40" />



## About the Project

Travel Booking Engine is a console-based application developed using Java.

The system is designed to manage travel booking operations and demonstrate flexible software architecture through reusable components.

## Project Objectives

- Develop a structured Java console application.
- Apply object-oriented programming principles.
- Implement seven software design patterns.
- Improve code reusability and maintainability.
- Demonstrate modular software architecture.

## Design Patterns

| Pattern | Where | Purpose |
|---|---|---|
| Builder | `builder/TripBuilder` | Assembles a trip package (flight, hotel, car) step by step |
| Factory Method | `factory/BookingCreator` and subclasses | Creates flight, hotel and car bookings |
| Strategy | `strategy/OfferStrategy` and implementations | Offer ranking (lowest price or best hotel rating) chosen at runtime |
| Observer | `observer/PriceTracker`, `observer/PriceObserver` | Notifies subscribers when a tracked price drops |
| Decorator | `decorator/BookingDecorator` and subclasses | Adds insurance and extra baggage to a booking's price and description |
| Facade | `facade/TravelBookingFacade` | One entry point for search, offers, bookings and price tracking |
| State | `state/BookingState` and implementations | Allows only valid status changes: pending → confirmed or cancelled, confirmed → cancelled |

## How the patterns work together

The patterns form one pipeline: each step of a booking is handled by the pattern responsible for it.

```text
Main (console menu)
 └─ TravelBookingFacade ............................................. Facade
     ├─ findOffers()
     │   ├─ TripBuilder assembles every flight + hotel + car ...... Builder
     │   └─ OfferStrategy ranks the packages ...................... Strategy
     ├─ (in Main) BasicBooking + Insurance / Baggage ................ Decorator
     ├─ book()
     │   └─ BookingCreator creates the Booking .................... Factory Method
     │       └─ Booking: PENDING → CONFIRMED / CANCELLED ........ State
     └─ getPriceTracker()
         └─ PriceTracker notifies PriceObservers on a price drop .... Observer
```

1. **Facade.** `Main` creates one `TravelBookingFacade` from the flight, hotel and car services, the booking repository and the price tracker. All search, offers, bookings and price tracking go through it.
2. **Builder.** `findOffers()` takes every flight, hotel and car on the route and assembles each combination into a `TravelPackage` with `TripBuilder`. Invalid combinations (cities that don't match) are skipped.
3. **Strategy.** The user picks `CheapestOfferStrategy` or `BestRatedOfferStrategy`, and the facade gets it as a parameter to rank the packages. A new ranking rule is a new class; the facade does not change.
4. **Decorator.** The chosen package becomes a `BasicBooking`. Each extra wraps it: `InsuranceDecorator` adds $50, `BaggageDecorator` adds $30, and both add their name to the description.
5. **Factory Method.** `book()` receives a `BookingCreator` and lets it create the `Booking` with a new ID, then saves it to the repository. The facade never calls `new Booking(...)` itself.
6. **State.** Every new `Booking` starts in `PendingState`. `confirm()` and `cancel()` are delegated to the current state, which either moves the booking on or rejects the action (for example, confirming a cancelled booking).

**Observer** runs next to the booking flow. The user subscribes a `ConsolePriceObserver` to a flight through the facade's `PriceTracker`. When `updatePrice()` sets a lower price, the tracker notifies every subscriber of that flight; a price increase sends no alert.

## Project Structure

```text
travel-booking-engine/
│
├── docs/
│
├── src/
│   ├── main/
│   │   └── java/
│   │       └── travel/
│   │           ├── builder/
│   │           ├── decorator/
│   │           ├── facade/
│   │           ├── factory/
│   │           ├── models/
│   │           ├── observer/
│   │           ├── repository/
│   │           ├── services/
│   │           ├── state/
│   │           ├── strategy/
│   │           └── Main.java
│   │
│   └── test/
│       └── java/
│
├── pom.xml
├── .gitignore
└── README.md
```

---
