public class simplex {


    private double[][] A;
    private double[] b;
    private double[] c;
    private int m;
    private int n;
    private double[][] tableau;
    private int[] basis;



    public simplex(double[][] A, double[] b, double[] c) {
        this.m = b.length; // Número de restrições
        this.n = c.length; // Número de variáveis
        this.A = A;
        this.b = b;
        this.c = c;
        tableau = new double[m + 1][n + m + 1];
        basis = new int[m];

        // Inicializar tableau
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                tableau[i][j] = A[i][j];
            }
            tableau[i][n + i] = 1.0;
            tableau[i][n + m] = b[i];
            basis[i] = n + i;
        }
        for (int j = 0; j < n; j++) {
            tableau[m][j] = c[j];
        }
    }

    public simplex(double[][] a, double[] b, double[] c, int m, int n) {
        A = a;
        this.b = b;
        this.c = c;
        this.m = m;
        this.n = n;
    }

    private void pivot(int p, int q) {
        for (int i = 0; i <= m; i++) {
            for (int j = 0; j <= n + m; j++) {
                if (i != p && j != q) {
                    tableau[i][j] -= tableau[p][j] * tableau[i][q] / tableau[p][q];
                }
            }
        }
        for (int i = 0; i <= m; i++) {
            if (i != p) {
                tableau[i][q] = 0.0;
            }
        }
        for (int j = 0; j <= n + m; j++) {
            if (j != q) {
                tableau[p][j] /= tableau[p][q];
            }
        }
        tableau[p][q] = 1.0;
        basis[p] = q;
    }

    public double[] solve() {
        while (true) {
            int q = -1;
            for (int j = 0; j < n + m; j++) {
                if (tableau[m][j] > 0) {
                    q = j;
                    break;
                }
            }
            if (q == -1) break;

            int p = -1;
            for (int i = 0; i < m; i++) {
                if (tableau[i][q] > 0) {
                    if (p == -1 || tableau[i][n + m] / tableau[i][q] < tableau[p][n + m] / tableau[p][q]) {
                        p = i;
                    }
                }
            }
            if (p == -1) throw new ArithmeticException("Problema ilimitado");

            pivot(p, q);
        }

        double[] sol = new double[n];
        for (int i = 0; i < m; i++) {
            if (basis[i] < n) {
                sol[basis[i]] = tableau[i][n + m];
            }
        }
        return sol;
    }
}
