    public class Euler {

    public static void main(String[] args) {
        EulerMethod();
    }

    public static void EulerMethod(){
        double days = 1, nSteps = 0, h = 0.1, beta = 0.002, gama = 0.01, ro = 0.6, alfa = 0, S, I, R, S0 = 999, I0 = 1, R0 = 0, nd = 30;

        System.out.printf("S = %10f, I = %10f, R = %10f, N = %10f\n", 999.0, 1.0, 0.0, 1000.0);
        do
        {
            S = S0 + h * ((-beta) * S0 * I0);
            I = I0 + h * (ro * beta * S0 * I0 - (gama * I0) + alfa * R0);
            R = R0 + h * (gama * I0 - (alfa * R0) + ((1 - ro) * beta * S0 * I0));
            nSteps++;
            S0 = S;
            I0 = I;
            R0 = R;
            if (nSteps * h >= days) {
                System.out.printf("S = %10f, I = %10f, R = %10f, N = %10f\n", S, I, R, S + I + R);
                days++;
            }
        } while (days <= nd);

    }
}
