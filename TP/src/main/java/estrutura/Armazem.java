package estrutura;

public class Armazem {
    public int capacidade;
    double custo_fixo;
    boolean isOpen;

    public Armazem(int capacidade, double custo_fixo) {
        this.capacidade = capacidade;
        this.custo_fixo = custo_fixo;
        this.isOpen = false;
    }

    public double getCusto_fixo() {
        return custo_fixo;
    }

    public void setCusto_fixo(double custo_fixo) {
        this.custo_fixo = custo_fixo;
    }

    public int getCapacidade() {
        return capacidade;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
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
