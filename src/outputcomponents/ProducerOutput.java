package outputcomponents;

import java.util.List;

//class used as the producer component of the output class
public final class ProducerOutput {
    private int id;
    private int maxDistributors;
    private double priceKW;
    private String energyType;
    private int energyPerDistributor;
    private List<MonthlyStatus> monthlyStats;

    public ProducerOutput(int id, int maxDistributors, double priceKW, String energyType,
                          int energyPerDistributor, List<MonthlyStatus> monthlyStats) {
        this.id = id;
        this.maxDistributors = maxDistributors;
        this.priceKW = priceKW;
        this.energyType = energyType;
        this.energyPerDistributor = energyPerDistributor;
        this.monthlyStats = monthlyStats;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMaxDistributors() {
        return maxDistributors;
    }

    public void setMaxDistributors(int maxDistributors) {
        this.maxDistributors = maxDistributors;
    }

    public double getPriceKW() {
        return priceKW;
    }

    public void setPriceKW(int priceKW) {
        this.priceKW = priceKW;
    }

    public String getEnergyType() {
        return energyType;
    }

    public void setEnergyType(String energyType) {
        this.energyType = energyType;
    }

    public int getEnergyPerDistributor() {
        return energyPerDistributor;
    }

    public void setEnergyPerDistributor(int energyPerDistributor) {
        this.energyPerDistributor = energyPerDistributor;
    }

    public List<MonthlyStatus> getMonthlyStats() {
        return monthlyStats;
    }

    public void setMonthlyStats(List<MonthlyStatus> monthlyStats) {
        this.monthlyStats = monthlyStats;
    }

    @Override
    public String toString() {
        return "ProducerOutput{"
                + "id=" + id
                + ", maxDistributors=" + maxDistributors
                + ", priceKW=" + priceKW
                + ", energyType='" + energyType
                + '\'' + ", energyPerDistributor=" + energyPerDistributor
                + ", monthlyStats=" + monthlyStats
                + '}';
    }
}
