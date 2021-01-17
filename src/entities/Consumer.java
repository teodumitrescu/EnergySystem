package entities;

import core.Database;
import useful.Constants;

import java.util.Observable;

public final class Consumer extends Entity {
    private int monthlyIncome;
    private int currentBill = 0;
    private int penalty = 0;
    private int monthsLeftFromContract;
    private Distributor currentDistributor;
    private Distributor oldDistributor;
    private int mustChangeDistributor = 0;

    public Consumer(final int id, final int initialBudget) {
        this.setId(id);
        this.setBudget(initialBudget);
    }

    public int getMonthlyIncome() {
        return monthlyIncome;
    }

    public void setMonthlyIncome(final int monthlyIncome) {
        this.monthlyIncome = monthlyIncome;
    }

    public int getMonthsLeftFromContract() {
        return monthsLeftFromContract;
    }

    public void setMonthsLeftFromContract(final int monthsLeftFromContract) {
        this.monthsLeftFromContract = monthsLeftFromContract;
    }

    public int getMustChangeDistributor() {
        return mustChangeDistributor;
    }

    public void setMustChangeDistributor(final int mustChangeDistributor) {
        this.mustChangeDistributor = mustChangeDistributor;
    }

    public Distributor getOldDistributor() {
        return oldDistributor;
    }

    public void setOldDistributor(final Distributor oldDistributor) {
        this.oldDistributor = oldDistributor;
    }

    public int getCurrentBill() {
        return currentBill;
    }

    public void setCurrentBill(final int currentBill) {
        this.currentBill = currentBill;
    }

    public int getPenalty() {
        return penalty;
    }

    public void setPenalty(final int penalty) {
        this.penalty = penalty;
    }

    public Distributor getCurrentDistributor() {
        return currentDistributor;
    }

    public void setCurrentDistributor(final Distributor currentDistributor) {
        this.currentDistributor = currentDistributor;
    }

    /**
     * adds the monthly salary to the consumer's budget
     */
    public void updateBudget() {
        int oldValue = this.getBudget();
        this.setBudget(oldValue + monthlyIncome);
    }

    /**
     * computes the total cost a consumer has to pay in a month
     * @return the sum to be paid by the consumer
     */
    public int computePayroll() {
//        if (penalty != 0) {
//            if (oldDistributor != null) {
//                if (!oldDistributor.equals(currentDistributor)) {
//                    return (int) Math.round(Math.floor(Constants.TOTAL_PERCENT * penalty));
//                }
//            }
//        }
        return (int) Math.round(Math.floor(Constants.TOTAL_PERCENT * penalty) + currentBill);
    }

    /**
     * adds a penalty to the consumer when he cannot pay the bill
     */
    public void updateBillsPenalty() {
        penalty = currentBill;
    }

    /**
     * the function finds the distributor with the lowest
     * contract price that is not bankrupt and moves the
     * consumer to the new found distributor
     */
    public void findNewDistributor() {
        if (currentDistributor != null) {
           oldDistributor = currentDistributor;
            oldDistributor.getCurrentConsumers().remove(this);
            int oldValue = oldDistributor.getNumberOfClients();
            oldDistributor.setNumberOfClients(oldValue - 1);
        }
        for (Distributor distributor : Database.getInstance().getDistributors()) {
            if (!distributor.getIsBankrupt()) {
                currentDistributor = distributor;
                break;
            }
        }
        currentBill = currentDistributor.getFinalContractPrice();
        monthsLeftFromContract = currentDistributor.getContractLength();
        int oldValue = currentDistributor.getNumberOfClients();
        currentDistributor.setNumberOfClients(oldValue + 1);
        currentDistributor.getCurrentConsumers().add(this);

        mustChangeDistributor = 0;
    }

    /**
     * function in which the consumer pays the bills and
     * penalties to his distributors
     */
    public void payBills() {
        int value;
        if (penalty != 0) {
            value = oldDistributor.getBudget();
            oldDistributor.setBudget(value + computePayroll());
            if (oldDistributor.equals(currentDistributor)) {
                penalty = 0;
            } else {
                updateBillsPenalty();
                setOldDistributor(currentDistributor);
            }
        } else {
            value = currentDistributor.getBudget();
            currentDistributor.setBudget(value + computePayroll());
        }
        value = this.getBudget();
        this.setBudget((value - computePayroll()));

//        int value = this.currentDistributor.getBudget();
//        this.currentDistributor.setBudget(value + currentBill);
//        if (penalty != 0) {
//            value = this.oldDistributor.getBudget();
//            this.oldDistributor.setBudget(value
//                    + (int) Math.floor(Constants.TOTAL_PERCENT * penalty));
//            penalty = 0;
//        }
//        value = this.getBudget();
//        this.setBudget((value - this.computePayroll()));
    }

    @Override
    public void update(Observable o, Object arg) {
    }
}
