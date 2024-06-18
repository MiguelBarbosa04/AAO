import estrutura.Armazem;
import estrutura.Cliente;

import java.util.Arrays;
import java.util.List;

public class SimplexAlgorithm {
    // Define constants and data structures
    private static final double EPSILON = 1e-9; // Small value for numerical stability

    public void solveUWLP(List<Cliente> clientes, List<Armazem> armazens) {
        int m = clientes.size(); // Number of clientes
        int n = armazens.size(); // Number of armazens

        // Objective function coefficients
        double[][] c = new double[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                c[i][j] = clientes.get(i).getCusto_alocacao(j);
            }
        }

        // Fixed costs of opening armazens
        double[] f = new double[n];
        for (int j = 0; j < n; j++) {
            f[j] = armazens.get(j).getCusto_fixo();
        }

        // Variables initialization
        double[] u = new double[m]; // Dual variables (Lagrange multipliers)
        int[] x = new int[m]; // Assignment of clients to armazens

        Arrays.fill(x, -1); // Start with no assignments

        // Simplex algorithm iteration
        while (true) {
            // Step 1: Solve the dual problem
            Arrays.fill(u, Double.POSITIVE_INFINITY);

            for (int i = 0; i < m; i++) {
                if (x[i] != -1) { // Client i is already assigned
                    u[i] = c[i][x[i]] - f[x[i]];
                }
            }

            // Step 2: Check optimality
            boolean optimal = true;
            int iOpt = -1;
            for (int i = 0; i < m; i++) {
                if (u[i] < -EPSILON) { // Found a negative reduced cost
                    optimal = false;
                    iOpt = i;
                    break;
                }
            }

            if (optimal) {
                // Step 3: Output the optimal solution
                System.out.println("Optimal assignment:");
                for (int i = 0; i < m; i++) {
                    System.out.println("Cliente " + clientes.get(i).getId() + " assigned to Armazem " + x[i]);
                }
                break;
            }

            // Step 4: Choose entering armazem (most negative reduced cost)
            int jOpt = -1;
            double minReducedCost = Double.POSITIVE_INFINITY;
            for (int j = 0; j < n; j++) {
                double reducedCost = c[iOpt][j] - f[j];
                if (reducedCost < minReducedCost) {
                    minReducedCost = reducedCost;
                    jOpt = j;
                }
            }

            // Step 5: Update assignment (pivot)
            x[iOpt] = jOpt;
        }
    }
}