package file;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import entities.Producer;
import outputcomponents.ConsumerOutput;
import outputcomponents.DistributorClient;
import outputcomponents.DistributorOutput;
import core.Database;
import entities.Consumer;
import entities.Distributor;
import outputcomponents.ProducerOutput;
import comparators.ClientsOutputComparator;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class OutputWriter {

    private final String filepath;

    public OutputWriter(final String filepath) {
        this.filepath = filepath;
    }


    /**
     * function to create the output for the consumers
     * @return a list with the consumers info for output
     */
    public List<ConsumerOutput> initializeConsumers() {

        List<ConsumerOutput> consumerOutputs = new ArrayList<>();

        for (Consumer consumer : Database.getInstance().getConsumersMap().values()) {
            ConsumerOutput newCons = new ConsumerOutput(consumer.getId(),
                    consumer.getIsBankrupt(), consumer.getBudget());
            consumerOutputs.add(newCons);
        }
        return consumerOutputs;
    }

    /**
     * function to create the output for the distributors
     * @return a list with the distributors info for output
     */
    public List<DistributorOutput> initializeDistributors() {

        List<DistributorOutput> distributorOutputs = new ArrayList<>();

        for (Distributor distributor : Database.getInstance().getDistributorsMap().values()) {

            List<DistributorClient> clients = new ArrayList<>();
            for (Consumer consumer : distributor.getCurrentConsumers()) {
                DistributorClient client = new DistributorClient(consumer.getId(),
                        consumer.getCurrentBill(), consumer.getMonthsLeftFromContract());
                clients.add(client);
            }
            Collections.sort(clients, new ClientsOutputComparator());
            DistributorOutput newDist = new DistributorOutput(distributor.getId(),
                    distributor.getEnergyNeededKW(), distributor.getFinalContractPrice(),
                    distributor.getBudget(), distributor.getProducerStrategy().getLabel(),
                    distributor.getIsBankrupt(), clients);
            distributorOutputs.add(newDist);
        }
        return distributorOutputs;
    }

    /**
     * function to create the output for the producers
     * @return a list with the producers info for output
     */
    public List<ProducerOutput> initializeProducers() {

        List<ProducerOutput> producerOutputs = new ArrayList<>();

        for (Producer producer : Database.getInstance().getProducersMap().values()) {
            ProducerOutput newProd = new ProducerOutput(producer.getId(),
                    producer.getMaxDistributors(), producer.getPriceKW(),
                    producer.getEnergyType().getLabel(), producer.getEnergyPerDistributor(),
                    producer.getMonthlyStats());
            producerOutputs.add(newProd);
        }
        return producerOutputs;
    }

    /**
     * function that writes a the output in a JSON structure in a file
     * @throws IOException
     */
    public void writeData() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        File file = new File(filepath);
        ObjectWriter writer = objectMapper.writer(new DefaultPrettyPrinter());

        List<ConsumerOutput> consumerOutputs = this.initializeConsumers();
        List<DistributorOutput> distributorOutputs = this.initializeDistributors();
        List<ProducerOutput> producerOutputs = this.initializeProducers();

        OutputData output = new OutputData(consumerOutputs, distributorOutputs, producerOutputs);
        writer.writeValue(file, output);
    }
}
