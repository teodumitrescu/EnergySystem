package changecomponents;

import entities.Consumer;

import java.util.List;

//class that stores all the updates in a month
public final class Update {
    private List<Consumer> newConsumersList;
    private List<DistributorChange> distributorChangesList;
    private List<ProducerChange> producerChangesList;

    public Update(final List<Consumer> newConsumersList,
                  final List<DistributorChange> distributorChangesList,
                  final List<ProducerChange> producerChangesList) {
        this.newConsumersList = newConsumersList;
        this.distributorChangesList = distributorChangesList;
        this.producerChangesList = producerChangesList;
    }

    public List<DistributorChange> getDistributorChangesList() {
        return distributorChangesList;
    }

    public void setDistributorChangesList(final List<DistributorChange> distributorChangesList) {
        this.distributorChangesList = distributorChangesList;
    }

    public List<Consumer> getNewConsumersList() {
        return newConsumersList;
    }

    public void setNewConsumersList(final List<Consumer> newConsumersList) {
        this.newConsumersList = newConsumersList;
    }

    public List<ProducerChange> getProducerChangesList() {
        return producerChangesList;
    }

    public void setProducerChangesList(List<ProducerChange> producerChangesList) {
        this.producerChangesList = producerChangesList;
    }
}
