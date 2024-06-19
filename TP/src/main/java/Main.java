import algoritmosExatos.UWLP;
import algoritmosExatos.exato;
import estrutura.Armazem;
import estrutura.Cliente;
import grasp.Grasp;
import heuristicosConstrutivos.AlgoritmoGuloso;
import heuristicosPesquisaLocal.HillClimbing;
import metaheuristicos.antColonyOptimization.AntColonyOptimization;

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

        try {
            CarregarDados.lerDados(armazem, cliente, "src/main/java/data/uncap/cap71.txt");
            //CarregarDados.lerDados(armazem, cliente, "src/main/java/data/M/Kcapmo1.txt");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        //Exato - Simplex
/*
        UWLP uwlp = new UWLP();
         boolean[] solution = uwlp.solveUWLP(armazem, cliente);

        // Exibindo a solução
        System.out.println("Solução do UWLP:");
        for (int i = 0; i < armazem.size(); i++) {
            Armazem armazens = armazem.get(i);
            System.out.println("Armazém " + (i + 1) + " está " + (armazens.isOpen() ? "aberto" : "fechado"));
        }
        System.out.println("Custo total: " + uwlp.calculateTotalCost(solution, armazem, cliente));
*/


            // Definir capacidades dos armazéns e custos de atribuição
        double[] capacidades = new double[armazem.size()];
        double[][] custos = new double[cliente.size()][armazem.size()];
        double[] demandas = new double[cliente.size()];

        for (int i = 0; i < armazem.size(); i++) {
            capacidades[i] = armazem.get(i).getCapacidade();
        }

        for (int i = 0; i < cliente.size(); i++) {
            for (int j = 0; j < armazem.size(); j++) {
                custos[i][j] = cliente.get(i).getCusto_alocacao(j);
            }
            // A demanda de cada cliente é a soma das procuras em todos os armazéns
            demandas[i] += cliente.get(i).getProcura();
        }


            exato Exato = new exato();

            // Resolver o ILP com OR-Tools
            int[] solucao = Exato.solveILP(cliente, armazem, custos, capacidades, demandas);

            // Avaliar a solução encontrada
            double custoTotal = Exato.calculateTotalCost(cliente, armazem, solucao, custos);

            // Mostrar resultado
            System.out.println("Solução ótima encontrada:");
            for (int i = 0; i < cliente.size(); i++) {
                System.out.println("Cliente " + cliente.get(i).getId() + " -> Armazem " + solucao[i]);
            }
            System.out.println("Custo total: " + custoTotal);

     /*


        algoritmosExatos.SimplexAlgorithm simplex = new algoritmosExatos.SimplexAlgorithm();
        simplex.solveUWLP(cliente, armazem);

        //Metaheuristico - Genetic Algorithm
        GeneticAlgorithm ga = new GeneticAlgorithm(armazem, cliente);
        Solution bestSolution = ga.run();

        System.out.println("Melhor custo encontrado: " + bestSolution.totalCost);
        System.out.println("Alocações dos clientes:");
        for (int i = 0; i < bestSolution.assignments.length; i++) {
            System.out.println("Cliente " + i + " alocado ao armazém " + bestSolution.assignments[i]);
        }
*/

        //Heuristica de Pesquisa Local - Pesquisa Local
        //pesquisaLocal(armazem, cliente);

        //Heuristico Construtivo - Greedy

/*
        AlgoritmoGuloso greedy = new AlgoritmoGuloso();
        greedy.executar(armazem, cliente);
*/
/*



        int[] solution = greedy.executar(armazem, cliente);


        HillClimbing hillClimbing = new HillClimbing();

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


*/

        /*
        int[] alocacaoAtual = new int[armazem.size()];
        AlgoritmoGuloso.executar(armazem, cliente);

        // Mostrar resultados da solução inicial
        System.out.println("Resultado do Algoritmo Guloso:");
        for (int i = 0; i < armazem.size(); i++) {
            System.out.println("Cliente alocado no Armazem " + i + ": " + alocacaoAtual[i]);
        }
        */

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
/*
        // Parâmetros do GRASP
        int maxIteracoes = 1000; // Número máximo de iterações
        double alpha = 0.5; // Grau de aleatoriedade (0 <= alpha <= 1)

        int[] sba = greedy.executar(armazem, cliente);
        Grasp grasp = new Grasp();
        // Resolver o problema usando GRASP
        grasp.resolver(cliente, armazem, maxIteracoes, alpha, sba);

*/
        //




/*
        // Configuração da função objetivo e restrições
        int qtdArmazens = armazem.size();
        int qtdClientes = cliente.size();

        // Função objetivo: minimizar o custo total
        double[] custos = new double[qtdArmazens * qtdClientes];
        for (int i = 0; i < qtdClientes; i++) {
            Cliente clientes = cliente.get(i);
            for (int j = 0; j < qtdArmazens; j++) {
                custos[i * qtdArmazens + j] = clientes.getCusto_alocacao(j);
            }
        }

        // Restrições de capacidade dos armazéns e demanda dos clientes
        double[][] A = new double[qtdArmazens + qtdClientes][qtdArmazens * qtdClientes];
        double[] b = new double[qtdArmazens + qtdClientes];

        // Capacidade dos armazéns
        for (int j = 0; j < qtdArmazens; j++) {
            for (int i = 0; i < qtdClientes; i++) {
                A[j][i * qtdArmazens + j] = 1;
            }
            b[j] = armazem.get(j).getCapacidade();
        }

        // Demanda dos cliente
        for (int i = 0; i < qtdClientes; i++) {
            for (int j = 0; j < qtdArmazens; j++) {
                A[qtdArmazens + i][i * qtdArmazens + j] = 1;
            }
            b[qtdArmazens + i] = cliente.get(i).getProcura(0); // Assumindo que a procura é a mesma para todos os armazéns
        }

        // Resolver o problema utilizando o Simplex
        simplex simplex = new simplex(A, b, custos);
        double[] solucao = simplex.solve();

        // Imprimir a solução
        System.out.println("Solução:");
        double custoTotal = 0;
        for (int i = 0; i < qtdClientes; i++) {
            Cliente clientes = cliente.get(i);
            for (int j = 0; j < qtdArmazens; j++) {
                double quantidade = solucao[i * qtdArmazens + j];
                if (quantidade > 0) {
                    System.out.println("Quantidade transportada do armazém " + (j + 1) + " para o cliente " + (i + 1) + ": " + quantidade);
                    custoTotal += clientes.getCusto_alocacao(j);
                }
            }
        }
        System.out.println("Custo total mínimo: " + custoTotal);
        */

        //double[][] matriz = matrix(armazem, cliente);
/*
        double[] max = new double[armazem.size()];
        for (int i = 0; i < armazem.size(); i++) {
            max[i] = armazem.get(i).getCapacidade();
        }
        */




    }
}

