package file;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import changecomponents.DistributorChange;
import changecomponents.ProducerChange;
import changecomponents.Update;
import core.Database;

import entities.Producer;
import entities.Consumer;
import entities.Distributor;
import entities.EnergyType;
import entities.Factory;
import strategies.EnergyChoiceStrategyType;
import useful.Constants;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class InputParse {

    private final String filepath;

    public InputParse(final String filepath) {
        this.filepath = filepath;
    }

    public void parseConsumers(JsonNode inputNode, Factory f) throws IOException {

        JsonNode consNode = inputNode.get(Constants.INITIAL_DATA).get(Constants.CONSUMERS);

        if (!consNode.isNull()) {

            for (JsonNode consumer : consNode) {
                int id = consumer.get(Constants.CONSUMER_ID).asInt();
                int initBudget = consumer.get(Constants.INITIAL_BUDGET).asInt();
                Consumer cons = (Consumer) f.createEntity(Constants.CONSUMER, id, initBudget);
                cons.setMonthlyIncome(consumer.get(Constants.MONTHLY_INCOME).asInt());

                Database.getInstance().getConsumersMap().put(id, cons);
            }
        }
    }

    public void parseDistributors(JsonNode inputNode, Factory f) throws IOException {

        JsonNode distNode = inputNode.get(Constants.INITIAL_DATA).get(Constants.DISTRIBUTORS);

        if (!distNode.isNull()) {
            for (JsonNode distributor : distNode) {
                int id = distributor.get(Constants.DISTRIBUTOR_ID).asInt();
                int initBudget = distributor.get(Constants.INITIAL_BUDGET).asInt();
                Distributor newDist = (Distributor) f.createEntity(Constants.DISTRIBUTOR, id, initBudget);
                newDist.setContractLength(distributor.get(Constants.CONTRACT_LENGTH).asInt());
                newDist.setInfrastructureCost(distributor.get(Constants.INITIAL_INFRASTRUCTURE_COST).asInt());
                newDist.setEnergyNeededKW(distributor.get(Constants.ENERGY_NEEDED_KW).asInt());

                String aux = distributor.get(Constants.PRODUCER_STRATEGY).toString();
                aux = aux.substring(1, aux.length() - 1);
                newDist.setProducerStrategy(EnergyChoiceStrategyType.valueOf(aux));

                Database.getInstance().getDistributors().add(newDist);
                Database.getInstance().getDistributorsMap().put(id, newDist);
            }
        }
    }

    public void parseProducers(JsonNode inputNode) throws IOException {

        JsonNode prodNode = inputNode.get(Constants.INITIAL_DATA).get(Constants.PRODUCERS);

        if (!prodNode.isNull()) {
            for (JsonNode producer : prodNode) {
                String aux = producer.get(Constants.ENERGY_TYPE).toString();
                aux = aux.substring(1, aux.length() - 1);
                Producer newProd = new Producer(producer.get(Constants.PRODUCER_ID).asInt(),
                        EnergyType.valueOf(aux),
                        producer.get(Constants.MAX_DISTRIBUTORS).asInt(),
                        producer.get(Constants.PRICE_KW).asDouble(),
                        producer.get(Constants.ENERGY_PER_DISTRIBUTOR).asInt());

                Database.getInstance().getProducersMap().put(newProd.getId(), newProd);
                Database.getInstance().getProducers().add(newProd);
            }
        }
    }

    public void parseNewConsumers(JsonNode update, List<Consumer> newConsumersList, Factory f) {

        JsonNode newConsumersNode = update.get(Constants.NEW_CONSUMERS);

        if (!newConsumersNode.isNull()) {
            for (JsonNode crtCons : newConsumersNode) {
                int id = crtCons.get(Constants.CONSUMER_ID).asInt();
                int initBudget = crtCons.get(Constants.INITIAL_BUDGET).asInt();
                Consumer c = (Consumer) f.createEntity(Constants.CONSUMER, id, initBudget);
                c.setMonthlyIncome(crtCons.get(Constants.MONTHLY_INCOME).asInt());
                newConsumersList.add(c);
            }
        }
    }

    public void parseDistributorChanges(JsonNode update,
                                        List<DistributorChange> distributorChangesList, Factory f) {

        JsonNode distributorChangesNode = update.get(Constants.DISTRIBUTOR_CHANGES);

        if (!distributorChangesNode.isNull()) {
            for (JsonNode crtChange : distributorChangesNode) {
                int id = crtChange.get(Constants.DISTRIBUTOR_ID).asInt();
                int infraCost = crtChange.get(Constants.INFRASTRUCTURE_COST).asInt();
                DistributorChange newDistributorChange = new DistributorChange(id, infraCost);
                distributorChangesList.add(newDistributorChange);
            }
        }

    }

    public void parseProducerChanges(JsonNode update, List<ProducerChange> producerChangesList) {

        JsonNode producerChangesNode = update.get(Constants.PRODUCER_CHANGES);

        if (!producerChangesNode.isNull()) {
            for (JsonNode crtChange : producerChangesNode) {
                int id = crtChange.get(Constants.PRODUCER_ID).asInt();
                int energyPerDistributor = crtChange.get(Constants.ENERGY_PER_DISTRIBUTOR).asInt();
                ProducerChange newProducerChange = new ProducerChange(id, energyPerDistributor);
                producerChangesList.add(newProducerChange);
            }
        }
    }

    /**
     * function that reads a JSON from a file and adds the information in
     * a database class
     * @throws IOException
     */
    public void readInitialData() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        File file = new File(filepath);
        Factory f = new Factory();
        JsonNode inputNode = objectMapper.readTree(file);

        int numberOfTurns = inputNode.get(Constants.NUMBER_OF_TURNS).asInt();
        Database.getInstance().setNumberOfTurns(numberOfTurns);

        parseConsumers(inputNode, f);
        parseDistributors(inputNode, f);
        parseProducers(inputNode);

        JsonNode monthlyUpdatesNode = inputNode.get(Constants.MONTHLY_UPDATES);

        for (JsonNode update : monthlyUpdatesNode) {

            List<Consumer> newConsumersList = new ArrayList<>();
            List<DistributorChange> distributorChangesList = new ArrayList<>();
            List<ProducerChange> producerChangesList = new ArrayList<>();

            parseNewConsumers(update, newConsumersList, f);
            parseDistributorChanges(update, distributorChangesList, f);
            parseProducerChanges(update, producerChangesList);

            Update newUpdate = new Update(newConsumersList,
                                distributorChangesList, producerChangesList);
            Database.getInstance().getMonthlyUpdates().add(newUpdate);
        }
    }
}
