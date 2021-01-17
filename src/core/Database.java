package core;

import entities.Consumer;
import entities.Distributor;
import changecomponents.Update;
import entities.Producer;
import comparators.ContractPriceComparator;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Collections;

public final class Database {

    private int numberOfTurns;
    private List<Distributor> distributors;
    private List<Producer> producers;
    private List<Update> monthlyUpdates;

    private Map<Integer, Consumer> consumersMap;
    private Map<Integer, Distributor> distributorsMap;
    private Map<Integer, Producer> producersMap;

    private static Database instance = null;

    private Database() {
        //the distributors list is used only when sorting by the contract price
        this.distributors = new ArrayList<>();
        this.monthlyUpdates = new ArrayList<>();
        this.producers = new ArrayList<>();

        this.consumersMap = new HashMap<>();
        this.distributorsMap = new HashMap<>();
        this.producersMap = new HashMap<>();
    }

    /**
     * function to make the class of Singleton design
     * @return the singular instance of the database
     */
    public static Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }

    /**
     * function that empties all the lists and
     * maps in the database
     */
    public void emptyDatabase() {
        this.distributors.clear();
        this.producers.clear();
        this.monthlyUpdates.clear();
        this.consumersMap.clear();
        this.distributorsMap.clear();
        this.producersMap.clear();
    }

    public int getNumberOfTurns() {
        return numberOfTurns;
    }

    public void setNumberOfTurns(final int numberOfTurns) {
        this.numberOfTurns = numberOfTurns;
    }

    public List<Distributor> getDistributors() {
        return distributors;
    }

    public void setDistributors(final List<Distributor> distributors) {
        this.distributors = distributors;
    }

    public List<Update> getMonthlyUpdates() {
        return monthlyUpdates;
    }

    public void setMonthlyUpdates(final List<Update> monthlyUpdates) {
        this.monthlyUpdates = monthlyUpdates;
    }

    public Map<Integer, Consumer> getConsumersMap() {
        return consumersMap;
    }

    public void setConsumersMap(final Map<Integer, Consumer> consumersMap) {
        this.consumersMap = consumersMap;
    }

    public Map<Integer, Distributor> getDistributorsMap() {
        return distributorsMap;
    }

    public void setDistributorsMap(final Map<Integer, Distributor> distributorsMap) {
        this.distributorsMap = distributorsMap;
    }

    public Map<Integer, Producer> getProducersMap() {
        return producersMap;
    }

    public void setProducersMap(Map<Integer, Producer> producersMap) {
        this.producersMap = producersMap;
    }

    public List<Producer> getProducers() {
        return producers;
    }

    public void setProducers(List<Producer> producers) {
        this.producers = producers;
    }

    /**
     * function that reinitializes the list of distributors with
     * all their new values and sorts them by the contract price
     */
    public void updateDistributorsList() {
        this.distributors.clear();
        this.distributors.addAll(this.distributorsMap.values());
        Collections.sort(this.distributors, new ContractPriceComparator());
    }

    public void updateProducersList() {
        this.producers.clear();
        this.producers.addAll(this.producersMap.values());
        //Collections.sort(this.producers);
    }
}
