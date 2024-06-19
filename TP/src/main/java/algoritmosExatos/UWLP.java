package algoritmosExatos;
import com.google.ortools.Loader;
import com.google.ortools.linearsolver.MPSolver;
import com.google.ortools.linearsolver.MPVariable;
import com.google.ortools.linearsolver.MPObjective;
import com.google.ortools.linearsolver.MPConstraint;
import estrutura.Armazem;
import estrutura.Cliente;

import java.util.Arrays;
import java.util.List;

public class UWLP {
    // Função para resolver o UWLP usando as classes Armazem e Cliente
    public static boolean[] solveUWLP(List<Armazem> armazens, List<Cliente> clientes) {
        int numArmazens = armazens.size();
        int numClientes = clientes.size();

        // Matriz de custos acumulados
        double[][] dp = new double[numClientes][numArmazens];

        // Inicialização da primeira linha da matriz DP (para o primeiro cliente)
        for (int j = 0; j < numArmazens; j++) {
            dp[0][j] = clientes.get(0).getCusto_alocacao(j) + armazens.get(j).getCusto_fixo();
        }

        // Preenchimento da matriz DP
        for (int i = 1; i < numClientes; i++) {
            for (int j = 0; j < numArmazens; j++) {
                double minCost = Double.MAX_VALUE;
                for (int k = 0; k < numArmazens; k++) {
                    double cost = dp[i - 1][k] + clientes.get(i).getCusto_alocacao(j);
                    if (cost < minCost) {
                        minCost = cost;
                    }
                }
                dp[i][j] = minCost + armazens.get(j).getCusto_fixo();
            }
        }

        // Preenchimento da matriz DP
        for (int i = 0; i < numClientes; i++) {
            for (int j = 0; j < numArmazens; j++) {
                double minCost = Double.MAX_VALUE;
                for (int k = 0; k < numArmazens; k++) {
                    double cost = clientes.get(i).getCusto_alocacao(j);

                    if (cost < minCost) {
                        minCost = cost;
                    }
                }
                if (armazens.get(j).isOpen()) {
                    dp[i][j] = minCost;
                } else {
                    dp[i][j] = minCost + armazens.get(j).getCusto_fixo();
                    armazens.get(j).setOpen(true);
                }

            }
        }


        // Reconstrução da solução a partir da matriz DP
        boolean[] solution = new boolean[numArmazens];
        int minCostIndex = 0;
        for (int j = 1; j < numArmazens; j++) {
            if (dp[numClientes - 1][j] < dp[numClientes - 1][minCostIndex]) {
                minCostIndex = j;
            }
        }
        solution[minCostIndex] = true;

        return solution;
    }

    // Função para calcular o custo total da solução
    public static double calculateTotalCost(boolean[] solution, List<Armazem> armazens, List<Cliente> clientes) {
        double totalCost = 0.0;

        // Custos de abertura dos armazéns
        for (int i = 0; i < armazens.size(); i++) {
            if (solution[i]) {
                totalCost += armazens.get(i).getCusto_fixo();
            }
        }

        // Custos de alocação dos clientes aos armazéns abertos
        for (Cliente cliente : clientes) {
            for (int j = 0; j < armazens.size(); j++) {
                if (solution[j]) {
                    totalCost += cliente.getCusto_alocacao(j);
                }
            }
        }

        return totalCost;
    }
}

