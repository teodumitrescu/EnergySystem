package outputcomponents;

import java.util.List;

//class used as the consumers component of the output class
public final class DistributorOutput {
    private int id;
    private int energyNeededKW;
    private int contractCost;
    private int budget;
    private String producerStrategy;
    private boolean isBankrupt;
    private List<DistributorClient> contracts;

    public DistributorOutput(int id, int energyNeededKW, int contractCost, int budget,
                             String producerStrategy, boolean isBankrupt, List<DistributorClient> contracts) {
        this.id = id;
        this.energyNeededKW = energyNeededKW;
        this.contractCost = contractCost;
        this.budget = budget;
        this.producerStrategy = producerStrategy;
        this.isBankrupt = isBankrupt;
        this.contracts = contracts;
    }

    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public int getBudget() {
        return budget;
    }

    public void setBudget(final int budget) {
        this.budget = budget;
    }

    public boolean getIsBankrupt() {
        return isBankrupt;
    }

    public void setIsBankrupt(final boolean isBankrupt) {
        this.isBankrupt = isBankrupt;
    }

    public List<DistributorClient> getContracts() {
        return contracts;
    }

    public void setContracts(final List<DistributorClient> contracts) {
        this.contracts = contracts;
    }

    public int getEnergyNeededKW() {
        return energyNeededKW;
    }

    public void setEnergyNeededKW(int energyNeededKW) {
        this.energyNeededKW = energyNeededKW;
    }

    public int getContractCost() {
        return contractCost;
    }

    public void setContractCost(int contractCost) {
        this.contractCost = contractCost;
    }

    public String getProducerStrategy() {
        return producerStrategy;
    }

    public void setProducerStrategy(String producerStrategy) {
        this.producerStrategy = producerStrategy;
    }

    @Override
    public String toString() {
        return "DistributorOutput{" +
                "id=" + id
                + ", energyNeededKW=" + energyNeededKW
                + ", contractCost=" + contractCost
                + ", budget=" + budget
                + ", producerStrategy='" + producerStrategy + '\''
                + ", isBankrupt=" + isBankrupt
                + ", contracts=" + contracts + '}';
    }
}
