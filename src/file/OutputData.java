package file;

import outputcomponents.ConsumerOutput;
import outputcomponents.DistributorOutput;
import outputcomponents.ProducerOutput;

import java.util.List;

public final class OutputData {
    private List<ConsumerOutput> consumers;
    private List<DistributorOutput> distributors;
    private List<ProducerOutput> energyProducers;

    public OutputData(List<ConsumerOutput> consumers, List<DistributorOutput> distributors,
                      List<ProducerOutput> energyProducers) {
        this.consumers = consumers;
        this.distributors = distributors;
        this.energyProducers = energyProducers;
    }

    public List<ConsumerOutput> getConsumers() {
        return consumers;
    }

    public void setConsumers(List<ConsumerOutput> consumers) {
        this.consumers = consumers;
    }

    public List<DistributorOutput> getDistributors() {
        return distributors;
    }

    public void setDistributors(List<DistributorOutput> distributors) {
        this.distributors = distributors;
    }

    public List<ProducerOutput> getEnergyProducers() {
        return energyProducers;
    }

    public void setEnergyProducers(List<ProducerOutput> energyProducers) {
        this.energyProducers = energyProducers;
    }

    @Override
    public String toString() {
        return consumers.toString() + '\n' + distributors.toString() + '\n'
                + energyProducers.toString() + '\n';
    }
}
