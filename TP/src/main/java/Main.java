import algoritmoTeste.AntColonyOptimization;
import estrutura.Armazem;
import estrutura.Cliente;
import geneticAlgorithm.GeneticAlgorithm;
import geneticAlgorithm.Solution;
import grasp.Grasp;
import guloso.AlgoritmoGuloso;
import guloso.CustomGreedy;
import java.util.Arrays;


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

        int[] sba = CustomGreedy.executar(armazem, cliente);
        // Resolver o problema usando GRASP
        Grasp.resolver(cliente, armazem, maxIteracoes, alpha, sba);
*/

        AlgoritmoGuloso.executar(armazem, cliente);
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

