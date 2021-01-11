package comparators;

import outputcomponents.DistributorClient;

import java.util.Comparator;

public final class ClientsOutputComparator implements Comparator<DistributorClient> {
    @Override
    public int compare(final DistributorClient o1, final DistributorClient o2) {
        if (o1.getRemainedContractMonths() == o2.getRemainedContractMonths()) {
         return Integer.compare(o1.getConsumerId(), o2.getConsumerId());
        }
        return Integer.compare(o1.getRemainedContractMonths(), o2.getRemainedContractMonths());
    }
}
