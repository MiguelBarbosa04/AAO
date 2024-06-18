package geneticAlgorithm;
import estrutura.Armazem;
import estrutura.Cliente;

import java.util.List;

public class Solution {
    public int[] assignments;
    public double totalCost;

    Solution(int numClientes) {
        this.assignments = new int[numClientes];
    }

    void calculateCost(List<Armazem> armazens, List<Cliente> clientes) {
        double cost = 0;
        int[] warehouseUsage = new int[armazens.size()];

        for (int i = 0; i < assignments.length; i++) {
            int warehouseIndex = assignments[i];
            warehouseUsage[warehouseIndex] += clientes.get(i).getProcura(warehouseIndex);
            cost += clientes.get(i).getCusto_alocacao(warehouseIndex);
        }

        for (int i = 0; i < armazens.size(); i++) {
            if (warehouseUsage[i] > 0) {
                cost += armazens.get(i).getCusto_fixo();
            }
        }
        this.totalCost = cost;
    }
}