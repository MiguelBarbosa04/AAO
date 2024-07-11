package heuristicosPesquisaLocal.tabuSearch;
import java.util.*;

import estrutura.Armazem;
import estrutura.Cliente;

public class TabuSearch {
    private List<Armazem> armazens;
    private List<Cliente> clientes;
    private int maxIterations;
    private int tabuTenure;

    // Construtor classe Tabu Search
    public TabuSearch(List<Armazem> armazens, List<Cliente> clientes, int maxIterations, int tabuTenure) {
        this.armazens = armazens;
        this.clientes = clientes;
        this.maxIterations = maxIterations;
        this.tabuTenure = tabuTenure;
    }

    
    /** 
     //* Greedy
     * Calcula o custo total da alocação através do array allocation
     * @param allocation
     * @return double
     */
    public double calculateCost(int[] allocation) {
        double totalCost = 0;

        // fechar todos os armazens antes de calcular o custo
        for (Armazem armazem : armazens) {
            armazem.setOpen(false); 
        }

        // calcular o custo total da alocação atual para cada cliente
        for (int i = 0; i < allocation.length; i++) {
            int armazemIndex = allocation[i];

            if (!armazens.get(armazemIndex).isOpen()) {
                armazens.get(armazemIndex).setOpen(true);
                totalCost += armazens.get(armazemIndex).getCusto_fixo();
            }
            totalCost += clientes.get(i).getCusto_alocacao(armazemIndex);
        }
        return totalCost;
    }

    
    /** 
     * Função generateNeighbors Gera vizinhos mudando a alocação de um cliente para outro armazém
     * @param allocation
     * @return List<TabuSearchSolution>
     */
    public List<TabuSearchSolution> generateNeighbors(int[] allocation) {
        List<TabuSearchSolution> neighbors = new ArrayList<>(); // lista neighbors vazia
        for (int i = 0; i < allocation.length; i++) {
            int currentWarehouse = allocation[i];
            for (int j = 0; j < armazens.size(); j++) {
                if (j != currentWarehouse) {
                    int[] newAllocation = allocation.clone();
                    newAllocation[i] = j;
                    neighbors.add(new TabuSearchSolution(newAllocation, -1)); // -1 = O custo será calculado mais tarde
                }
            }
        }
        return neighbors;
    }

    
    /** 
     * Atualiza a tabu list, adicionando o movimento atual e removendo o mais antigo se necessário
     //* Tabu List guarda soluções utilizadas anteriormente, impedindo que essa solução seja utilizada em iterações futuras
     * @param tabuList
     * @param currentMove
     */
    private void updateTabuList(List<Integer> tabuList, int currentMove) {
        tabuList.add(currentMove);
        if (tabuList.size() > tabuTenure) {
            tabuList.remove(0); // Remover o movimento mais antigo se a lista tabu exceder 
        }
    }
    
    /** 
     //* Executa o algoritmo Tabu Search
     * @param initialAllocation
     * @return TabuSearchSolution
     */
    public TabuSearchSolution tabuSearch(int[] initialAllocation) {
        int[] bestAllocation = initialAllocation.clone(); //greedy
        double bestCost = calculateCost(initialAllocation);
        int[] currentAllocation = bestAllocation.clone();
        double currentCost = bestCost;

        List<Integer> tabuList = new ArrayList<>();
        int iteration = 0;

        double[] iterationCosts = new double[maxIterations]; // Array armazena os custos 100

        // Loop até atingir o número máximo de iterações (100)
        while (iteration < maxIterations) {
            List<TabuSearchSolution> neighbors = generateNeighbors(currentAllocation);
            TabuSearchSolution bestNeighbor = null;
            double bestNeighborCost = Double.MAX_VALUE;

            // Avalia os vizinhos e encontra o melhor
            for (TabuSearchSolution neighbor : neighbors) { // correr a lista neighbors
                int move = Arrays.hashCode(neighbor.getAllocation());
                if (!tabuList.contains(move) || neighbor.getCost() < bestCost) {
                    neighbor.setCost(calculateCost(neighbor.getAllocation()));
                    if (neighbor.getCost() < bestNeighborCost) {
                        bestNeighbor = neighbor;
                        bestNeighborCost = neighbor.getCost();
                    }
                }
            }

            if (bestNeighbor != null) { //null
                currentAllocation = bestNeighbor.getAllocation();
                currentCost = bestNeighbor.getCost();

                if (currentCost < bestCost) {
                    bestAllocation = currentAllocation.clone();
                    bestCost = currentCost;
                }

                updateTabuList(tabuList, Arrays.hashCode(currentAllocation));

                // Cada iteração
                iterationCosts[iteration] = currentCost;

                System.out.println("Iteration " + (iteration + 1) + " - Cost: " + currentCost);
            }

            iteration++; //próxima iteração
        }

        // Melhor custo global encontrado
        System.out.println("\nBest global cost found: " + bestCost);

        return new TabuSearchSolution(bestAllocation, bestCost);
    }
}