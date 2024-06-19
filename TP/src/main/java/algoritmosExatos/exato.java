package algoritmosExatos;

import com.google.ortools.Loader;
import com.google.ortools.linearsolver.*;
import estrutura.Armazem;
import estrutura.Cliente;
import org.apache.commons.math3.optim.MaxIter;
import org.apache.commons.math3.optim.PointValuePair;
import org.apache.commons.math3.optim.linear.*;

import java.util.List;

public class exato {

    static {
        Loader.loadNativeLibraries();
    }

    public static int[] solveILP(List<Cliente> clientes, List<Armazem> armazens, double[][] custos, double[] capacidades, double[] demandas) {
        int numClientes = clientes.size();
        int numArmazens = armazens.size();

        MPSolver solver = new MPSolver("UWLP", MPSolver.OptimizationProblemType.CBC_MIXED_INTEGER_PROGRAMMING);

        // Variáveis de decisão
        MPVariable[][] x = new MPVariable[numClientes][numArmazens];
        for (int i = 0; i < numClientes; i++) {
            for (int j = 0; j < numArmazens; j++) {
                x[i][j] = solver.makeIntVar(0, 1, "x[" + i + "," + j + "]");
            }
        }

        // Restrições de capacidade dos armazéns
        for (int j = 0; j < numArmazens; j++) {
            MPConstraint capacityConstraint = solver.makeConstraint(0.0, capacidades[j], "capacidade[" + j + "]");
            for (int i = 0; i < numClientes; i++) {
                capacityConstraint.setCoefficient(x[i][j], demandas[i]);
            }
        }

        // Restrições de atribuição de clientes
        for (int i = 0; i < numClientes; i++) {
            MPConstraint assignmentConstraint = solver.makeConstraint(1.0, 1.0, "atribuicao[" + i + "]");
            for (int j = 0; j < numArmazens; j++) {
                assignmentConstraint.setCoefficient(x[i][j], 1);
            }
        }

        // Função objetivo
        MPObjective objective = solver.objective();
        for (int i = 0; i < numClientes; i++) {
            for (int j = 0; j < numArmazens; j++) {
                objective.setCoefficient(x[i][j], custos[i][j]);
            }
        }
        objective.setMinimization();

        // Resolver o problema
        MPSolver.ResultStatus resultStatus = solver.solve();

        // Extrair a solução
        int[] solucao = new int[numClientes];
        if (resultStatus == MPSolver.ResultStatus.OPTIMAL) {
            for (int i = 0; i < numClientes; i++) {
                for (int j = 0; j < numArmazens; j++) {
                    if (x[i][j].solutionValue() > 0.5) {
                        solucao[i] = j;
                        break;
                    }
                }
            }
        } else {
            System.err.println("Não foi possível encontrar uma solução ótima.");
        }

        return solucao;
    }

    public static double calculateTotalCost(List<Cliente> clientes, List<Armazem> armazens, int[] solucao, double[][] custos) {
        double custoTotal = 0;
        for (int i = 0; i < clientes.size(); i++) {
            int armazemIndex = solucao[i];
            Armazem armazem = armazens.get(armazemIndex);
            if (armazem.isOpen()) {
                custoTotal += custos[i][armazemIndex];
            } else {
                custoTotal += custos[i][armazemIndex] + armazem.getCusto_fixo();
                armazem.setOpen(true);
            }
        }
        return custoTotal;
    } 
}
