package travel.models;

import travel.state.BookingState;
import travel.state.PendingState;

import java.time.LocalDateTime;
import java.util.Objects;

public class Booking {
    private final String id;
    private final String serviceDescription;
    private final LocalDateTime createdAt;
    private BookingState state;

    public Booking(String id, String serviceDescription) {
        this.id = Objects.requireNonNull(id);
        this.serviceDescription = Objects.requireNonNull(serviceDescription);
        this.createdAt = LocalDateTime.now();
        this.state = new PendingState();
    }

    public String getId() { return id; }
    public String getServiceDescription() { return serviceDescription; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public String getStatus() { return state.name(); }
    public void confirm() { state.confirm(this); }
    public void cancel() { state.cancel(this); }
    public void setState(BookingState state) { this.state = Objects.requireNonNull(state); }

    @Override public String toString() {
        return id + " | " + serviceDescription + " | " + getStatus() + " | " + createdAt;
    }
}
