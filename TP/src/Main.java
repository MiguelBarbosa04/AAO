import estrutura.Armazem;
import estrutura.Cliente;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        List<Armazem> armazem = new ArrayList<Armazem>();
        List<Cliente> cliente = new ArrayList<Cliente>();

        Path currentPath = Paths.get("");
        System.out.println(currentPath.toAbsolutePath().toString());
        try {
            CarregarDados.lerDados(armazem, cliente, "src/Kcapmo1.txt");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        for (Armazem armazens : armazem) {
            System.out.println(armazens);
        }

        for (Cliente clientes : cliente) {
            System.out.println(clientes);
        }

    }
}

