package estrutura;

public class Armazem {
    public int capacidade;
    double custo_fixo;

    public Armazem(int capacidade, double custo_fixo) {
        this.capacidade = capacidade;
        this.custo_fixo = custo_fixo;
    }

    public double getCusto_fixo() {
        return custo_fixo;
    }

    public void setCusto_fixo(double custo_fixo) {
        this.custo_fixo = custo_fixo;
    }

    @Override
    public String toString() {
        return "{\n" +
                "   Info Armazem \n" +
                "   CAPACIDADE: " + capacidade + "\n" +
                "   CUSTO FIXO: " + custo_fixo + "\n" +
                "}";
    }
}
