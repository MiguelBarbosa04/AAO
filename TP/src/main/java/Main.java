import algoritmosExatos.UWLP;
import algoritmosExatos.exato;
import estrutura.Armazem;
import estrutura.Cliente;

import heuristicosConstrutivos.AlgoritmoGuloso;
import heuristicosPesquisaLocal.HillClimbing;
import metaheuristicos.antColonyOptimization.AntColonyOptimization;
import metaheuristicos.geneticAlgorithm.GeneticAlgorithm;
import metaheuristicos.geneticAlgorithm.Solution;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static metaheuristicos.antColonyOptimization.AntColonyOptimization.calcularCustoTotal;


public class Main {

    private static double[][] matrix(List<Armazem> armazens, List<Cliente> clients) {
        double[][] matrix = new double[clients.size()][armazens.size()];

        for (int i = 0; i < clients.size(); i++) {
            System.out.println("Cliente ID: " + clients.get(i).getId());
            for (int j = 0; j < armazens.size(); j++) {
                matrix[i][j] = clients.get(i).getCusto_alocacao(j);
                System.out.println("Armazem ID: " + j);
                System.out.print(matrix[i][j] + "\t");
            }
            System.out.println();
        }

        return matrix;
    }

    public static void main(String[] args) {
        List<Armazem> armazem = new ArrayList<Armazem>();
        List<Cliente> cliente = new ArrayList<Cliente>();
        GeneticAlgorithm ga = new GeneticAlgorithm(armazem, cliente);
        AlgoritmoGuloso greedy = new AlgoritmoGuloso();
        HillClimbing hillClimbing = new HillClimbing();

        try {
            CarregarDados.lerDados(armazem, cliente, "src/main/java/data/uncap/capa.txt");
            //CarregarDados.lerDados(armazem, cliente, "src/main/java/data/M/Kcapmo1.txt");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        long startTime = System.nanoTime();


        //Metaheuristico - Genetic Algorithm
/*
        Solution bestSolution = ga.run();

        System.out.println("Alocações dos clientes:");
        for (int i = 0; i < bestSolution.assignments.length; i++) {
            System.out.println("Cliente " + i + " alocado ao armazém " + bestSolution.assignments[i]);
        }
        System.out.println("Melhor custo encontrado: " + bestSolution.totalCost);
*/

        //Heuristico Construtivo - Greedy
        greedy.executar(armazem, cliente);

        //Solução inicial = solução do greedy
        int[] solution = greedy.executar(armazem, cliente);
        //Verificar o custo da solução inicial
        int currentCost = hillClimbing.evaluateSolution(solution, armazem, cliente);

        boolean improved;
        do {
            improved = false;
            for (int i = 0; i < solution.length; i++) {
                for (int j = 0; j < armazem.size(); j++) {
                    if (solution[i] != j) {
                        int[] newSolution = solution.clone();
                        newSolution[i] = j;
                        int newCost = hillClimbing.evaluateSolution(newSolution, armazem, cliente);
                        if (newCost < currentCost) {
                            solution = newSolution;
                            currentCost = newCost;
                            improved = true;
                        }
                    }
                }
            }
        } while (improved);

/*
        int numFormigas = 16;
        int numIteracoes = 1000;
        double alfa = 1.5;
        double beta = 1.0;
        double evaporacao = 0.6;
        double feromonioInicial = 1.4;

        AntColonyOptimization aco = new AntColonyOptimization(numFormigas, numIteracoes, alfa, beta, evaporacao, feromonioInicial);
        int[] melhorSolucao = aco.resolver(armazem, cliente);

        // Mostrar resultados finais
        System.out.println("Melhor solução encontrada:");
        for (int i = 0; i < melhorSolucao.length; i++) {
            System.out.println("Cliente " + i + " alocado no Armazem " + melhorSolucao[i]);
        }
        double melhorCusto = calcularCustoTotal(cliente, armazem, melhorSolucao);
        System.out.println("Custo total: " + melhorCusto);
*/

        // Medir o tempo de término
        long endTime = System.nanoTime();
        long totalTime = endTime - startTime;

// Converter o tempo total de nanossegundos para segundos
        double totalTimeInSeconds = totalTime / 1_000_000_000.0; // Convertendo nanossegundos para segundos

// Arredondar o tempo para três casas decimais
        double roundedTimeInSeconds = Math.round(totalTimeInSeconds * 1000.0) / 1000.0;

        System.out.println("Tempo de execução: " + roundedTimeInSeconds + " segundos");




    }
}

