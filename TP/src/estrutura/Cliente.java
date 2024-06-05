package estrutura;

public class Cliente {
    public int[] custo_alocacao ;
    public int[] procura;
    int id;


    public Cliente(int qtdArmazem, int id) {
        this.custo_alocacao = new int[qtdArmazem];
        this.procura = new int[qtdArmazem];
        this.id = id;
    }

    public int[] getCusto_alocacao() {
        return custo_alocacao;
    }

    public void setCusto_alocacao(int[] custo_alocacao) {
        this.custo_alocacao = custo_alocacao;
    }

    public int[] getProcura() {
        return procura;
    }

    public void setProcura(int[] procura) {
        this.procura = procura;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Cliente{id=").append(id).append(", procura=[");
        if (procura != null && procura.length > 0) {
            sb.append(procura[0]);
            for (int i = 1; i < procura.length; i++) {
                sb.append(", ").append(procura[i]);
            }
        }
        sb.append("], custo_alocacao=[");
        if (custo_alocacao != null && custo_alocacao.length > 0) {
            sb.append(custo_alocacao[0]);
            for (int i = 1; i < custo_alocacao.length; i++) {
                sb.append(", ").append(custo_alocacao[i]);
            }
        }
        sb.append("]}");
        return sb.toString();
    }

}
