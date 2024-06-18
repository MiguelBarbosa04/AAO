import estrutura.Armazem;
import estrutura.Cliente;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Tudo {

    public static void main(String[] args) {
        List<Armazem> armazens = new ArrayList<>();
        List<Cliente> clientes = new ArrayList<>();
        String caminhoFicheiro = "src/main/java/cap71.txt"; // Substituir pelo caminho certo
        long startTime = System.nanoTime();

        
        try {
            CarregarDados.lerDados(armazens, clientes, caminhoFicheiro);
        } catch (IOException e) {
            System.err.println("Erro ao ler o ficheiro: " + e.getMessage());
            return;
        }

        // Gerar a solução inicial usando heurística construtiva
        int[] solution = heuristicInitialSolution(armazens, clientes);
        int currentCost = evaluateSolution(solution, armazens, clientes);

        boolean improved;
        do {
            improved = false;
            for (int i = 0; i < solution.length; i++) {
                for (int j = 0; j < armazens.size(); j++) {
                    if (solution[i] != j) {
                        int[] newSolution = solution.clone();
                        newSolution[i] = j;
                        int newCost = evaluateSolution(newSolution, armazens, clientes);
                        if (newCost < currentCost) {
                            solution = newSolution;
                            currentCost = newCost;
                            improved = true;
                        }
                    }
                }
            }
        } while (improved);

        System.out.println("Melhor solução encontrada:");
        for (int i = 0; i < solution.length; i++) {
            // Verificar se solution[i] é um índice válido para clientes e armazens
            if (solution[i] >= 0 && solution[i] < armazens.size() && i < clientes.size()) {
                System.out.println("Cliente " + clientes.get(i).getId() + " -> Armazem " + solution[i]);
            } else {
                System.out.println("Cliente " + clientes.get(i).getId() + " -> Sem armazém atribuído");
            }
        }
        System.out.println("Custo total: " + currentCost);

        // Medir o tempo de término
        long endTime = System.nanoTime();
        long totalTime = endTime - startTime;

        // Converter o tempo total de nanossegundos para segundos
        double totalTimeInMillis = totalTime / 1_000_000.0;
        System.out.println("Tempo de execução: " + totalTimeInMillis + " ms");

    }

    public static int[] heuristicInitialSolution(List<Armazem> armazens, List<Cliente> clientes) {
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
            double menorCusto = Integer.MAX_VALUE;

            // Iterar sobre todos os armazéns para encontrar o melhor para o cliente atual
            for (int j = 0; j < numArmazens; j++) {
                Armazem armazem = armazens.get(j);

                // Verificar se o armazém tem capacidade para atender a demanda do cliente
                if (cliente.getProcura(j) <= armazem.getCapacidade()) {
                    double custo = cliente.getCusto_alocacao(j);

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

    public static int evaluateSolution(int[] solution, List<Armazem> armazens, List<Cliente> clientes) {
        int totalCost = 0;
        int[] armazemCapacities = new int[armazens.size()];

        for (int i = 0; i < solution.length; i++) {
            int armazemIndex = solution[i];
            // Verificar se solution[i] é um índice válido para clientes e armazens
            if (armazemIndex >= 0 && armazemIndex < armazens.size() && i < clientes.size()) {
                armazemCapacities[armazemIndex] += clientes.get(i).getProcura(armazemIndex);
                totalCost += clientes.get(i).getCusto_alocacao(armazemIndex);
            }
        }

        for (int i = 0; i < armazemCapacities.length; i++) {
            if (armazemCapacities[i] > armazens.get(i).getCapacidade()) {
                totalCost += (armazemCapacities[i] - armazens.get(i).getCapacidade()); // Penalidade por exceder a capacidade
            }
        }

        return totalCost;
    }

}
