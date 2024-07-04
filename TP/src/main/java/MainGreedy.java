import estrutura.Armazem;
import estrutura.Cliente;
import heuristicos.AlgoritmoGuloso;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;




public class MainGreedy {

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
        greedy.executar(armazem, cliente);
        System.out.println("Custo Total: " + greedy.custoTotal);

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

