package heuristicos;

import estrutura.Armazem;
import estrutura.Cliente;

import java.util.List;

/**
 * The type Algoritmo guloso.
 */
public class AlgoritmoGuloso {


    /**
     * Executar int [ ].
     *
     * @param armazens the armazens
     * @param clientes the clientes
     * @return the int [ ]
     */
    public int[] executar(List<Armazem> armazens, List<Cliente> clientes) {
        int numClientes = clientes.size();
        int numArmazens = armazens.size();
        int[] solution = new int[numClientes];
        double custoTotal = 0;

            // Inicializar a array da "solution" como -1
        for (int i = 0; i < numClientes; i++) {
            solution[i] = -1;
        }

        // Atribuir para cada cliente o melhor armazem
        for (int i = 0; i < numClientes; i++) {
            Cliente cliente = clientes.get(i);

            int melhorArmazem = -1;
            double menorCusto = Integer.MAX_VALUE;

            for (int j = 0; j < numArmazens; j++) {
                Armazem armazem = armazens.get(j);

                double custoAtual = 0;
                //Caso o armazem esteja aberta o "custoAtual" será o custo do cliente
                // senão o "custoAtual" será o custo do cliente mais o custo fixo do armazém
                if (armazem.isOpen()) {
                    custoAtual = (cliente.getCusto_alocacao(j));
                } else {
                    custoAtual = (cliente.getCusto_alocacao(j) + armazem.getCusto_fixo());
                }


                if (custoAtual < menorCusto) {
                    menorCusto = custoAtual;
                    melhorArmazem = j;
                }
            }

            if (melhorArmazem != -1) {
                solution[i] = melhorArmazem;
                armazens.get(melhorArmazem).setOpen(true);
                custoTotal += menorCusto;
            }
        }

        // Solução e o seu custo Total
        System.out.println("Solução de alocação:");
        for (int i = 0; i < solution.length; i++) {
            System.out.println("Cliente " + i + " -> Armazém " + solution[i] + " Custo de Alocação " + clientes.get(i).getCusto_alocacao(solution[i]-1));
        }
        System.out.println("Custo Total: " + custoTotal);

        return solution;
    }
}
