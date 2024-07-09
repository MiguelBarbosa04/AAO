package heuristicosPesquisaLocal;

import java.util.*;

import estrutura.Armazem;
import estrutura.Cliente;

public class HillClimbing {
    private List<Armazem> armazens;
    private List<Cliente> clientes;

    //constructor inicializa listas armazens e clientes
    public HillClimbing(List<Armazem> armazens, List<Cliente> clientes) {
        this.armazens = armazens;
        this.clientes = clientes;
    }
    
    /** 
     * @param allocation
     * @return double
     */
    public double calculateCost(int[] allocation) {
        double totalCost = 0;

        // fechar todos os armazens antes de calcular o custo
        for (Armazem armazem : armazens) {
            armazem.setOpen(false);
        }

        // calcular o custo total da alocação atual
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

    
    //* Algoritmo Hill Climbing que vai melhorar a solução inicial */
    /** 
     * @param initialAllocation
     * @return int[]
     */
    public int[] hillClimbing(int[] initialAllocation) {
        int[] currentAllocation = initialAllocation.clone();
        double currentCost = calculateCost(currentAllocation);

        boolean foundBetter = true;

        // Loop 
        while (foundBetter) {
            foundBetter = false;

            // Gerar vizinhos mudando a alocação de um cliente de cada vez
            for (int i = 0; i < currentAllocation.length; i++) {
                int currentWarehouse = currentAllocation[i];

                // Tentar alocar o cliente em cada armazem (exceto o atual)
                for (int j = 0; j < armazens.size(); j++) {
                    if (j != currentWarehouse) {
                        int[] neighborAllocation = currentAllocation.clone();
                        neighborAllocation[i] = j;

                        double neighborCost = calculateCost(neighborAllocation);
                        
                        // Se o custo do vizinho é melhor que o atual, trocar a alocação e continuar
                        if (neighborCost < currentCost) {
                            currentAllocation = neighborAllocation;
                            currentCost = neighborCost;
                            foundBetter = true;
                            break; // Mover para a próxima iteração do loop while
                        }
                    }
                }

                if (foundBetter) {
                    break; 
                }
            }
        }

        return currentAllocation;
    }
}
