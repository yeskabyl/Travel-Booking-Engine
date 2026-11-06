package travel.decorator;

public class InsuranceDecorator extends BookingDecorator {

    public InsuranceDecorator(BookingComponent booking) {
        super(booking);
    }

    @Override
    public String getDescription() {
        return booking.getDescription() + " + Insurance";
    }

    @Override
    public double getPrice() {
        return booking.getPrice() + 50.0;
    }
}
