package entities;

import strategies.EnergyChoiceStrategyType;
import strategies.Strategy;
import strategies.StrategyFactory;
import useful.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public final class Distributor extends Entity {
    private int contractLength;
    private int infrastructureCost;
    private int energyNeededKW;
    private EnergyChoiceStrategyType producerStrategy;
    private int productionCost;

    private int finalContractPrice;
    private int numberOfClients;
    private int profit = 0;
    private int monthlyCost;
    private boolean mustFindNewProducers;

    private List<Consumer> currentConsumers = new ArrayList<>();
    private List<Producer> currentProducers = new ArrayList<>();

    public Distributor(final int id, final int initialBudget) {
        this.setId(id);
        this.setBudget(initialBudget);
    }

    public int getContractLength() {
        return contractLength;
    }

    public void setContractLength(final int contractLength) {
        this.contractLength = contractLength;
    }

    public int getInfrastructureCost() {
        return infrastructureCost;
    }

    public void setInfrastructureCost(final int infrastructureCost) {
        this.infrastructureCost = infrastructureCost;
    }

    public int getEnergyNeededKW() {
        return energyNeededKW;
    }

    public void setEnergyNeededKW(int energyNeededKW) {
        this.energyNeededKW = energyNeededKW;
    }

    public EnergyChoiceStrategyType getProducerStrategy() {
        return producerStrategy;
    }

    public void setProducerStrategy(EnergyChoiceStrategyType producerStrategy) {
        this.producerStrategy = producerStrategy;
    }

    public List<Consumer> getCurrentConsumers() {
        return currentConsumers;
    }

    public void setCurrentConsumers(final List<Consumer> currentConsumers) {
        this.currentConsumers = currentConsumers;
    }

    public int getFinalContractPrice() {
        return finalContractPrice;
    }

    public void setFinalContractPrice(final int finalContractPrice) {
        this.finalContractPrice = finalContractPrice;
    }

    public int getNumberOfClients() {
        return numberOfClients;
    }

    public void setNumberOfClients(final int numberOfClients) {
        this.numberOfClients = numberOfClients;
    }

    public int getMonthlyCost() {
        return monthlyCost;
    }

    public void setMonthlyCost(final int monthlyCost) {
        this.monthlyCost = monthlyCost;
    }

    public int getProfit() {
        return profit;
    }

    public void setProfit(final int profit) {
        this.profit = profit;
    }

    public int getProductionCost() {
        return productionCost;
    }

    public void setProductionCost(int productionCost) {
        this.productionCost = productionCost;
    }

    public List<Producer> getCurrentProducers() {
        return currentProducers;
    }

    public void setCurrentProducers(List<Producer> currentProducers) {
        this.currentProducers = currentProducers;
    }

    public boolean isMustFindNewProducers() {
        return mustFindNewProducers;
    }

    public void setMustFindNewProducers(boolean mustFindNewProducers) {
        this.mustFindNewProducers = mustFindNewProducers;
    }

    /**
     * function that updates the profit based on a formula
     */
    public void updateProfit() {
        this.profit = (int) Math.round(Math.floor(Constants.PENALTY_PERCENT * productionCost));
    }

    /**
     * function that computes the monthly cost based on a formula
     * @return
     */
    public int computeMonthlyCost() {
        monthlyCost = infrastructureCost + productionCost * numberOfClients;
        return monthlyCost;
    }

    /**
     * function in which the distributor pays its monthly cost
     * (the cost is subtracted from its budget)
     */
    public void payMonthlyCost() {
        int value = this.getBudget();
        this.setBudget(value - monthlyCost);
    }

    /**
     * function in which a distributor declares bancrupcy and
     * all the needed changes are applied on him because of that
     */
    public void declareBancrupcy() {
        this.setIsBankrupt(true);
        for (Consumer consumer : this.getCurrentConsumers()) {
            consumer.setMustChangeDistributor(1);
        }
        //for (Producer producer : currentProducers) {
        //    producer.getCurrentDistributors().remove(this);
        //    producer.deleteObserver(this);
        //}
        currentConsumers.clear();
        numberOfClients = 0;
    }

    public void computeProductionCost() {
        double cost = 0;
        for (Producer producer : currentProducers) {
            cost += producer.getEnergyPerDistributor() * producer.getPriceKW();
        }
        this.productionCost = (int) Math.round(Math.floor(cost / Constants.DIVISOR));
    }

    public void findNewProducers() {
        if (this.getCurrentProducers() != null) {
            for (Producer producer : this.getCurrentProducers()) {
                producer.deleteObserver(this);
                producer.getCurrentDistributors().remove(this);
            }
            this.getCurrentProducers().clear();
        }
        StrategyFactory strFactory = new StrategyFactory();
        Strategy str = strFactory.createStrategy(this);
        str.applyStrategy(this);
        this.mustFindNewProducers = false;
    }

    /**
     * function that computes and updates the final contract price based on a formula
     */
    public void updateFinalContractPrice() {
        if (numberOfClients != 0) {
            finalContractPrice = (int) Math.round(Math.floor(infrastructureCost / numberOfClients)
                    + productionCost + profit);
        } else {
            finalContractPrice = infrastructureCost + productionCost + profit;
        }
    }

    @Override
    public String toString() {
        return "Distributor{"
                + "id=" + this.getId()
                + ", finalContractPrice="
                + finalContractPrice + '}';
    }

    @Override
    public void update(Observable producer, Object arg) {
        if (this.getCurrentProducers().contains(producer)) {
           this.mustFindNewProducers = true;
        }
    }
}
