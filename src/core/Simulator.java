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

    /**
     * function that plays the initial round of the game
     */
    public void initialRound() {

        Collections.sort(Database.getInstance().getProducers(), new IdProdComparator());

        for (Distributor crtDistributor : Database.getInstance().getDistributors()) {
            crtDistributor.findNewProducers();
        }

        //updating the profit and contract price for distributors
        for (Distributor distributor : Database.getInstance().getDistributorsMap().values()) {
            distributor.computeProductionCost();
            distributor.updateProfit();
            distributor.updateFinalContractPrice();
        }
        Database.getInstance().updateDistributorsList();

        //attributing distributors to consumers
        for (Consumer crtConsumer : Database.getInstance().getConsumersMap().values()) {
            crtConsumer.findNewDistributor();
        }

        //consumers are getting their salary and updating their remainder month number
        for (Consumer consumer : Database.getInstance().getConsumersMap().values()) {
            consumer.updateBudget();
            int value = consumer.getMonthsLeftFromContract();
            consumer.setMonthsLeftFromContract(value - 1);
        }

        //consumers pay their bills
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

        //distributors pay their costs
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

        //bankrupt consumers are removed from their distributors
        for (Consumer consumer : Database.getInstance().getConsumersMap().values()) {
            if (consumer.getIsBankrupt()) {
                if (consumer.getCurrentDistributor().getCurrentConsumers().contains(consumer)) {
                    consumer.getCurrentDistributor().getCurrentConsumers().remove(consumer);
                    int value = consumer.getCurrentDistributor().getNumberOfClients();
                    consumer.getCurrentDistributor().setNumberOfClients(value - 1);
                }
            }
        }

        for (Distributor distributor : Database.getInstance().getDistributorsMap().values()) {
            if (distributor.getIsBankrupt()) {
                for (Producer producer : distributor.getCurrentProducers()) {
                    producer.getCurrentDistributors().remove(distributor);
                    producer.deleteObserver(distributor);
                }
            }
        }

    }



    /**
     * function that plays the simulation for the given number of turns
     */
    public void playSimulation() {
        for (int i = 0; i < Database.getInstance().getNumberOfTurns(); i++) {

            //adding changes for distributors
            for (DistributorChange distributorChange : Database.getInstance().getMonthlyUpdates().get(i).getDistributorChangesList()) {
                int id = distributorChange.getId();
                int infCost = distributorChange.getInfrastructureCost();
                Database.getInstance().getDistributorsMap().get(id).setInfrastructureCost(infCost);
            }

            //updating the profit and contract price for distributors
            for (Distributor distributor : Database.getInstance().getDistributorsMap().values()) {
                //distributor.computeProductionCost();
                distributor.updateProfit();
                distributor.updateFinalContractPrice();
            }
            Database.getInstance().updateDistributorsList();

            //adding new consumers and attributing them distributors
            for (Consumer consumer : Database.getInstance().getMonthlyUpdates().get(i).getNewConsumersList()) {
                Database.getInstance().getConsumersMap().put(consumer.getId(), consumer);
                consumer.findNewDistributor();
            }

            //consumers switch (or not) to a new found contract
            for (Consumer consumer : Database.getInstance().getConsumersMap().values()) {
                if (!consumer.getIsBankrupt()) {
                    if (consumer.getMonthsLeftFromContract() == 0) {
                        consumer.findNewDistributor();
                    } else if (consumer.getMustChangeDistributor() == 1) {
                        consumer.findNewDistributor();
                    }
                }
            }

            //consumers are getting their salary and updating their remainder month number
            for (Consumer consumer : Database.getInstance().getConsumersMap().values()) {
                if (!consumer.getIsBankrupt()) {
                    consumer.updateBudget();
                    int value = consumer.getMonthsLeftFromContract();
                    consumer.setMonthsLeftFromContract(value - 1);
                }
            }

            //consumers pay their bills
            for (Consumer consumer : Database.getInstance().getConsumersMap().values()) {
                if (!consumer.getIsBankrupt()) {
                    if ((consumer.getBudget() - consumer.computePayroll()) < 0) {
                        if (consumer.getPenalty() != 0) {
                            consumer.setIsBankrupt(true);
                        } else {
                            //consumer.payBills();
                            consumer.updateBillsPenalty();
                        }
                    } else {
                        consumer.payBills();
                    }
                }
            }

            //distributors pay their costs
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

            for (ProducerChange producerChange : Database.getInstance().getMonthlyUpdates().get(i).getProducerChangesList()) {
                int crtId = producerChange.getId();
                int energy = producerChange.getEnergyPerDistributor();
                Database.getInstance().getProducersMap().get(crtId).setEnergyPerDistributor(energy);
                Database.getInstance().getProducersMap().get(crtId).notifyDistributors();
            }
            Database.getInstance().updateProducersList();

            Collections.sort(Database.getInstance().getDistributors(), new IdDistComparator());
            //finding new producers for the distributors whose producers have changed their state
            for (Distributor crtDistributor : Database.getInstance().getDistributors()) {
                if (crtDistributor.isMustFindNewProducers() && !crtDistributor.getIsBankrupt()) {
                    crtDistributor.findNewProducers();
                    crtDistributor.computeProductionCost();
                }
            }


            Collections.sort(Database.getInstance().getProducers(), new IdProdComparator());
            for (Producer producer : Database.getInstance().getProducers()) {
                producer.updateMonthlyStats(i + 1);
            }

            //bankrupt consumers are eliminated from the distributors list
            for (Consumer consumer : Database.getInstance().getConsumersMap().values()) {
                if (consumer.getIsBankrupt()) {
                    if (consumer.getCurrentDistributor().getCurrentConsumers().contains(consumer)) {
                        consumer.getCurrentDistributor().getCurrentConsumers().remove(consumer);
                        int value = consumer.getCurrentDistributor().getNumberOfClients();
                        consumer.getCurrentDistributor().setNumberOfClients(value - 1);
                    }
                }
            }

            for (Distributor distributor : Database.getInstance().getDistributorsMap().values()) {
                if (distributor.getIsBankrupt()) {
                    for (Producer producer : distributor.getCurrentProducers()) {
                        producer.getCurrentDistributors().remove(distributor);
                        producer.deleteObserver(distributor);
                    }
                }
            }
        }
    }
}
