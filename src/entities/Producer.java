package entities;

import comparators.IdDistComparator;
import outputcomponents.MonthlyStatus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Observable;

public final class Producer extends Observable {
    private int id;
    private EnergyType energyType;
    private int maxDistributors;
    private double priceKW;
    private int energyPerDistributor;
    private List<MonthlyStatus> monthlyStats = new ArrayList<>();
    private List<Distributor> currentDistributors = new ArrayList<>();

    public Producer(int id, EnergyType energyType, int maxDistributors,
                    double priceKW, int energyPerDistributor) {
        this.id = id;
        this.energyType = energyType;
        this.maxDistributors = maxDistributors;
        this.priceKW = priceKW;
        this.energyPerDistributor = energyPerDistributor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public EnergyType getEnergyType() {
        return energyType;
    }

    public void setEnergyType(EnergyType energyType) {
        this.energyType = energyType;
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

    public void setPriceKW(double priceKW) {
        this.priceKW = priceKW;
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

    public List<Distributor> getCurrentDistributors() {
        return currentDistributors;
    }

    public void setCurrentDistributors(List<Distributor> currentDistributors) {
        this.currentDistributors = currentDistributors;
    }

    public void notifyDistributors() {
        this.setChanged();
        this.notifyObservers();
    }

    public void updateMonthlyStats(int month) {
        ArrayList<Integer> crtDistributorsId = new ArrayList<>();
        if (!getCurrentDistributors().isEmpty()) {
            Collections.sort(getCurrentDistributors(), new IdDistComparator());
            for (Distributor distributor : getCurrentDistributors()) {
                crtDistributorsId.add(distributor.getId());
            }
        }

        MonthlyStatus crtStatus = new MonthlyStatus(month, crtDistributorsId);
        this.monthlyStats.add(crtStatus);
    }
}
