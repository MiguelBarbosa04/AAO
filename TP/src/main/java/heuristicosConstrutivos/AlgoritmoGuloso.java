package heuristicosConstrutivos;

import estrutura.Armazem;
import estrutura.Cliente;

import java.util.List;

public class AlgoritmoGuloso {

    public int[] executar(List<Armazem> armazens, List<Cliente> clientes) {
        int numClientes = clientes.size();
        int numArmazens = armazens.size();
        int[] solution = new int[numClientes];
        double custoTotal = 0;

        // Initialize the solution array with -1
        for (int i = 0; i < numClientes; i++) {
            solution[i] = -1;
        }

        // Assign each client to the best warehouse
        for (int i = 0; i < numClientes; i++) {
            Cliente cliente = clientes.get(i);

            int melhorArmazem = -1;
            double menorCusto = Integer.MAX_VALUE;

            for (int j = 0; j < numArmazens; j++) {
                Armazem armazem = armazens.get(j);


                double custoAtual = 0;
                if (armazem.isOpen()) {
                    custoAtual = (cliente.getCusto_alocacao(j));
                } else {
                    custoAtual = (cliente.getCusto_alocacao(j) + armazem.getCusto_fixo());
                }

                //double custoAtual = (cliente.getCusto_alocacao(j) + armazem.getCusto_fixo());
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

        // Output the solution and total cost
        System.out.println("Solução de alocação:");
        for (int i = 0; i < solution.length; i++) {
            System.out.println("Cliente " + i + " -> Armazém " + solution[i] + " Custo de Alocação " + clientes.get(i).getCusto_alocacao(solution[i]-1));
        }
        System.out.println("Custo Total: " + custoTotal);

        return solution;
    }
}
