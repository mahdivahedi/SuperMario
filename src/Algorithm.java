import java.util.ArrayList;
import java.util.List;

public class Algorithm {

    private final Population population;
    private final List<Integer> maxFitness;
    private final List<Integer> minFitness;
    private final List<Double> avgFitness;

    public Algorithm(String level) {
        this.population = new Population(level);
        maxFitness = new ArrayList<>();
        minFitness = new ArrayList<>();
        avgFitness = new ArrayList<>();
    }

    public void runAlgo() {
        while (true) {
            population.geneticGeneration();
            population.nextGeneration();
            population.sort();
            minFitness.add(population.getPopulation().get(population.getPopulation().size() - 1).fitnessFunction());
            maxFitness.add(population.getPopulation().get(0).fitnessFunction());
            avgFitness.add(population.getPopulation().stream().mapToInt(Population.Chromosome::fitnessFunction).average().orElse(0));
            if (population.variance() < 0.01) {
                population.setGoals();
                if (population.getGoals().size() != 0)
                    break;
            }
        }
        population.getGoals().forEach(c -> {
            for (int e : c.getGene()) {
                System.out.print(e);
            }
            System.out.println(" fitness = " + c.fitnessFunction());
        });
    }

    public List<Integer> getMaxFitness() {
        return maxFitness;
    }

    public List<Integer> getMinFitness() {
        return minFitness;
    }

    public List<Double> getAvgFitness() {
        return avgFitness;
    }
}
