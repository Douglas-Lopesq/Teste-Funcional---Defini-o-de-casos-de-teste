package testIdentifier;

public class VerificaIdentifier {
    public static void main(String[] args) {
        String[][] casos = {
            {null, "false"},
            {"", "false"},
            {"A", "true"},
            {"z9B2c7", "true"},
            {"A123456", "false"},
            {"1abc", "false"},
            {"_abc", "false"},
            {"Ab#1", "false"},
            {"Aé", "false"},
            {"éA", "false"},
            {"aBc09", "true"}
        };

        Identifier id = new Identifier();
        int ok = 0;

        for (String[] c : casos) {
            String entrada = c[0];
            boolean esperado = Boolean.parseBoolean(c[1]);
            boolean resultado = (entrada == null) ? id.validateIdentifier(null) : id.validateIdentifier(entrada);
            boolean passou = (resultado == esperado);
            if (passou) ok++;
            System.out.println((entrada == null ? "null" : entrada) + " => " + resultado + " | esperado=" + esperado + " | " + (passou ? "OK" : "FALHOU"));
        }

        System.out.println("Total: " + ok + "/11");
    }
}
