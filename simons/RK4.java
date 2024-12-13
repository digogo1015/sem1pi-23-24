public class RK4 {
    public static void main(String args[]) {
        double y0=999,x=1,h=0.1,beta=0.002,I=1,ro=0.6;
        System.out.println("\nThe value of y at x is : " + rungeKutta(y0,x,h,beta,I,ro));
    }
    static double dydx(double x,double y0,double c){return y0*x*c;}
    static double rungeKutta(double y0,double x,double h,double beta,double I,double ro){
        double k1,k2,k3,k4,y,x0=0;
        while (x0 < x) {
            y=y0;
            k1 = h*(dydx(1-h,y0,-beta*I));
            k2 = h*(dydx(1 - 0.5*h,y0 + 0.5*k1,-beta*I));
            k3 = h*(dydx(1 - 0.5*h,y0 + 0.5*k2,-beta*I));
            k4 = h*(dydx(1 , y0 + k3,-beta*I));
            y0 = y0 + (1.0/6.0) * (k1 + 2*k2 + 2*k3 + k4);
            I=(y-y0)*ro+I;
            x0 = x0 + h;
        }
        return y0;
    }
}
