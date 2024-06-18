package grasp;

import estrutura.Armazem;
import estrutura.Cliente;
import java.util.List;

public class Grasp {

    // Função para calcular o custo total de alocação para um cliente dado uma atribuição de armazéns
    public static double calcularCustoTotal(List<Cliente> clientes, List<Armazem> armazens, int[] alocacao) {
        double custoTotal = 0.0;
        for (int i = 0; i < alocacao.length; i++) {
            int clienteId = alocacao[i];
            if (clienteId >= 0 && clienteId < clientes.size() && i < armazens.size()) {
                Cliente cliente = clientes.get(clienteId);
                custoTotal += cliente.getCusto_alocacao(i) + armazens.get(i).getCusto_fixo();
            }
        }
        return custoTotal;
    }


    // Função de busca local para melhorar a solução
    public static int[] buscaLocal(List<Cliente> clientes, List<Armazem> armazens, int[] solucao) {
        boolean melhorou;
        do {
            melhorou = false;
            for (int i = 0; i < solucao.length; i++) {
                for (int j = i + 1; j < solucao.length; j++) {
                    // Tenta trocar a alocação entre os armazéns i e j
                    int temp = solucao[i];
                    solucao[i] = solucao[j];
                    solucao[j] = temp;

                    double custoNovo = calcularCustoTotal(clientes, armazens, solucao);

                    if (custoNovo < calcularCustoTotal(clientes, armazens, solucao)) {
                        // Aceita a solução se o custo diminuir
                        melhorou = true;
                    } else {
                        // Reverte a troca se não houver melhoria
                        temp = solucao[i];
                        solucao[i] = solucao[j];
                        solucao[j] = temp;
                    }
                }
            }
        } while (melhorou);

        return solucao;
    }


    // Função principal para resolver o problema usando GRASP
    public static void resolver(List<Cliente> clientes, List<Armazem> armazens, int maxIteracoes, double alpha, int[] sba) {
        int[] melhorSolucao = null;
        double melhorCusto = Double.POSITIVE_INFINITY;

        for (int iteracao = 0; iteracao < maxIteracoes; iteracao++) {
            int[] solucao = sba.clone();
            solucao = buscaLocal(clientes, armazens, solucao);

            double custo = calcularCustoTotal(clientes, armazens, solucao);
            if (custo < melhorCusto) {
                melhorCusto = custo;
                melhorSolucao = solucao.clone();
            }

            System.out.println("Iteração: " + iteracao + " | Custo: " + custo);
        }


        for (int i = 0; i < melhorSolucao.length; i++) {
            System.out.println("Armazém " + i + " alocado ao Cliente " + melhorSolucao[i]);
        }
        System.out.println("Melhor custo encontrado: " + melhorCusto);
    }
}
