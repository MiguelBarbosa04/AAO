package heuristicosPesquisaLocal.tabuSearch;

public class TabuSearchSolution {
    int[] allocation; // allocation[i] = armazem atribuido ao cliente i
    double cost;

    public TabuSearchSolution(int[] allocation, double cost) {
        this.allocation = allocation.clone();
        this.cost = cost;
    }

    public int[] getAllocation() {
        return allocation;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }
}