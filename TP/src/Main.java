import estrutura.Armazem;
import estrutura.Cliente;
import geneticAlgorithm.GeneticAlgorithm;
import geneticAlgorithm.Solution;
import guloso.AlgoritmoGuloso;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static pesquisaLocal.PesquisaLocal.pesquisaLocal;

public class Main {

    public static void main(String[] args) {

        List<Armazem> armazem = new ArrayList<Armazem>();
        List<Cliente> cliente = new ArrayList<Cliente>();

        Path currentPath = Paths.get("");
        System.out.println(currentPath.toAbsolutePath().toString());
        try {
            CarregarDados.lerDados(armazem, cliente, "src/cap71.txt");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        /*
        for (Armazem armazens : armazem) {
            System.out.println(armazens);
        }

        for (Cliente clientes : cliente) {
            System.out.println(clientes);
        }
*/
        /*
        GeneticAlgorithm ga = new GeneticAlgorithm(armazem, cliente);
        Solution bestSolution = ga.run();

        System.out.println("Melhor custo encontrado: " + bestSolution.totalCost);
        System.out.println("Alocações dos clientes:");
        for (int i = 0; i < bestSolution.assignments.length; i++) {
            System.out.println("Cliente " + i + " alocado ao armazém " + bestSolution.assignments[i]);
        }

         */

        //pesquisaLocal(armazem, cliente);

        int[] alocacaoAtual = new int[armazem.size()];
        AlgoritmoGuloso.alocarClientes(armazem, cliente);

        // Mostrar resultados da solução inicial
        System.out.println("Resultado do Algoritmo Guloso:");
        for (int i = 0; i < armazem.size(); i++) {
            System.out.println("Cliente alocado no Armazem " + i + ": " + alocacaoAtual[i]);
        }
    }
}

