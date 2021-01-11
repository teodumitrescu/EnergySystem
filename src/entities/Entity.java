package entities;

import java.util.Observer;

public abstract class Entity implements Observer {
        private int id;
        private int budget;
        private boolean isBankrupt = false;

        public final int getId() {
                return id;
        }

        public final void setId(final int id) {
                this.id = id;
        }

        public final int getBudget() {
                return budget;
        }

        public final void setBudget(final int initialBudget) {
                this.budget = initialBudget;
        }

        public final boolean getIsBankrupt() {
                return isBankrupt;
        }

        public final void setIsBankrupt(final boolean isBankrupt) {
                this.isBankrupt = isBankrupt;
        }
}

