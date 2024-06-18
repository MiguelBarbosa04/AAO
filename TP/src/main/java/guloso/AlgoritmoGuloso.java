package guloso;

import estrutura.Armazem;
import estrutura.Cliente;

import java.util.List;

public class AlgoritmoGuloso {

    // Método para encontrar uma solução inicial usando um algoritmo guloso
    public static int[] executar(List<Armazem> armazens, List<Cliente> clientes) {
        int numClientes = clientes.size();
        int numArmazens = armazens.size();
        int[] solution = new int[numClientes]; // Vetor para armazenar a solução inicial

        // Inicialmente nenhum cliente está atribuído a nenhum armazém
        for (int i = 0; i < numClientes; i++) {
            solution[i] = -1; // Inicializa com -1, indicando que nenhum armazém foi atribuído ainda
        }

        // Iterar sobre cada cliente para atribuir um armazém
        for (int i = 0; i < numClientes; i++) {
            Cliente cliente = clientes.get(i);
            int melhorArmazem = -1;
            int menorCusto = Integer.MAX_VALUE;

            // Iterar sobre todos os armazéns para encontrar o melhor para o cliente atual
            for (int j = 0; j < numArmazens; j++) {
                Armazem armazem = armazens.get(j);

                // Verificar se o armazém tem capacidade para atender a demanda do cliente
                if (cliente.getProcura(j) <= armazem.getCapacidade()) {
                    int custo = cliente.getCusto_alocacao(j);

                    // Escolher o armazém que minimiza o custo para o cliente atual
                    if (custo < menorCusto) {
                        menorCusto = custo;
                        melhorArmazem = j;
                    }
                }
            }

            // Atribuir o melhor armazém encontrado para o cliente atual
            solution[i] = melhorArmazem;
        }

        return solution;
    }
}
