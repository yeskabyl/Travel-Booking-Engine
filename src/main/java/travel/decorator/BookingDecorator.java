package travel.decorator;

public abstract class BookingDecorator
        implements BookingComponent {

    protected final BookingComponent booking;

    public BookingDecorator(BookingComponent booking) {
        this.booking = booking;
    }

    @Override
    public String getDescription() {
        return booking.getDescription();
    }

    @Override
    public double getPrice() {
        return booking.getPrice();
    }
}
