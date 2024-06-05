import estrutura.Armazem;
import estrutura.Cliente;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CarregarDados {

    public static void lerDados(List<Armazem> armazens, List<Cliente> clientes, String caminhoFicheiro) throws IOException {
        try (BufferedReader buffer = new BufferedReader(new FileReader(caminhoFicheiro))) {

            String[] primeiraLinha = buffer.readLine().trim().split("\\s+");
            int qtd_armazens = Integer.parseInt(primeiraLinha[0]);
            int qtd_clientes = Integer.parseInt(primeiraLinha[1]);

            List<Integer> listaProcura = new ArrayList<>();
            List<Integer> listaCustoTotal = new ArrayList<>();

            //Separar dados dos armazens
            for (int i = 0; i < qtd_armazens; i++) {
                String[] dadosArmazem = buffer.readLine().trim().split("\\s+");
                int capacidade = Integer.parseInt(dadosArmazem[0]);
                double custoFixo = Double.parseDouble(dadosArmazem[1]);
                armazens.add(new Armazem(capacidade, custoFixo));
            }

            //Separar dados dos clientes
            for (int j = 0; j < qtd_clientes; j++) {
                int idCliente = Integer.parseInt(buffer.readLine().trim());
                System.out.println("====================================================");
                System.out.println("Id Cliente: " + idCliente);
                Cliente cliente = new Cliente(qtd_armazens, idCliente);
                for (int m = 0; m < 13; m++) {
                    String[] dadosClientes = buffer.readLine().trim().split("\\s+");
                    for (int k = 0; k < dadosClientes.length; k++) {
                        String[] procuraCliente = dadosClientes[k].split("\\.");

                        System.out.println(procuraCliente[0]);

                        //Caso a procuraCliente[0] for vazio ele coloca 0 senão for.... EX: linha 108 cap71.txt
                        int procura = procuraCliente[0].isEmpty() ? 0 : Integer.parseInt(procuraCliente[0]);
                        int custoTotal = procuraCliente[1].isEmpty() ? 0 : Integer.parseInt(procuraCliente[1]);
                        listaProcura.add(procura);
                        listaCustoTotal.add(custoTotal);
                    }
                }
                System.out.println(listaProcura.toString());
                System.out.println(listaCustoTotal.toString());
                for (int l = 0; l < listaProcura.size(); l++) {
                    cliente.custo_alocacao[l] = listaCustoTotal.get(l);
                    cliente.procura[l] = listaProcura.get(l);
                }
                listaProcura.clear();
                listaCustoTotal.clear();
                clientes.add(cliente);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
