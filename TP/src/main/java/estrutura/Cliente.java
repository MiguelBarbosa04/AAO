package estrutura;

public class Cliente {
    public double[] custo_alocacao ;
    public int procura;
    int id;
    int size_cost;


    public Cliente(int qtdArmazem, int id) {
        this.custo_alocacao = new double[qtdArmazem];
        this.id = id;
        this.size_cost = 0;
    }

    public int getId() {
        return id;
    }

    public double getCusto_alocacao(int index) {
        return custo_alocacao[index];
    }

    public void setCusto_alocacao(double custo_alocacao, int index) {
        this.custo_alocacao[index] = custo_alocacao;
        size_cost++;
    }

    public int getProcura() {
        return procura;
    }

    public void setProcura(int procura) {
        this.procura= procura;
    }


    public int getSize_cost() {
        return size_cost;
    }


    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Info Cliente\n{\n   ID: ").append(id).append("\n   ");
        sb.append("Procura: ").append(procura).append("\n");
        sb.append("]\n   CUSTO TOTAL\n   [");
        if (custo_alocacao != null && custo_alocacao.length > 0) {
            sb.append(custo_alocacao[0]);
            for (int i = 1; i < custo_alocacao.length; i++) {
                sb.append(", ").append(custo_alocacao[i]);
            }
        }
        sb.append("]\n}");
        return sb.toString();
    }

}
