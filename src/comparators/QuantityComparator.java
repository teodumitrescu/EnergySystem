package comparators;

import entities.Producer;
import java.util.Comparator;

public final class QuantityComparator implements Comparator<Producer> {
    @Override
    public int compare(Producer o1, Producer o2) {
        if (o1.getEnergyPerDistributor() == o2.getEnergyPerDistributor()) {
            return Integer.compare(o1.getId(), o2.getId());
        }
        return Integer.compare(o1.getEnergyPerDistributor(), o2.getEnergyPerDistributor());
    }
}
