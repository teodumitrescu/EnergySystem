package comparators;

import entities.Distributor;

import java.util.Comparator;

//comparator used to sort distributors by their contract price
public final class ContractPriceComparator implements Comparator<Distributor> {
    @Override
    public int compare(final Distributor o1, final Distributor o2) {
        return Integer.compare(o1.getFinalContractPrice(), o2.getFinalContractPrice());
    }
}
