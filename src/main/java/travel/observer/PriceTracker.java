package travel.observer;

import java.math.BigDecimal;
import java.util.*;

public class PriceTracker {
    private final Map<String, BigDecimal> prices = new HashMap<>();
    private final Map<String, List<PriceObserver>> observers = new HashMap<>();

    public void subscribe(String itemId, PriceObserver observer) {
        observers.computeIfAbsent(itemId, key -> new ArrayList<>()).add(observer);
    }

    public void setInitialPrice(String itemId, BigDecimal price) {
        if (price == null || price.signum() < 0) throw new IllegalArgumentException("Invalid price");
        prices.put(itemId, price);
    }

    public void updatePrice(String itemId, BigDecimal newPrice) {
        if (newPrice == null || newPrice.signum() < 0) throw new IllegalArgumentException("Invalid price");
        BigDecimal oldPrice = prices.get(itemId);
        if (oldPrice == null) throw new IllegalArgumentException("No price tracked for " + itemId);
        prices.put(itemId, newPrice);
        if (newPrice.compareTo(oldPrice) < 0) {
            observers.getOrDefault(itemId, List.of())
                    .forEach(observer -> observer.onPriceDrop(itemId, oldPrice, newPrice));
        }
    }

    public BigDecimal getCurrentPrice(String itemId) { return prices.get(itemId); }
}
