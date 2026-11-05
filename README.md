# Travel-Booking-Engine

## About the Project

Travel Booking Engine is a console-based application developed using Java. The project demonstrates object-oriented programming principles and the practical application of software design patterns.

The system is designed to manage travel booking operations and demonstrate flexible software architecture through reusable components.

This project was developed as an individual academic project.

## Project Objectives

- Develop a structured Java console application.
- Apply object-oriented programming principles.
- Implement six software design patterns.
- Improve code reusability and maintainability.
- Demonstrate modular software architecture.

## Design Patterns

### 1. Builder Pattern
Used to construct complex travel packages step by step.

### 2. Factory Method Pattern
Encapsulates the creation of different booking types.

### 3. Strategy Pattern
Provides interchangeable algorithms for selecting, filtering, and sorting travel offers.

### 4. Observer Pattern
Implements a notification mechanism for changes in travel prices.

### 5. Decorator Pattern
Allows additional booking features, such as insurance and baggage, to be added dynamically.

### 6. Facade Pattern
Provides a simplified interface for interacting with travel booking subsystems.

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
