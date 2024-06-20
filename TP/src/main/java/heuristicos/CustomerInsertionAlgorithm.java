package heuristicos;

import estrutura.Armazem;
import estrutura.Cliente;

import java.util.ArrayList;
import java.util.List;

public class CustomerInsertionAlgorithm {

    public static double executar(List<Cliente> clientes, List<Armazem> armazens) {
        List<Armazem> armazensAbertos = new ArrayList<>();
        double custoTotal = 0;
        List<Cliente> clientesAtendidos = new ArrayList<>();

        while (clientesAtendidos.size() < clientes.size()) {
            Cliente melhorCliente = null;
            Armazem melhorArmazem = null;
           
            double menorCusto = Double.MAX_VALUE;
            // Encontrar o melhor cliente e armazém para inserção
            for (int i = 0; i< clientes.size(); i++) {
                
                if (!clientesAtendidos.contains(clientes.get(i))) {
                    for (int j = 0; j < armazens.size(); j++) {
                        double custo = clientes.get(i).getCusto_alocacao(j);
                        if (custo < menorCusto) {
                            menorCusto = custo;
                            melhorCliente = clientes.get(i);
                            melhorArmazem = armazens.get(j);
                        }
                    }
                }
            }

            // Abrir o armazém se necessário
            if (melhorArmazem != null && !melhorArmazem.isOpen()) {
                melhorArmazem.setOpen(true);
                custoTotal += melhorArmazem.getCusto_fixo();
                armazensAbertos.add(melhorArmazem);
            }

            // Adicionar o cliente ao armazém escolhido
            custoTotal += menorCusto;
            clientesAtendidos.add(melhorCliente);
        }

        // Retornar o custo total calculado
        return custoTotal;
    }
}
