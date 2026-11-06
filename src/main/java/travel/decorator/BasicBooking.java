package travel.decorator;

public class BasicBooking implements BookingComponent {

    @Override
    public String getDescription() {
        return "Basic Flight Booking";
    }

    @Override
    public double getPrice() {
        return 300.0;
    }
}
