package estrutura;

public class Cliente {
    public int[] custo_alocacao ;
    public int[] procura;
    int id;
    int size_cost;
    int size_demand;


    public Cliente(int qtdArmazem, int id) {
        this.custo_alocacao = new int[qtdArmazem];
        this.procura = new int[qtdArmazem];
        this.id = id;
        this.size_cost = 0;
        this.size_demand = 0;
    }

    public int[] getCusto_alocacao() {
        return custo_alocacao;
    }

    public void setCusto_alocacao(int custo_alocacao, int index) {
        this.custo_alocacao[index] = custo_alocacao;
        size_cost++;
    }

    public int[] getProcura() {
        return procura;
    }

    public void setProcura(int procura, int index) {
        this.procura[index] = procura;
        size_demand++;
    }

    public int getSize_demand() {
        return size_demand;
    }

    public int getSize_cost() {
        return size_cost;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Info Cliente\n{\n   ID: ").append(id).append("\n   PROCURA\n   [");
        if (procura != null && procura.length > 0) {
            sb.append(procura[0]);
            for (int i = 1; i < procura.length; i++) {
                sb.append(", ").append(procura[i]);
            }
        }
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
