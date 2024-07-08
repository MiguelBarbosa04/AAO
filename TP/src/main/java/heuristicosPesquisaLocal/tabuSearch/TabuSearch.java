package heuristicosPesquisaLocal.tabuSearch;
import java.util.*;

import estrutura.Armazem;
import estrutura.Cliente;

public class TabuSearch {
    private List<Armazem> armazens;
    private List<Cliente> clientes;
    private int maxIterations;
    private int tabuTenure;

    public TabuSearch(List<Armazem> armazens, List<Cliente> clientes, int maxIterations, int tabuTenure) {
        this.armazens = armazens;
        this.clientes = clientes;
        this.maxIterations = maxIterations;
        this.tabuTenure = tabuTenure;
    }

    
    /** 
     * @param allocation
     * @return double
     */
    public double calculateCost(int[] allocation) {
        double totalCost = 0;
        for (Armazem armazem : armazens) {
            armazem.setOpen(false);
        }
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
     * @param allocation
     * @return List<TabuSearchSolution>
     */
    public List<TabuSearchSolution> generateNeighbors(int[] allocation) {
        List<TabuSearchSolution> neighbors = new ArrayList<>();
        for (int i = 0; i < allocation.length; i++) {
            int currentWarehouse = allocation[i];
            for (int j = 0; j < armazens.size(); j++) {
                if (j != currentWarehouse) {
                    int[] newAllocation = allocation.clone();
                    newAllocation[i] = j;
                    neighbors.add(new TabuSearchSolution(newAllocation, -1)); // cost will be calculated later
                }
            }
        }
        return neighbors;
    }

    
    /** 
     * @param tabuList
     * @param currentMove
     */
    private void updateTabuList(List<Integer> tabuList, int currentMove) {
        tabuList.add(currentMove);
        if (tabuList.size() > tabuTenure) {
            tabuList.remove(0); // Remove the oldest move if tabu list exceeds tenure
        }
    }

    
    /** 
     * @param initialAllocation
     * @return TabuSearchSolution
     */
    public TabuSearchSolution tabuSearch(int[] initialAllocation) {
        int[] bestAllocation = initialAllocation.clone();
        double bestCost = calculateCost(initialAllocation);
        int[] currentAllocation = bestAllocation.clone();
        double currentCost = bestCost;

        List<Integer> tabuList = new ArrayList<>();
        int iteration = 0;

        double[] iterationCosts = new double[maxIterations];

        while (iteration < maxIterations) {
            List<TabuSearchSolution> neighbors = generateNeighbors(currentAllocation);
            TabuSearchSolution bestNeighbor = null;
            double bestNeighborCost = Double.MAX_VALUE;

            for (TabuSearchSolution neighbor : neighbors) {
                int move = Arrays.hashCode(neighbor.getAllocation());
                if (!tabuList.contains(move) || neighbor.getCost() < bestCost) {
                    neighbor.setCost(calculateCost(neighbor.getAllocation()));
                    if (neighbor.getCost() < bestNeighborCost) {
                        bestNeighbor = neighbor;
                        bestNeighborCost = neighbor.getCost();
                    }
                }
            }

            if (bestNeighbor != null) {
                currentAllocation = bestNeighbor.getAllocation();
                currentCost = bestNeighbor.getCost();

                if (currentCost < bestCost) {
                    bestAllocation = currentAllocation.clone();
                    bestCost = currentCost;
                }

                updateTabuList(tabuList, Arrays.hashCode(currentAllocation));

                iterationCosts[iteration] = currentCost;

                // Print cost per iteration
                System.out.println("Iteration " + (iteration + 1) + " - Cost: " + currentCost);
            }

            iteration++;
        }

        // Print best global cost found
        System.out.println("\nBest global cost found: " + bestCost);

        return new TabuSearchSolution(bestAllocation, bestCost);
    }
}