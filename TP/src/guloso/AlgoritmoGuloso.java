package guloso;
import estrutura.Armazem;
import estrutura.Cliente;

import java.util.List;
import java.util.Random;

public class AlgoritmoGuloso {

    // Método para encontrar uma solução inicial usando um algoritmo guloso
    public static void executar(List<Armazem> armazens, List<Cliente> clientes, int[] alocacaoAtual) {
        Random random = new Random();

        for (Cliente cliente : clientes) {
            int[] custoAlocacao = cliente.getCusto_alocacao();
            int[] procura = cliente.getProcura();

            // Encontrar o melhor armazém para cada unidade de procura do cliente
            for (int i = 0; i < procura.length; i++) {
                if (procura[i] > 0) {
                    int melhorArmazem = -1;
                    double melhorCusto = Double.POSITIVE_INFINITY;

                    for (int j = 0; j < armazens.size(); j++) {
                        if (alocacaoAtual[j] == -1 || custoAlocacao[i] + armazens.get(j).getCusto_fixo() < melhorCusto) {
                            melhorArmazem = j;
                            melhorCusto = custoAlocacao[i] + armazens.get(j).getCusto_fixo();
                        }
                    }

                    alocacaoAtual[melhorArmazem] = cliente.getId();
                    procura[i]--;
                }
            }
        }
    }
}
