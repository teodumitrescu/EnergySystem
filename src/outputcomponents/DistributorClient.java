package outputcomponents;

//class that stores an active contract of a distributor
public final class DistributorClient {
    private int consumerId;
    private int price;
    private int remainedContractMonths;

    public DistributorClient(final int consumerId, final int price,
                             final int remainedContractMonths) {
        this.consumerId = consumerId;
        this.price = price;
        this.remainedContractMonths = remainedContractMonths;
    }

    public int getConsumerId() {
        return consumerId;
    }

    public void setConsumerId(final int consumerId) {
        this.consumerId = consumerId;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(final int price) {
        this.price = price;
    }

    public int getRemainedContractMonths() {
        return remainedContractMonths;
    }

    public void setRemainedContractMonths(final int remainedContractMonths) {
        this.remainedContractMonths = remainedContractMonths;
    }

    @Override
    public String toString() {
        return "DistributorClient{" + "consumerId=" + consumerId
                + ", price=" + price
                + ", remainedContractMonths="
                + remainedContractMonths + '}';
    }
}
