package entities;

import useful.Constants;

public final class Factory {

    /**
     * function that creates entities as consumers or distributors
     * @param type consumer or distributor
     * @param id the given entity id
     * @param initialBudget the given budget
     * @return a new entity
     */
    public Entity createEntity(final String type, final int id, final int initialBudget) {

        if (type.equals(Constants.CONSUMER)) {
            return new Consumer(id, initialBudget);
        } else {
            return new Distributor(id, initialBudget);
        }
    }

    public Producer createProducer(final int id, final EnergyType energyType, final int maxDistributors,
                                   final double priceKW, final int energyPerDistributor) {
        return new Producer(id, energyType, maxDistributors, priceKW, energyPerDistributor);
    }
}
