package metaheuristicos.antColonyOptimization;

import estrutura.Armazem;
import estrutura.Cliente;

import java.util.List;
import java.util.Random;

public class AntColonyOptimization {

    private int numFormigas;
    private int numIteracoes;
    private double alfa;
    private double beta;
    private double evaporacao;
    private double feromonioInicial;
    private double[][] trilhaFeromonio;

    public AntColonyOptimization(int numFormigas, int numIteracoes, double alfa, double beta, double evaporacao, double feromonioInicial) {
        this.numFormigas = numFormigas;
        this.numIteracoes = numIteracoes;
        this.alfa = alfa;
        this.beta = beta;
        this.evaporacao = evaporacao;
        this.feromonioInicial = feromonioInicial;
    }

    // Função para calcular o custo total de alocação para um cliente dado uma atribuição de armazéns
    public static double calcularCustoTotal(List<Cliente> clientes, List<Armazem> armazens, int[] alocacao) {
        double custoTotal = 0.0;
        for (int i = 0; i < clientes.size(); i++) {
            Cliente cliente = clientes.get(i);
            int armazemIndex = alocacao[i];

            if (armazemIndex >= 0) {
                if (armazens.get(armazemIndex).isOpen()) {
                    custoTotal += cliente.getCusto_alocacao(armazemIndex);

                } else {
                    custoTotal += cliente.getCusto_alocacao(armazemIndex) + armazens.get(armazemIndex).getCusto_fixo();
                    armazens.get(armazemIndex).setOpen(true);
                }
            }
        }
        return custoTotal;
    }

    // Inicializa as trilhas de feromônio
    private void inicializarFeromonios(int numClientes, int numArmazens) {
        trilhaFeromonio = new double[numClientes][numArmazens];
        for (int i = 0; i < numClientes; i++) {
            for (int j = 0; j < numArmazens; j++) {
                trilhaFeromonio[i][j] = feromonioInicial;
            }
        }
    }

    // Construir uma solução para uma formiga
    private int[] construirSolucao(List<Cliente> clientes, List<Armazem> armazens) {
        Random random = new Random();
        int numClientes = clientes.size();
        int numArmazens = armazens.size();
        int[] solucao = new int[numClientes];

        for (int i = 0; i < numClientes; i++) {
            Cliente cliente = clientes.get(i);
            double[] probabilidades = new double[numArmazens];
            double somaProbabilidades = 0.0;

            // Calcular as probabilidades para escolher o armazém
            for (int j = 0; j < numArmazens; j++) {
                probabilidades[j] = Math.pow(trilhaFeromonio[i][j], alfa) * Math.pow(1.0 / cliente.getCusto_alocacao(j), beta);
                somaProbabilidades += probabilidades[j];
            }

            // Selecionar o armazém com base nas probabilidades
            double valorAleatorio = random.nextDouble() * somaProbabilidades;
            double somaAcumulada = 0.0;
            for (int j = 0; j < numArmazens; j++) {
                somaAcumulada += probabilidades[j];
                if (somaAcumulada >= valorAleatorio) {
                    solucao[i] = j;
                    break;
                }
            }
        }

        return solucao;
    }

    // Atualizar as trilhas de feromônio
    private void atualizarFeromonios(List<int[]> solucoes, List<Cliente> clientes, List<Armazem> armazens) {
        // Evaporação do feromônio
        for (int i = 0; i < trilhaFeromonio.length; i++) {
            for (int j = 0; j < trilhaFeromonio[i].length; j++) {
                trilhaFeromonio[i][j] *= (1 - evaporacao);
            }
        }

        // Adição de novo feromônio com base nas soluções
        for (int[] solucao : solucoes) {
            double custo = calcularCustoTotal(clientes, armazens, solucao);
            for (int i = 0; i < solucao.length; i++) {
                trilhaFeromonio[i][solucao[i]] += 1.0 / custo;
            }
        }
    }

    // Algoritmo de Otimização por Colônia de Formigas
    public int[] resolver(List<Armazem> armazens, List<Cliente> clientes) {
        int numClientes = clientes.size();
        int numArmazens = armazens.size();

        inicializarFeromonios(numClientes, numArmazens);

        int[] melhorSolucao = null;
        double melhorCusto = Double.POSITIVE_INFINITY;

        for (int iteracao = 0; iteracao < numIteracoes; iteracao++) {
            List<int[]> solucoes = new java.util.ArrayList<>();
            for (int k = 0; k < numFormigas; k++) {
                int[] solucao = construirSolucao(clientes, armazens);
                solucoes.add(solucao);
                double custo = calcularCustoTotal(clientes, armazens, solucao);
                if (custo < melhorCusto) {
                    melhorCusto = custo;
                    melhorSolucao = solucao;
                }
            }

            atualizarFeromonios(solucoes, clientes, armazens);

            System.out.println("Iteração: " + iteracao + ", Melhor Custo: " + melhorCusto);
        }

        return melhorSolucao;
    }
}

