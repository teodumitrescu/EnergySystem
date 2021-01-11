package comparators;


import entities.EnergyType;
import entities.Producer;
import java.util.Comparator;

public final class GreenComparator implements Comparator<Producer> {
    @Override
    public int compare(Producer o1, Producer o2) {
        if (o1.getEnergyType().isRenewable() == o2.getEnergyType().isRenewable()) {
            if (Double.compare(o1.getPriceKW(), o2.getPriceKW()) == 0) {
                if (o1.getEnergyPerDistributor() == o2.getEnergyPerDistributor()) {
                    return Integer.compare(o1.getId(), o2.getId());
                }
                return Integer.compare(o1.getEnergyPerDistributor(), o2.getEnergyPerDistributor());
            }
            return Double.compare(o1.getPriceKW(), o2.getPriceKW());
        } else {
            if (o1.getEnergyType().isRenewable()) {
                return -1;
            }
            return 1;
        }
    }
}
