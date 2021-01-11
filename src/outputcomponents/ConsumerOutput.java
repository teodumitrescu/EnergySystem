package outputcomponents;

//class used as the consumers component of the output class
public final class ConsumerOutput {
    private int id;
    private boolean isBankrupt;
    private int budget;

    public ConsumerOutput(final int id, final boolean isBankrupt, final int budget) {
        this.id = id;
        this.isBankrupt = isBankrupt;
        this.budget = budget;
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

    @Override
    public String toString() {
        return "ConsumerOutput{"
                + "id=" + id
                + ", isBankrupt=" + isBankrupt
                + ", budget=" + budget
                + '}';
    }
}
