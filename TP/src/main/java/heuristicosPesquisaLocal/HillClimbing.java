package heuristicosPesquisaLocal;

import java.util.*;

import estrutura.Armazem;
import estrutura.Cliente;

public class HillClimbing {
    private List<Armazem> armazens;
    private List<Cliente> clientes;

    public HillClimbing(List<Armazem> armazens, List<Cliente> clientes) {
        this.armazens = armazens;
        this.clientes = clientes;
    }

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

    public int[] hillClimbing(int[] initialAllocation) {
        int[] currentAllocation = initialAllocation.clone();
        double currentCost = calculateCost(currentAllocation);

        boolean foundBetter = true;
        while (foundBetter) {
            foundBetter = false;

            // Generate neighbors by changing one client's allocation
            for (int i = 0; i < currentAllocation.length; i++) {
                int currentWarehouse = currentAllocation[i];

                // Try allocating this client to each warehouse (excluding current one)
                for (int j = 0; j < armazens.size(); j++) {
                    if (j != currentWarehouse) {
                        int[] neighborAllocation = currentAllocation.clone();
                        neighborAllocation[i] = j;

                        double neighborCost = calculateCost(neighborAllocation);
                        if (neighborCost < currentCost) {
                            currentAllocation = neighborAllocation;
                            currentCost = neighborCost;
                            foundBetter = true;
                            break; // Move to next iteration of while loop
                        }
                    }
                }

                if (foundBetter) {
                    break; // Move to next iteration of while loop
                }
            }
        }

        return currentAllocation;
    }
}
