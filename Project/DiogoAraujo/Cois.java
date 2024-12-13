import java.util.Scanner;

public class Cois {

    static Scanner ler = new Scanner(System.in);

    public static void main(String[] args) {
        String[] array = data();
    }

    static String[] data(){
        String[] array = new String[3];
        for (int i = 0 ; i < array.length ; i++){
            array[i] = ler.nextLine();
        }
        return array;
    }

}
