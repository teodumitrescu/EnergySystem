package entities;

import useful.Constants;

public final class Factory {

    public Factory() {
    }

    private static Factory instance = null;

    /**
     * function to make the class of Singleton design
     * @return the singular instance of the factory
     */
    public static Factory getInstance() {
        if (instance == null) {
            instance = new Factory();
        }
        return instance;
    }

    /**
     * function that creates entities as consumers or distributors
     * @param type consumer or distributor
     * @param id the given entity id
     * @param initialBudget the given budget
     * @return a new entity
     */
    public Entity createEntity(final String type, final int id, final int initialBudget) {

        if (type.equals(Constants.CONSUMER)) {
            return new Consumer(id, initialBudget);
        } else {
            return new Distributor(id, initialBudget);
        }
    }
}
