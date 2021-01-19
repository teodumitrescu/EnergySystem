package strategies;

import comparators.GreenComparator;
import core.Database;
import entities.Distributor;
import entities.Producer;

import java.util.Collections;

//class for the green strategy applied by a distributor
public final class GreenStrategy implements Strategy {

    @Override
    public void applyStrategy(Distributor distributor) {
        Collections.sort(Database.getInstance().getProducers(), new GreenComparator());

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
