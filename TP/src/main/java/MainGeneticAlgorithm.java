import estrutura.Armazem;
import estrutura.Cliente;
import metaheuristicos.geneticAlgorithm.GeneticAlgorithm;
import metaheuristicos.geneticAlgorithm.Solution;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;




public class MainGeneticAlgorithm{

    public static void main(String[] args) {
        List<Armazem> armazem = new ArrayList<Armazem>();
        List<Cliente> cliente = new ArrayList<Cliente>();

        try {
            CarregarDados.lerDados(armazem, cliente,"C:/Users/josed/OneDrive/Documentos/NetBeansProjects/AAO/TP/src/main/java/data/uncap/cap71.txt" );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        GeneticAlgorithm ga = new GeneticAlgorithm(armazem, cliente);
        long startTime = System.nanoTime();
 

        //* Genetic Algorithm
        Solution bestSolution = ga.run();

        System.out.println("Alocações dos clientes:");
        for (int i = 0; i < bestSolution.assignments.length; i++) {
            System.out.println("Cliente " + i + " alocado ao armazém " + bestSolution.assignments[i]);
        }
        System.out.println("Melhor custo encontrado: " + bestSolution.totalCost);

        // Medir o tempo computacional
        long endTime = System.nanoTime();
        long totalTime = endTime - startTime;

        // Converter o tempo total de nanossegundos para segundos
        double totalTimeInSeconds = totalTime / 1_000_000_000.0; 

        // Arredondar o tempo para três casas decimais
        double roundedTimeInSeconds = Math.round(totalTimeInSeconds * 1000.0) / 1000.0;

        System.out.println("Tempo de execução: " + roundedTimeInSeconds + " segundos");

    }
}

