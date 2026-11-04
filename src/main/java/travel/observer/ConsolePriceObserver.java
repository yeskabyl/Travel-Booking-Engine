package travel.observer;

import java.math.BigDecimal;

public class ConsolePriceObserver implements PriceObserver {
    private final String userName;
    public ConsolePriceObserver(String userName) { this.userName = userName; }
    @Override public void onPriceDrop(String itemId, BigDecimal oldPrice, BigDecimal newPrice) {
        System.out.printf("[PRICE ALERT for %s] %s dropped from $%s to $%s%n",
                userName, itemId, oldPrice, newPrice);
    }
}
