package comparators;

import entities.Distributor;

import java.util.Comparator;

//comparator used to sort distributors by id
public final class IdDistComparator implements Comparator<Distributor> {
    @Override
    public int compare(Distributor o1, Distributor o2) {
        return Integer.compare(o1.getId(), o2.getId());
    }
}
