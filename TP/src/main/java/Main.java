import algoritmoTeste.AntColonyOptimization;
import estrutura.Armazem;
import estrutura.Cliente;
import geneticAlgorithm.GeneticAlgorithm;
import geneticAlgorithm.Solution;
import grasp.Grasp;
import guloso.AlgoritmoGuloso;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static algoritmoTeste.AntColonyOptimization.calcularCustoTotal;
import static pesquisaLocal.PesquisaLocal.pesquisaLocal;

public class Main {

    public static void main(String[] args) {

        List<Armazem> armazem = new ArrayList<Armazem>();
        List<Cliente> cliente = new ArrayList<Cliente>();

        Path currentPath = Paths.get("");
        System.out.println(currentPath.toAbsolutePath().toString());
        try {
            CarregarDados.lerDados(armazem, cliente, "src/main/java/cap71.txt");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        /*
        for (Armazem armazens : armazem) {
            System.out.println(armazens);
        }

        for (Cliente clientes : cliente) {
            System.out.println(clientes);
        }
*/
/*
        GeneticAlgorithm ga = new GeneticAlgorithm(armazem, cliente);
        Solution bestSolution = ga.run();

        System.out.println("Melhor custo encontrado: " + bestSolution.totalCost);
        System.out.println("Alocações dos clientes:");
        for (int i = 0; i < bestSolution.assignments.length; i++) {
            System.out.println("Cliente " + i + " alocado ao armazém " + bestSolution.assignments[i]);
        }
*/

        //pesquisaLocal(armazem, cliente);

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

        int numFormigas = 32;
        int numIteracoes = 100;
        double alfa = 2.0;
        double beta = 2.0;
        double evaporacao = 0.5;
        double feromonioInicial = 1.0;

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
        int maxIteracoes = 100; // Número máximo de iterações
        double alpha = 0.6; // Grau de aleatoriedade (0 <= alpha <= 1)

        int[] sba = AlgoritmoGuloso.executar(armazem, cliente);
        // Resolver o problema usando GRASP
        Grasp.resolver(cliente, armazem, maxIteracoes, alpha, sba);
*/


        // Resolver o problema de alocação usando SimplexSolver
        simplex solver = new simplex(armazem.size(), cliente.size());
        solver.resolverProblema(armazem, cliente);

    }
}

