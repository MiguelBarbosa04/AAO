import estrutura.Armazem;
import estrutura.Cliente;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class CarregarDados {

    public static void lerDados(List<Armazem> armazens, List<Cliente> clientes, String caminhoFicheiro) throws IOException {
        try (BufferedReader buffer = new BufferedReader(new FileReader(caminhoFicheiro))) {

            String[] primeiraLinha = buffer.readLine().trim().split("\\s+");
            int qtd_armazens = Integer.parseInt(primeiraLinha[0]);
            int qtd_clientes = Integer.parseInt(primeiraLinha[1]);

            //Separar dados dos armazens
            for (int i = 0; i < qtd_armazens; i++) {
                String[] dadosArmazem = buffer.readLine().trim().split("\\s+");
                int capacidade = Integer.parseInt(dadosArmazem[0]);
                double custoFixo = Double.parseDouble(dadosArmazem[1]);
                armazens.add(new Armazem(capacidade, custoFixo));
                System.out.println(armazens.toString());
            }
            //Separar dados dos clientes
            for (int j = 0; j < qtd_clientes; j++) {
                int procura = Integer.parseInt(buffer.readLine().trim());
                Cliente cliente = new Cliente(qtd_armazens, j);

                do {

                    String[] custoArmazem = buffer.readLine().trim().split("\\s+");
                    for (int k = 0; k < custoArmazem.length; k++) {
                        //Caso a custoArmazem[0] for vazio ele coloca 0 senão for.... EX: linha 108 cap71.txt
                        double custoTotal = custoArmazem[k].isEmpty() ? 0 : Double.parseDouble(custoArmazem[k]);
                        cliente.setCusto_alocacao(custoTotal, cliente.getSize_cost());
                        cliente.setProcura(procura);
                    }
                    //System.out.println(cliente.getSize_cost());
                } while (cliente.getSize_cost() < qtd_armazens);
                System.out.println(cliente.toString());
                clientes.add(cliente);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
