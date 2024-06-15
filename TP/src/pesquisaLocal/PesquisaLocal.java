package pesquisaLocal;

import estrutura.Armazem;
import estrutura.Cliente;

import java.util.List;
import java.util.Random;

public class PesquisaLocal {

    // Função para calcular o custo total de alocação para um cliente dado uma atribuição de armazéns
    public static double calcularCustoTotal(Cliente cliente, List<Armazem> armazens, int[] alocacao) {
        double custoTotal = 0.0;
        for (int i = 0; i < alocacao.length; i++) {
            if (alocacao[i] >= 0) {
                custoTotal += cliente.getCusto_alocacao()[i] * armazens.get(alocacao[i]).getCusto_fixo();
            }
        }
        return custoTotal;
    }

    // Função para encontrar o índice do armazém com o menor custo fixo disponível para um cliente
    public static int encontrarMelhorArmazem(Cliente cliente, List<Armazem> armazens, int clienteIndex, int[] alocacaoAtual) {
        int melhorArmazem = -1;
        double melhorCusto = Double.POSITIVE_INFINITY;

        for (int i = 0; i < armazens.size(); i++) {
            if (cliente.getProcura()[i] > 0 && (alocacaoAtual[i] == -1 || cliente.getCusto_alocacao()[i] * armazens.get(i).getCusto_fixo() < melhorCusto)) {
                melhorArmazem = i;
                melhorCusto = cliente.getCusto_alocacao()[i] * armazens.get(i).getCusto_fixo();
            }
        }

        return melhorArmazem;
    }

    // Gera uma solução inicial aleatória
    public static int[] gerarSolucaoInicialAleatoria(List<Armazem> armazens, List<Cliente> clientes) {
        Random random = new Random();
        int[] alocacaoInicial = new int[armazens.size()];

        for (Cliente cliente : clientes) {
            for (int j = 0; j < cliente.getSize_demand(); j++) {
                int armazemAleatorio = random.nextInt(armazens.size());
                alocacaoInicial[armazemAleatorio] = cliente.getId();
            }
        }

        return alocacaoInicial;
    }

    // Algoritmo de pesquisa local para encontrar a alocação mínima de custo
    public static void pesquisaLocal(List<Armazem> armazens, List<Cliente> clientes) {
        Random random = new Random();
        int numIteracoesSemMelhoria = 0;
        int maxIteracoesSemMelhoria = 10;

        // Gerar solução inicial aleatória
        int[] alocacaoAtual = gerarSolucaoInicialAleatoria(armazens, clientes);

        // Iteração principal da pesquisa local
        while (numIteracoesSemMelhoria < maxIteracoesSemMelhoria) {
            boolean melhorou = false;

            // Para cada cliente, tentar realocar para um armazém com menor custo
            for (int c = 0; c < clientes.size(); c++) {
                Cliente cliente = clientes.get(c);
                int[] procura = cliente.getProcura();

                // Encontrar o melhor armazém para realocar o cliente
                int melhorArmazem = encontrarMelhorArmazem(cliente, armazens, c, alocacaoAtual);

                if (melhorArmazem != -1) {
                    // Realocar cliente para o melhor armazém encontrado
                    for (int i = 0; i < procura.length; i++) {
                        if (alocacaoAtual[i] == c) {
                            alocacaoAtual[i] = -1;
                        }
                    }
                    alocacaoAtual[melhorArmazem] = c;
                    melhorou = true;
                }
            }

            // Critério de parada: se não houver melhoria após várias iterações
            if (!melhorou) {
                numIteracoesSemMelhoria++;
            } else {
                numIteracoesSemMelhoria = 0;
            }

            // Impressão para depuração
            System.out.println("Iteração: " + numIteracoesSemMelhoria);
            for (int i = 0; i < armazens.size(); i++) {
                System.out.println("Cliente alocado no Armazem " + i + ": " + alocacaoAtual[i]);
            }
        }

        // Mostrar resultados finais
        for (int i = 0; i < armazens.size(); i++) {
            System.out.println("Cliente alocado no Armazem " + i + ": " + alocacaoAtual[i]);
        }

        double custoTotal = 0.0;
        for (int c = 0; c < clientes.size(); c++) {
            Cliente cliente = clientes.get(c);
            custoTotal += calcularCustoTotal(cliente, armazens, alocacaoAtual);
        }
        System.out.println("Custo total: " + custoTotal);
    }
}
