package travel.strategy;

import travel.models.TravelPackage;
import java.util.Comparator;
import java.util.List;

public class BestRatedOfferStrategy implements OfferStrategy {
    @Override public List<TravelPackage> rank(List<TravelPackage> offers) {
        return offers.stream()
                .sorted(Comparator.comparingDouble(TravelPackage::hotelRating).reversed()
                        .thenComparing(TravelPackage::totalPrice))
                .toList();
    }
    @Override public String name() { return "Highest hotel rating, then price"; }
}
