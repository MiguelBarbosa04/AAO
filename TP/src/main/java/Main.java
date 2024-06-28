import estrutura.Armazem;
import estrutura.Cliente;
import estrutura.Solution;
import heuristicos.AlgoritmoGuloso;
import heuristicosPesquisaLocal.HillClimbing;
import heuristicosPesquisaLocal.TabuSearch;
import heuristicosPesquisaLocal.NewHillClimbing;
import metaheuristicos.geneticAlgorithm.GeneticAlgorithm;
//import metaheuristicos.geneticAlgorithm.Solution;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;




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
        AlgoritmoGuloso greedy = new AlgoritmoGuloso();
        //HillClimbing hillClimbing = new HillClimbing();
        
        try {
            CarregarDados.lerDados(armazem, cliente,"C:/Users/josed/OneDrive/Documentos/NetBeansProjects/AAO/TP/src/main/java/data/uncap/capa.txt" );
            //CarregarDados.lerDados(armazem, cliente, "src/main/java/data/M/Kcapmo1.txt");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        GeneticAlgorithm ga = new GeneticAlgorithm(armazem, cliente);
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
        // greedy.executar(armazem, cliente);
   /*
        //Solução inicial = solução do greedy
        int[] solution = greedy.executar(armazem, cliente);
        //Verificar o custo da solução inicial
        int currentCost = hillClimbing.evaluateSolution(solution, armazem, cliente);
        //Loop para verificar se encontra uma solucao com um custo menor
        
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
*/

        NewHillClimbing hillClimbing = new NewHillClimbing(armazem, cliente);
        int[] solution = greedy.executar(armazem, cliente);

// Executar Hill Climbing
        int[] bestAllocation = hillClimbing.hillClimbing(solution);
        double bestCost = hillClimbing.calculateCost(bestAllocation);

        // Mostrar resultados
        System.out.println("Melhor solução encontrada: ");
        System.out.println("Custo: " + bestCost);
        System.out.println("Alocação: " + Arrays.toString(bestAllocation));

/*
        int[] solution = greedy.executar(armazem, cliente);
        // Configurações do Tabu Search
        int maxIter = 1000;
        int tabuTenure = 10;

        TabuSearch tabuSearch = new TabuSearch(armazem, cliente, 100, 30);
        Solution bestSolution = tabuSearch.tabuSearch(solution);

        System.out.println("Melhor solução encontrada: ");
        System.out.println("Custo: " + bestSolution.getCost());
        System.out.println("Alocação: " + Arrays.toString(bestSolution.getAllocation()));

*/

        // Medir o tempo computacional
        long endTime = System.nanoTime();
        long totalTime = endTime - startTime;

        // Converter o tempo total de nanossegundos para segundos
        double totalTimeInSeconds = totalTime / 1_000_000_000.0; 

        // Arredondar o tempo para três casas decimais
        double roundedTimeInSeconds = Math.round(totalTimeInSeconds * 1000.0) / 1000.0;

        System.out.println("Tempo de execução: " + roundedTimeInSeconds + " segundos");




    }
}

