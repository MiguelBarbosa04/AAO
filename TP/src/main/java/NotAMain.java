import java.io.*;
import java.util.*;

public class NotAMain {
    public static void main(String[] args) {
        File file = new File("data.txt");

        try (Scanner scanner = new Scanner(file)) {
            // Read number of warehouses and clients
            int numWarehouses = scanner.nextInt();
            int numClients = scanner.nextInt();

            System.out.println("Number of warehouses: " + numWarehouses);
            System.out.println("Number of clients: " + numClients);

            // Now you can proceed to read the rest of the data as needed
            // Example: skipping warehouse capacity and opening costs
            for (int i = 0; i < numWarehouses; i++) {
                scanner.nextInt(); // Read and ignore warehouse capacity
                scanner.nextDouble(); // Read and ignore warehouse opening cost
            }

            // Example: read clients data if needed
            for (int i = 0; i < numClients; i++) {
                // Example: read client data
                // Assuming client data follows a certain format, read accordingly
                // For instance, if each client has associated data after warehouses, read that here
                // scanner.nextInt();
                // scanner.nextDouble();
                // etc.
            }

        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Exception occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void solveUWLP(int m, int n, double[] fixedCosts, double[] demands, double[][] costs) {
        boolean[] openWarehouses = new boolean[m];
        double totalCost = 0.0;

        // Greedy heuristic: open warehouses based on the lowest fixed cost
        List<Integer> warehouseIndices = new ArrayList<>();
        for (int i = 0; i < m; i++) {
            warehouseIndices.add(i);
        }
        warehouseIndices.sort((i, j) -> Double.compare(fixedCosts[i], fixedCosts[j]));

        // Try to open warehouses until all customers are served
        double[] minCostForCustomer = new double[n];
        Arrays.fill(minCostForCustomer, Double.MAX_VALUE);

        for (int w : warehouseIndices) {
            double currentFixedCost = fixedCosts[w];
            boolean shouldOpen = false;

            // Check if opening this warehouse reduces the cost for any customer
            for (int j = 0; j < n; j++) {
                if (costs[w][j] < minCostForCustomer[j]) {
                    minCostForCustomer[j] = costs[w][j];
                    shouldOpen = true;
                }
            }

            if (shouldOpen) {
                openWarehouses[w] = true;
                totalCost += currentFixedCost;
            }
        }

        // Add the customer allocation costs
        for (double cost : minCostForCustomer) {
            totalCost += cost;
        }

        // Print the result
        System.out.println("Total Cost: " + totalCost);
        System.out.println("Open Warehouses:");
        for (int i = 0; i < m; i++) {
            if (openWarehouses[i]) {
                System.out.println("Warehouse " + (i + 1));
            }
        }
    }
}
