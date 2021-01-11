package strategies;

import entities.Distributor;

public final class StrategyExecutor {
    private Strategy s;

    public StrategyExecutor(Strategy s) {
        this.s = s;
    }

    public Strategy getS() {
        return s;
    }

    public void setS(Strategy s) {
        this.s = s;
    }

    void getStr(Distributor distributor) {
        s.applyStrategy(distributor);
    }
}
