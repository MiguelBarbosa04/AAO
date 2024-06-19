import estrutura.Armazem;
import estrutura.Cliente;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class CarregarDados {

    public static void lerDados(List<Armazem> armazens, List<Cliente> clientes, String caminhoFicheiro) throws IOException {
        try (BufferedReader buffer = new BufferedReader(new FileReader(caminhoFicheiro))) {

            String line;
            String[] primeiraLinha = buffer.readLine().trim().split("\\s+");
            int qtd_armazens = Integer.parseInt(primeiraLinha[0]);
            int qtd_clientes = Integer.parseInt(primeiraLinha[1]);

            // Separar dados dos armazens
            for (int i = 0; i < qtd_armazens; i++) {
                line = buffer.readLine().trim();
                if (!line.isEmpty()) {
                    String[] dadosArmazem = line.split("\\s+");
                    int capacidade = Integer.parseInt(dadosArmazem[0]);
                    double custoFixo = Double.parseDouble(dadosArmazem[1]);
                    armazens.add(new Armazem(capacidade, custoFixo));
                } else {
                    i--; // Decrementa i para repetir a leitura deste índice
                }
            }

            // Separar dados dos clientes
            for (int j = 0; j < qtd_clientes; j++) {
                line = buffer.readLine().trim();
                if (!line.isEmpty()) {
                    int procura = Integer.parseInt(line);
                    Cliente cliente = new Cliente(qtd_armazens, j);

                    do {
                        line = buffer.readLine().trim();
                        if (!line.isEmpty()) {
                            String[] custoArmazem = line.split("\\s+");
                            for (int k = 0; k < custoArmazem.length; k++) {
                                if (!custoArmazem[k].isEmpty()) {
                                    double custoTotal = Double.parseDouble(custoArmazem[k]);
                                    cliente.setCusto_alocacao(custoTotal, cliente.getSize_cost());
                                }
                            }
                            cliente.setProcura(procura);
                        }
                    } while (cliente.getSize_cost() < qtd_armazens);

                    clientes.add(cliente);
                } else {
                    j--; // Decrementa j para repetir a leitura deste índice
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
