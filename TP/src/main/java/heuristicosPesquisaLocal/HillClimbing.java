package heuristicosPesquisaLocal;

import estrutura.Armazem;
import estrutura.Cliente;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * The type Hill climbing.
 */
public class HillClimbing {

    /**
     * Evaluate solution int.
     *
     * @param solution the solution
     * @param armazens the armazens
     * @param clientes the clientes
     * @return the int
     */

     //
    public static int evaluateSolution(int[] solution, List<Armazem> armazens, List<Cliente> clientes) {
        int totalCost = 0;

        for (int i = 0; i < solution.length; i++) {
            int armazemIndex = solution[i];
            if (armazens.get(armazemIndex).isOpen()) {
                totalCost += clientes.get(i).getCusto_alocacao(armazemIndex);
            } else {
                totalCost += clientes.get(i).getCusto_alocacao(armazemIndex) + armazens.get(armazemIndex).getCusto_fixo();
                armazens.get(armazemIndex).setOpen(true); 
            }

        }

        return totalCost;
    }
}