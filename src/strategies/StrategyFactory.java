package strategies;

import entities.Distributor;

public final class StrategyFactory {

    public StrategyFactory() {
    }

    private static StrategyFactory instance = null;

    /**
     * function to make the class of Singleton design
     * @return the singular instance of the factory
     */
    public static StrategyFactory getInstance() {
        if (instance == null) {
            instance = new StrategyFactory();
        }
        return instance;
    }

    /**
     * function that creates a new strategy of a
     * certain type preffered by a distributor
     * @param distributor who chooses the type
     * @return the preferred strategy
     */
    public Strategy createStrategy(Distributor distributor) {
        if (distributor.getProducerStrategy().equals(EnergyChoiceStrategyType.GREEN)) {
            return new GreenStrategy();
        }
        if (distributor.getProducerStrategy().equals(EnergyChoiceStrategyType.PRICE)) {
            return new PriceStrategy();
        } else {
            return new QuantityStrategy();
        }
    }
}
