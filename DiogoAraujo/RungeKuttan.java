public class RungeKuttan {

    public static void main(String[] args) {
        rk4method();
    }

    public static void rk4method(){
        double days = 1, nSteps = 0, h = 0.1, beta = 0.002, gama = 0.01, ro = 0.6, alfa = 0, S = 999, I = 1, R = 0, nd = 30;
        double k1S, k1I, k1R, k2S, k2I, k2R, k3S, k3I, k3R, k4S, k4I, k4R, kS, kI, kR, Sk, Ik,Rk;

        System.out.printf("Day = 0,  S = %10f, I = %10f, R = %10f, N = %10f\n", S, I, R, S + I + R);

        do{
            k1S = h * ((-beta) * S * I);
            k1I = h * (ro * beta * S * I - (gama * I) + alfa * R);
            k1R = h * (gama * I - (alfa * R) + ((1 - ro) * beta * S * I));

            k2S = h * ((-beta) * (S + k1S/2) * (I + k1I/2));
            k2I = h * (ro * beta * (S + k1S/2) * (I + k1I/2) - (gama * (I + k1I/2)) + alfa * (R + k1R/2));
            k2R = h * (gama * (I + k1I/2) - (alfa * (R + k1R/2)) + ((1 - ro) * beta * (S + k1S/2) * (I + k1I/2)));

            k3S = h * ((-beta) * (S + k2S/2) * (I + k2I/2));
            k3I = h * (ro * beta * (S) * (I + k2I/2) - (gama * (I + k2I/2)) + alfa * (R + k2R/2));
            k3R = h * (gama * (I + k2I/2) - (alfa * (R + k2R/2)) + ((1 - ro) * beta * (S) * (I + k2I/2)));

            k4S = h * ((-beta) * (S + k3S) * (I + k3I));
            k4I = h * (ro * beta * (S + k3S) * (I + k3I) - (gama * (I + k3I)) + alfa * (R + k3R));
            k4R = h * (gama * (I + k3I) - (alfa * (R + k3R)) + ((1 - ro) * beta * (S + k3S) * (I + k3I)));

            kS = (k1S + (2*k2S) + (2*k3S) + k4S)/6;
            kI = (k1I + (2*k2I) + (2*k3I) + k4I)/6;
            kR = (k1R + (2*k2R) + (2*k3R) + k4R)/6;

            Sk = S + kS;
            Ik = I + kR;
            Rk = R + kI;
            S = Sk;
            I = Ik;
            R = Rk;

            nSteps++;

            if (nSteps * h >= days) {
                System.out.printf("Day = %.0f,  S = %10f, I = %10f, R = %10f, N = %10f\n",days, S, I, R, S + I + R);
                days++;
            }
        }while (days < nd);
    }
}