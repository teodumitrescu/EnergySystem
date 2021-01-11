import core.Database;
import core.Simulator;
import file.InputParse;
import file.OutputWriter;


public final class Main {

    private Main() {
    }

    /**
     * Call the main checker and the coding style checker
     * @param args the in filepath and out filepath
     * @throws Exception in case of exceptions to reading / writing
     */
    public static void main(final String[] args) throws Exception {
        InputParse parse = new InputParse(args[0]);
        Database.getInstance().emptyDatabase();
        parse.readInitialData();

        Simulator.getInstance().initialRound();
        Simulator.getInstance().playSimulation();


        OutputWriter writer = new OutputWriter(args[1]);
        writer.writeData();
    }
}
