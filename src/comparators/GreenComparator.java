package comparators;

import entities.Producer;
import java.util.Comparator;

//comparator used to sort producers by their type of energy, price, quantity of
//energy per distributor and then by id
public final class GreenComparator implements Comparator<Producer> {
    @Override
    public int compare(Producer o1, Producer o2) {
        if (o1.getEnergyType().isRenewable() == o2.getEnergyType().isRenewable()) {
            if (Double.compare(o1.getPriceKW(), o2.getPriceKW()) == 0) {
                if (o1.getEnergyPerDistributor() == o2.getEnergyPerDistributor()) {
                    return Integer.compare(o1.getId(), o2.getId());
                }
                return Integer.compare(o2.getEnergyPerDistributor(), o1.getEnergyPerDistributor());
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
