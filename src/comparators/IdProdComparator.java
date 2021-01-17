package comparators;

import entities.Producer;

import java.util.Comparator;

public final class IdProdComparator implements Comparator<Producer> {
    @Override
    public int compare(Producer o1, Producer o2) {
        return Integer.compare(o1.getId(), o2.getId());
    }
}
