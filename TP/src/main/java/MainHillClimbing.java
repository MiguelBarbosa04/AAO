import estrutura.Armazem;
import estrutura.Cliente;
import heuristicos.AlgoritmoGuloso;
import heuristicosPesquisaLocal.HillClimbing;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;




public class MainHillClimbing {

    public static void main(String[] args) {
        List<Armazem> armazem = new ArrayList<Armazem>();
        List<Cliente> cliente = new ArrayList<Cliente>();
        AlgoritmoGuloso greedy = new AlgoritmoGuloso();
        
        try {
            CarregarDados.lerDados(armazem, cliente,"C:/Users/josed/OneDrive/Documentos/NetBeansProjects/AAO/TP/src/main/java/data/uncap/cap72.txt" );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        long startTime = System.nanoTime();


        HillClimbing hillClimbing = new HillClimbing(armazem, cliente);
        //* Greedy
        int[] solution = greedy.executar(armazem, cliente);

        //* Hill Climbing
        int[] bestAllocation = hillClimbing.hillClimbing(solution);
        double bestCost = hillClimbing.calculateCost(bestAllocation);

        // Mostrar resultados
        System.out.println("Melhor solução encontrada: ");
        System.out.println("Custo: " + bestCost);
        System.out.println("Alocação: " + Arrays.toString(bestAllocation));

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

