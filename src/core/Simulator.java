package core;

import changecomponents.DistributorChange;
import changecomponents.ProducerChange;
import comparators.IdDistComparator;
import comparators.IdProdComparator;
import entities.Consumer;
import entities.Distributor;
import entities.Producer;

import java.util.Collections;

public final class Simulator {

    private static Simulator instance = null;

    /**
     * function to make the class of Singleton design
     * @return the singular instance of the database
     */
    public static Simulator getInstance() {
        if (instance == null) {
            instance = new Simulator();
        }
        return instance;
    }

    //consumers are getting their salary and updating their remainder month number
    private void consumersGetSalary() {
        for (Consumer consumer : Database.getInstance().getConsumersMap().values()) {
            if (!consumer.getIsBankrupt()) {
                consumer.updateBudget();
                int value = consumer.getMonthsLeftFromContract();
                consumer.setMonthsLeftFromContract(value - 1);
            }
        }
    }

    //consumers pay their bills
    private void consumersPayBills() {
        for (Consumer consumer : Database.getInstance().getConsumersMap().values()) {
            if (!consumer.getIsBankrupt()) {
                if ((consumer.getBudget() - consumer.computePayroll()) < 0) {
                    if (consumer.getPenalty() != 0) {
                        consumer.setIsBankrupt(true);
                    } else {
                        consumer.updateBillsPenalty();
                    }
                } else {
                    consumer.payBills();
                }
            }
        }
    }

    //consumers switch (or not) to a new found contract
    private void consumersChangeContract(boolean isInitialRound) {
        for (Consumer consumer : Database.getInstance().getConsumersMap().values()) {
            if (!consumer.getIsBankrupt()) {
                if (consumer.getMonthsLeftFromContract() == 0) {
                    consumer.findNewDistributor();
                } else if (consumer.getMustChangeDistributor() == 1 || isInitialRound) {
                    consumer.findNewDistributor();
                }
            }
        }
    }

    //adding new consumers and attributing them distributors
    private void consumersAddChanges(int month) {
        for (Consumer consumer : Database.getInstance().getMonthlyUpdates().get(month).getNewConsumersList()) {
            Database.getInstance().getConsumersMap().put(consumer.getId(), consumer);
            consumer.findNewDistributor();
        }
    }

    //bankrupt consumers are removed from their distributors
    private void eliminateBankruptConsumers() {
        for (Consumer consumer : Database.getInstance().getConsumersMap().values()) {
            if (consumer.getIsBankrupt()) {
                if (consumer.getCurrentDistributor().getCurrentConsumers().contains(consumer)) {
                    consumer.getCurrentDistributor().getCurrentConsumers().remove(consumer);
                    int value = consumer.getCurrentDistributor().getNumberOfClients();
                    consumer.getCurrentDistributor().setNumberOfClients(value - 1);
                }
            }
        }
    }

    //distributors pay their costs
    private void distributorsPayCosts() {
        for (Distributor distributor : Database.getInstance().getDistributorsMap().values()) {
            if (!distributor.getIsBankrupt()) {
                if ((distributor.getBudget() - distributor.computeMonthlyCost()) < 0) {
                    distributor.payMonthlyCost();
                    distributor.declareBancrupcy();
                } else {
                    distributor.payMonthlyCost();
                }
            }
        }
    }

    //updating the profit and contract price for distributors
    private void distributorsUpdatePrices(boolean isInitialRound) {
        for (Distributor distributor : Database.getInstance().getDistributorsMap().values()) {
            if (isInitialRound) {
                distributor.computeProductionCost();
            }
            distributor.updateProfit();
            distributor.updateFinalContractPrice();
        }
        Database.getInstance().updateDistributorsList();
    }

    //finding new producers for the distributors whose producers have changed their state
    private void distributorsFindNewProducers(boolean isInitialRound) {
        Collections.sort(Database.getInstance().getDistributors(), new IdDistComparator());
        for (Distributor crtDistributor : Database.getInstance().getDistributors()) {
            if (!isInitialRound) {
                if (crtDistributor.isMustFindNewProducers() && !crtDistributor.getIsBankrupt()) {
                    crtDistributor.findNewProducers();
                    crtDistributor.computeProductionCost();
                }
            } else {
                crtDistributor.findNewProducers();
            }
        }
    }

    //adding changes for distributors
    private void distributorsAddChanges(int month) {
        for (DistributorChange distributorChange : Database.getInstance().getMonthlyUpdates().get(month).getDistributorChangesList()) {
            int id = distributorChange.getId();
            int infCost = distributorChange.getInfrastructureCost();
            Database.getInstance().getDistributorsMap().get(id).setInfrastructureCost(infCost);
        }
    }

    //bankrupt distributors are eliminated from the producers' list
    private void eliminateBankruptDistributors() {
        for (Distributor distributor : Database.getInstance().getDistributorsMap().values()) {
            if (distributor.getIsBankrupt()) {
                for (Producer producer : distributor.getCurrentProducers()) {
                    producer.getCurrentDistributors().remove(distributor);
                    producer.deleteObserver(distributor);
                }
            }
        }
    }

    //producers are updated with the monthly changes
    private void producersAddChanges(int month) {
        for (ProducerChange producerChange : Database.getInstance().getMonthlyUpdates().get(month).getProducerChangesList()) {
            int crtId = producerChange.getId();
            int energy = producerChange.getEnergyPerDistributor();
            Database.getInstance().getProducersMap().get(crtId).setEnergyPerDistributor(energy);
            Database.getInstance().getProducersMap().get(crtId).notifyDistributors();
        }
        Database.getInstance().updateProducersList();
    }

    //producers get their monthly status
    private void producersUpdateStats(int month) {
        Collections.sort(Database.getInstance().getProducers(), new IdProdComparator());
        for (Producer producer : Database.getInstance().getProducers()) {
            producer.updateMonthlyStats(month + 1);
        }
    }

    /**
     * function that plays the initial round of the game
     */
    public void initialRound() {

        distributorsFindNewProducers(true);
        distributorsUpdatePrices(true);

        consumersChangeContract(true);
        consumersGetSalary();
        consumersPayBills();

        distributorsPayCosts();

        eliminateBankruptConsumers();
        eliminateBankruptConsumers();
    }


    /**
     * function that plays the simulation for the given number of turns
     */
    public void playSimulation() {
        for (int i = 0; i < Database.getInstance().getNumberOfTurns(); i++) {

            distributorsAddChanges(i);
            distributorsUpdatePrices(false);

            consumersAddChanges(i);
            consumersChangeContract(false);
            consumersGetSalary();
            consumersPayBills();

            distributorsPayCosts();

            producersAddChanges(i);

            distributorsFindNewProducers(false);

            producersUpdateStats(i);

            eliminateBankruptConsumers();
            eliminateBankruptDistributors();
        }
    }
}
