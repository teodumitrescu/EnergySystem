package strategies;

import entities.Distributor;

public final class StrategyFactory {
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
