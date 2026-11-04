package travel.strategy;

import travel.models.TravelPackage;
import java.util.List;

public interface OfferStrategy {
    List<TravelPackage> rank(List<TravelPackage> offers);
    String name();
}
