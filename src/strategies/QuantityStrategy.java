package strategies;

import comparators.QuantityComparator;
import core.Database;
import entities.Distributor;
import entities.Producer;

import java.util.Collections;

//class for the queen strategy applied by a distributor
public final class QuantityStrategy implements Strategy {

    @Override
    public void applyStrategy(Distributor distributor) {
        Collections.sort(Database.getInstance().getProducers(), new QuantityComparator());

        int remainedEnergy = distributor.getEnergyNeededKW();
        for (Producer producer : Database.getInstance().getProducers()) {
            if (remainedEnergy <= 0) {
                break;
            }
            if (producer.getCurrentDistributors().size() < producer.getMaxDistributors()) {
                distributor.getCurrentProducers().add(producer);
                producer.getCurrentDistributors().add(distributor);
                producer.addObserver(distributor);
                remainedEnergy -= producer.getEnergyPerDistributor();
            }
        }
    }
}
