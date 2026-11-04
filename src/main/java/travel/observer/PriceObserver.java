package travel.observer;

import java.math.BigDecimal;

public interface PriceObserver {
    void onPriceDrop(String itemId, BigDecimal oldPrice, BigDecimal newPrice);
}
