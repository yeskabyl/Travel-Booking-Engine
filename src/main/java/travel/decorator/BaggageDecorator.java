package travel.decorator;

public class BaggageDecorator extends BookingDecorator {

    public BaggageDecorator(BookingComponent booking) {
        super(booking);
    }

    @Override
    public String getDescription() {
        return booking.getDescription() + " + Extra Baggage";
    }

    @Override
    public double getPrice() {
        return booking.getPrice() + 30.0;
    }
}
