import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        Algorithm a = new Algorithm("____G_____");
        a.runAlgo();
        System.out.println();
        a.getMaxFitness().forEach(c -> System.out.print(c + " "));
        System.out.println();
        a.getMinFitness().forEach(c -> System.out.print(c + " "));
        System.out.println();
        a.getAvgFitness().forEach(c -> System.out.print(c + " "));

        BufferedWriter writer = new BufferedWriter(new FileWriter(".\\src\\plot.txt"));
        StringBuilder stringBuilder = new StringBuilder();
        for(int i = 0; i < a.getMaxFitness().size(); i++) {
            stringBuilder.append(a.getMaxFitness().get(i));
            stringBuilder.append(" ");
        }
        stringBuilder.append("\n");
        for(int i = 0; i < a.getMaxFitness().size(); i++) {
            stringBuilder.append(a.getMinFitness().get(i));
            stringBuilder.append(" ");
        }
        stringBuilder.append("\n");
        for(int i = 0; i < a.getMaxFitness().size(); i++) {
            stringBuilder.append(a.getAvgFitness().get(i));
            stringBuilder.append(" ");
        }
        writer.write(stringBuilder.toString());
        writer.close();
        System.out.println("\n" + a.getAvgFitness().size());
        ExecutePython executePython = new ExecutePython(".\\src\\plot.py");
        executePython.run();
    }
}
