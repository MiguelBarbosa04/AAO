import estrutura.Armazem;
import estrutura.Cliente;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class HillClimbing {

    public static void main(String[] args) {
        List<Armazem> armazens = new ArrayList<>();
        List<Cliente> clientes = new ArrayList<>();
        String caminhoFicheiro = "src/main/java/data/ORLIB/ORLIB-uncap/70/cap71.txt"; // Substitua pelo caminho do seu ficheiro

        try {
            CarregarDados.lerDados(armazens, clientes, caminhoFicheiro);
        } catch (IOException e) {
            System.err.println("Erro ao ler o ficheiro: " + e.getMessage());
            return;
        }

        int[] solution = initialSolution(clientes.size(), armazens.size());
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
            System.out.println("Cliente " + clientes.get(i).getId() + " -> Armazem " + solution[i]);
        }
        System.out.println("Custo total: " + currentCost);
    }

    public static int[] initialSolution(int numClientes, int numArmazens) {
        Random rand = new Random();
        int[] solution = new int[numClientes];
        for (int i = 0; i < numClientes; i++) {
            solution[i] = rand.nextInt(numArmazens);
        }
        return solution;
    }

    public static int evaluateSolution(int[] solution, List<Armazem> armazens, List<Cliente> clientes) {
        int totalCost = 0;
        int[] armazemCapacities = new int[armazens.size()];

        for (int i = 0; i < solution.length; i++) {
            int armazemIndex = solution[i];
            totalCost += clientes.get(i).getCusto_alocacao(armazemIndex);
        }

        return totalCost;
    }
}