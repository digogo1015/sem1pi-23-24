import java.io.*;
import java.util.Scanner;
import com.opencsv.CSVWriter;
import java.io.File;

public class MainS {
    static Scanner sc=new Scanner(System.in);
    static double readDouble(){
        String value;
        double num;
        value= sc.next();
        value = value.replace(",", ".");
        num=Double.parseDouble(value);
        return num;
    }
    static final double nDays=30;
    static final double people=1000;
    public static void main(String[] args) throws IOException {
        double beta,gama,ro,alfa,var,var2,var3;
        boolean gate=false;
        System.out.println("inserir nome do ficheiro");
        String name= sc.nextLine();
        File file=new File(name);
        Scanner in = new Scanner(file);
        beta=Double.parseDouble(in.next());
        gama=Double.parseDouble(in.next());
        ro=Double.parseDouble(in.next());
        alfa=Double.parseDouble(in.next());
        double S=people-1,I=1,R=0;
        double h;
        System.out.println("inserir H");
        h=readDouble();
        System.out.println("Usar metódo de \"euler\" ou \"runge-kutta\"?");
        sc.nextLine();
        switch (sc.nextLine().toLowerCase()){
            case "euler":
                gate=true;
                break;
            case "1":
                gate=true;
                break;
            case "e":
                gate=true;
                break;
            case "r": break;
            case "2": break;
            case "runge-kutta": break;
            case "runge kutta": break;
            case "rk4": break;
            default:
                System.out.println("Erro:Método desconhecido");
                System.exit(0);
        }
        File file2 = new File("DadosDoGráfico.csv");
        FileWriter outputfile = new FileWriter(file2);
        CSVWriter writer = new CSVWriter(outputfile);
        String[] intruction = {"sep=,"};
        writer.writeNext(intruction);
        String[] l1 = { "dias","S", "I", "R" };
        writer.writeNext(l1);
        String[] l2 = {"1",String.valueOf(S),String.valueOf(I),String.valueOf(R)};
        writer.writeNext(l2);
        for (int i=1;i<nDays;i++){
            var=S;
            if (gate) {
                S = Euler(S, h, 1, beta, I, ro);
                var2 = I - Euler2(I, h, 1, gama);
                var3 = R - Euler2(R, h, 1, alfa);
            }else {
                S = rungeKutta(S, h,1, beta, I, ro);
                var2 = I - rungeKutta2(I, h, 1,gama);
                var3 = R - rungeKutta2(I, h, 1,alfa);
            }
            I=(var-S)*ro+I-var2+var3;
            R=(var-S)*(1-ro)+R+var2-var3;
            /* Método alternativo de Euler que supostamente ao verificar cada variação em S/I/R em cada
            etapa de h faz o método ficar mais preciso.
            double x0=0;
            while (x0 < 1) {
                var = S - h * beta * S * I;
                var2 = I + h * gama * I;
                var3 = R + h * alfa * R;
                I=(S-var)*ro+var2+R-var3;
                R=(S-var)*(1-ro)+var3+I-var2;
                S=var;
                x0 = x0 + h;
            }
            x0=0;
             */
            String[] l3 = {String.valueOf(i+1),String.valueOf(S),String.valueOf(I),String.valueOf(R)};
            writer.writeNext(l3);
        }
        writer.close();
    }
    static double Euler(double y0, double h,double x,double beta,double I,double ro) {
        double var,x0=0;
            while (x0 < x) {
                var=y0;
                y0 = y0 - h * beta * y0 * I;
                x0 = x0 + h;
                I=(var-y0)*ro+I;
            }
            return y0;
    }
    static double Euler2(double y0, double h,double x,double c) {
        double x0=0;
        while (x0 < x) {
            y0 = y0 - h * c * y0;
            x0 = x0 + h;
        }
        return y0;
    }
    static double rungeKutta(double y0,double h,double x,double beta,double I,double ro){
        double k1,k2,k3,k4,y,x0=0;
        while (x0 < x) {
            y=y0;
            k1 = h*dydx(1-h,y0,-beta*I);
            k2 = h*dydx(1 - 0.5*h,y0 + 0.5*k1,-beta*I);
            k3 = h*dydx(1 - 0.5*h,y0 + 0.5*k2,-beta*I);
            k4 = h*dydx(1 , y0 + k3,-beta*I);
            y0 = y0 + (1.0/6.0) * (k1 + 2*k2 + 2*k3 + k4);
            I=(y-y0)*ro+I;
            x0 = x0 + h;
        }
        return y0;
    }
    static double dydx(double x,double y0,double c){return y0*x*c;}
    static double rungeKutta2(double y0,double h,double x,double c){
        double k1,k2,k3,k4,y,x0=0;
        while (x0 < x) {
            k1 = h*(dydx(1-h,y0,c));
            k2 = h*(dydx(1 - 0.5*h,y0 + 0.5*k1,c));
            k3 = h*(dydx(1 - 0.5*h,y0 + 0.5*k2,c));
            k4 = h*(dydx(1 , y0 + k3,c));
            y0 = y0 + (1.0/6.0) * (k1 + 2*k2 + 2*k3 + k4);
            x0 = x0 + h;
        }
        return y0;
    }
}