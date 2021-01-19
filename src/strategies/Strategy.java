package strategies;

import entities.Distributor;

public interface Strategy {
    /**
     * function used to apply a strategy by a
     * distributor in general
     * @param distributor which will apply
     *                    the strategy
     */
    void applyStrategy(Distributor distributor);
}
