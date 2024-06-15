package guloso;

import estrutura.Armazem;
import estrutura.Cliente;

import java.util.List;

public class AlgoritmoGuloso {

    // Método para encontrar uma solução inicial usando um algoritmo guloso
    public static void executar(List<Armazem> armazens, List<Cliente> clientes) {
        int[] alocacaoAtual = new int[armazens.size()];
        int[] capacidadeRestante = new int[armazens.size()];

        // Inicializar alocação e capacidade restante
        for (int i = 0; i < armazens.size(); i++) {
            alocacaoAtual[i] = -1; // Nenhum cliente alocado inicialmente
            capacidadeRestante[i] = armazens.get(i).capacidade;
        }

        for (Cliente cliente : clientes) {

            // Encontrar o melhor armazém para cada unidade de procura do cliente
            for (int i = 0; i < cliente.procura.length; i++) {
                if (cliente.procura[i] > 0) {
                    int demanda = cliente.procura[i];
                    int melhorArmazem = -1;
                    double melhorCusto = Double.POSITIVE_INFINITY;

                    for (int j = 0; j < armazens.size(); j++) {
                        if (capacidadeRestante[j] >= demanda && cliente.custo_alocacao[i] + armazens.get(j).getCusto_fixo() < melhorCusto) {
                            melhorArmazem = j;
                            melhorCusto = cliente.custo_alocacao[i] + armazens.get(j).getCusto_fixo();
                        }
                    }

                    if (melhorArmazem != -1) {
                        alocacaoAtual[melhorArmazem] = cliente.getId();
                        capacidadeRestante[melhorArmazem] -= demanda;
                        System.out.println("Cliente " + cliente.getId() + " alocado ao Armazém " + (melhorArmazem + 1) + " com custo " + melhorCusto);
                    } else {
                        System.out.println("Cliente " + cliente.getId() + " não pôde ser alocado.");
                    }
                }
            }
        }
        System.out.println("Capacidade restante dos armazéns: ");
        for (int i = 0; i < capacidadeRestante.length; i++) {
            System.out.println("Armazém " + (i + 1) + ": " + capacidadeRestante[i]);
        }
    }
}
