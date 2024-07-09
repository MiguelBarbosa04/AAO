package metaheuristicos.geneticAlgorithm;
import estrutura.Armazem;
import estrutura.Cliente;

import java.util.*;

/**
 * The type Genetic algorithm.
 */
public class GeneticAlgorithm {
    private static final int POPULATION_SIZE = 500;  //Nº de soluções a correr
    private static final int NUM_GENERATIONS = 2000; //Quantidade de gerações a serem criadas
    private static final double MUTATION_RATE = 0.12; //Chance para ocorrer mutação genética


    private List<Solution> population;
    private List<Armazem> armazens;
    private List<Cliente> clientes;
    private Solution bestSolutionEver; // Melhor solução global

    /**
     * Construtor que inicializa o algoritmo genetic
     *
     * @param armazens the armazens
     * @param clientes the clientes
     */
    public GeneticAlgorithm(List<Armazem> armazens, List<Cliente> clientes) {
        this.armazens = armazens;
        this.clientes = clientes;
        this.population = initializePopulation(clientes.size(), armazens.size());
        evaluatePopulation(); // avalia a população inicial
        bestSolutionEver = findBestSolutionInGeneration(population); // Inicializa a melhor solução global
    }

    /**
     * Executa o algoritmo genetic
     *
     * @return the solution
     */
    public Solution run() {
        for (int generation = 0; generation < NUM_GENERATIONS; generation++) {
            List<Solution> newPopulation = new ArrayList<>();
            
            // Gerar nova população através de seleção, crossover e mutação
            for (int i = 0; i < POPULATION_SIZE; i++) {
                Solution parent1 = selectParent();
                Solution parent2 = selectParent();
                Solution son = crossover(parent1, parent2);
                mutate(son, armazens.size());
                son.calculateCost(armazens, clientes);
                newPopulation.add(son);
            }
            population = newPopulation;

            // Encontrar a melhor solução da geração atual
            Solution bestInGeneration = findBestSolutionInGeneration(population);
            if (bestInGeneration.totalCost < bestSolutionEver.totalCost) {
                bestSolutionEver = bestInGeneration; // Atualiza a melhor solução global se encontrar uma melhor
            }
            System.out.println("Geração " + generation + ": Melhor custo na geração = " + bestInGeneration.totalCost + ", Melhor custo global = " + bestSolutionEver.totalCost);
        }

        return bestSolutionEver;
    }

    //Iniciar a população com soluções aleatórias
    private List<Solution> initializePopulation(int numClientes, int numArmazens) {
        List<Solution> population = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < POPULATION_SIZE; i++) {
            Solution solution = new Solution(numClientes);
            for (int j = 0; j < numClientes; j++) {
                solution.assignments[j] = random.nextInt(numArmazens);
            }
            population.add(solution);
        }
        return population;
    }

    // Avalia a popualação calculando o custo de cada solução
    private void evaluatePopulation() {
        for (Solution solution : population) {
            solution.calculateCost(armazens, clientes);
        }
    }

    // Seleciona um pai usando o método por torneio
    private Solution selectParent() {
        // Seleção por torneio
        Random random = new Random();
        Solution best = population.get(random.nextInt(POPULATION_SIZE));
        for (int i = 0; i < 4; i++) {
            Solution challenger = population.get(random.nextInt(POPULATION_SIZE));
            if (challenger.totalCost < best.totalCost) {
                best = challenger;
            }
        }
        return best;
    }

    // Crossover entre dois pais para gerar um filho
    private Solution crossover(Solution parent1, Solution parent2) {
        Solution son = new Solution(parent1.assignments.length);
        Random random = new Random();
        for (int i = 0; i < parent1.assignments.length; i++) {
            // Escolhe aleatóriamente o gene de um dos pais
            if (random.nextBoolean()) {
                son.assignments[i] = parent1.assignments[i];
            } else {
                son.assignments[i] = parent2.assignments[i];
            }
        }
        return son;
    }

    // Mutação genética para alterar aleatoriamente os genes de uma solução
    private void mutate(Solution solution, int numArmazens) {
        Random random = new Random();
        for (int i = 0; i < solution.assignments.length; i++) {
            if (random.nextDouble() < MUTATION_RATE) {
                solution.assignments[i] = random.nextInt(numArmazens);
            }
        }
    }

    // Ecnontra a melhor solução na geração atual
    private Solution findBestSolutionInGeneration(List<Solution> population) {
        Solution best = population.get(0);
        for (Solution solution : population) {
            if (solution.totalCost < best.totalCost) {
                best = solution;
            }
        }
        return best;
    }
}