import java.util.*;

import static java.lang.Math.random;


public class Population {

    private final String level;
    private List<Chromosome> population;
    private final List<Chromosome> goals;

    public Population(String level) {
        this.level = level;
        setChromosomes();
        this.goals = new ArrayList<>();
    }

    public void setChromosomes() {
        int capacity = 200;
        population = new ArrayList<>(capacity);
        while (capacity-- > 0) {
            population.add(new Chromosome());
        }
    }

    public void geneticGeneration() {
        sort();
        int mid = population.size() >> 1;
        for (int i = 0; i < mid; i++)
            population.set(population.size() - i - 1, population.get(i).crossover(population.get(i + 1)));
    }

    public void nextGeneration() {
        sort();
        int mid = population.size() / 2;
        var totalWeight = 0;
        var weights = new double[mid];
        for (int i = 0; i < mid; i++)
            totalWeight += population.get(i).fitnessFunction();
        for (int i = 0; i < mid; i++)
            weights[i] = population.get(i).fitnessFunction() / (double) totalWeight + (i == 0 ? 0 : weights[i - 1]);
        for (int i = population.size() - 1; i >= mid; i--)
            population.set(i, parent(weights).crossover(parent(weights)));
    }

    private Chromosome parent(double[] weights) {
        double rand = random();
        int counter = -1;
        while (weights[++counter] <= rand);
        return population.get(counter);
    }

    public List<Chromosome> getPopulation() {
        return population;
    }

    public double variance() {
        sort();
        List<Integer> fitness = population.subList(0, 10).stream().map(Chromosome::fitnessFunction).toList();
        double average = 0;
        for (Integer f : fitness) {
            average += (double) f;
        }
        average /= fitness.size();
        double finalAverage = average;
        return Math.sqrt(fitness.stream().mapToDouble(f -> (finalAverage - f) * (finalAverage - f)).average().orElse(0));
    }

    public void sort() {
        Collections.sort(population);
    }

    public void setGoals() {
        population.forEach(c -> {
            if (c.win)
                goals.add(c);
        });
        Collections.sort(goals);
    }

    public List<Chromosome> getGoals() {
        return goals;
    }

    public class Chromosome implements Comparable<Chromosome> {

        private final int[] gene;
        private boolean win;

        public Chromosome() {
            this.gene = new int[level.length()];
            setGene();
            this.win = false;
        }

        public Chromosome(int[] gene) {
            this.gene = gene;
        }

        private void setGene() {
            Arrays.setAll(this.gene, c -> (int) (3 * random()));
        }

        public Chromosome crossover(Chromosome chromosome) {
            int[] temp = new int[level.length()];
            int randomIndex = (int) (random() * level.length());
            int index = 0;
            while (index < randomIndex)
                temp[index] = this.gene[index++];
            while (index < level.length())
                temp[index] = chromosome.gene[index++];
            Chromosome crossover = new Chromosome(temp);
            crossover.mutation();
            return crossover;
        }

        private void mutation() {
            while (!(random() > 0.1)) {
                gene[(int) (random() * level.length())] = (int) (random() * 3);
            }
        }

        public int fitnessFunction() {
            int fitness = 0;
            boolean isWin = true;
            for (int i = 0; i < gene.length - 1; i++) {
                int move = gene[i];
                char c = level.charAt(i + 1);
                if (isWin)
                    isWin = !((c == 'L' && move != 2) || (c == 'G' && move != 1));
                if (c == 'G') {
                    fitness += move != 1 ? -2 : 2;
                }else if (c == 'L') {
                    fitness += move != 2 ? -2 : 1;
                }else if (c == 'M') {
                    fitness += move == 0 ? 3 : -1;
                }else if (c == '_') {
                    fitness += move == 0 ? 1 : -1;
                }
            }
            if (isWin) {
                this.win = true;
                fitness += 5;
            }else
                this.win = false;
            return Math.max(fitness, 0);
        }

        public int[] getGene() {
            return gene;
        }

        @Override
        public int compareTo(Chromosome o) {
            return o.fitnessFunction() - fitnessFunction();
        }
    }
}
