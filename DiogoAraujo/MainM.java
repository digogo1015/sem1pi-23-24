import java.io.*;
import java.util.Scanner;


public class MainM {

    static final String file = "exemplo1.csv";
    static final Scanner ler = new Scanner(System.in);

    public static void main(String[] args) throws FileNotFoundException {
        String[][] ficheiro = tenisDiNike();
        tranformStringInDouble(ficheiro);

    }

    public static String[][] tenisDiNike() throws FileNotFoundException{
        int i = 0;
        Scanner sc = new Scanner(new File(file));
        String[][] ficheiro = new String[10][5];
        sc.nextLine();

        while (sc.hasNextLine())
            ficheiro[i++] = sc.nextLine().split(";");
        return ficheiro;
    }

    public static int getLineOfThePerson(String[][] ficheiro){
        String nome = ler.next();
        int flag = 0;
        int i;
        for (i = 0; i < ficheiro.length; i++) {
            if (ficheiro[i][0].equalsIgnoreCase(nome)) {
                flag = 1;
                break;
            }
        }
        if(flag == 1)
            return i;
        else
            return -1;

    }

    public static void tranformStringInDouble(String[][]ficheiro) {
        int linha = getLineOfThePerson(ficheiro);
        double[] arrDeVariaveis = new double[4];

        for (int i = 0; i < arrDeVariaveis.length ; i++) {
            ficheiro[linha][i + 1] = ficheiro[linha][i + 1].replace(",", ".");
            arrDeVariaveis[i] = Double.parseDouble(ficheiro[linha][i + 1]);
            System.out.printf("%8s", arrDeVariaveis[i]);
        }
    }
}