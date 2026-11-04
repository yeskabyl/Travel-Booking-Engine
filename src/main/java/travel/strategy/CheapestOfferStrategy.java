package travel.strategy;

import travel.models.TravelPackage;
import java.util.Comparator;
import java.util.List;

public class CheapestOfferStrategy implements OfferStrategy {
    @Override public List<TravelPackage> rank(List<TravelPackage> offers) {
        return offers.stream().sorted(Comparator.comparing(TravelPackage::totalPrice)).toList();
    }
    @Override public String name() { return "Lowest total price"; }
}
