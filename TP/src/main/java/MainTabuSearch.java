import estrutura.Armazem;
import estrutura.Cliente;
import heuristicos.AlgoritmoGuloso;
import heuristicosPesquisaLocal.tabuSearch.TabuSearch;
import heuristicosPesquisaLocal.tabuSearch.TabuSearchSolution;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;




public class MainTabuSearch {

    public static void main(String[] args) {
        List<Armazem> armazem = new ArrayList<Armazem>();
        List<Cliente> cliente = new ArrayList<Cliente>();
        AlgoritmoGuloso greedy = new AlgoritmoGuloso();
        
        try {
            CarregarDados.lerDados(armazem, cliente,"C:/Users/josed/OneDrive/Documentos/NetBeansProjects/AAO/TP/src/main/java/data/uncap/cap71.txt" );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        long startTime = System.nanoTime();

        //* Greedy
        int[] solution = greedy.executar(armazem, cliente);

        //* Tabu Search
        TabuSearch tabuSearch = new TabuSearch(armazem, cliente, 100, 30);
        TabuSearchSolution bestSolution = tabuSearch.tabuSearch(solution);

        System.out.println("Melhor solução encontrada: ");
        System.out.println("Custo: " + bestSolution.getCost());
        System.out.println("Alocação: " + Arrays.toString(bestSolution.getAllocation()));


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

