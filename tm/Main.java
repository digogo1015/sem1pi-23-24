/*
*	Trabalho realizado por:
*	
*		Diogo 	Araujo 		1221219;
*		Luis 	Estebainha	1220664;
*		Simao	Lopes		1220779;
*		Tiago	Alves		1220780;
*		
*		Turma:	1DL;
*/

import java.util.*;
import java.io.*;

public class Main
{
	static Scanner sc = new Scanner(System.in);

	static boolean checkArgc(String[] argv)
	{
		if (argv.length > 0)
			return (false);
		return (true);
	}

	static void changeVars(String option, String[] data, int n)
	{
		System.out.printf("\nChange %s: ", option);
		data[n - 1] = sc.nextLine();
	}

	static int readFile(String[][] tax, String file) throws Exception
	{
		int	i;
		File 	fd = new File(file);
		Scanner in = new Scanner(fd);

		i = 0;
		in.nextLine();
		while (in.hasNextLine())
			tax[i++] = in.nextLine().split(";");
		return (i);
	}

	static void calc(String[] data) throws Exception
	{
		int		num;
		int		index = 0;
		int		method = Integer.parseInt(data[1]);
		int		size = Integer.parseInt(data[3]);
		int		days = Integer.parseInt(data[4]);
		double		step = Double.parseDouble(data[2]);
		String		file_in = data[0];
		String		file_name = "";
		String[][]	tax;

		tax = new String[15][5];
		num = readFile(tax, file_in);

		while (index < num)
		{
			if (method == 1 || method == 2)
				file_name = method(tax, step, size - 1, days, index, method);
			else
			{
				System.out.printf("Invalid method!\n");
				return ;
			}
			index++;
			gnuplot(file_name);
		}
	}

	static void convert(String[][] tax, int index)
	{
		int	i;

		i = 1;
		while (i < tax[index].length)
		{
			tax[index][i] = tax[index][i].replace(",", ".");
			i++;
		}
	}

	static String roundUp(double x)
	{
		String parts[] = Double.toString(x).split("\\.");

		if (x == 1)
			return ("1");
		return ("0" + parts[1]);
	}

	static String method(String[][] tax, double h, double S0, int nd, int index, int method) throws Exception
	{
		Double	beta, gama, ro, alfa;
		String 	name, str;

		convert(tax, index);
		name = tax[index][0];
		beta = Double.parseDouble(tax[index][1]);
		gama = Double.parseDouble(tax[index][2]);
		ro = Double.parseDouble(tax[index][3]);
		alfa = Double.parseDouble(tax[index][4]);
		str = name + "m" + method + "p" + roundUp(h) + "t" + (int)(S0 + 1) + "d" + nd + ".csv";
		
		File file = new File(str);
		PrintWriter pw = new PrintWriter(file);

		printToFile(pw, 0, S0, 1, 0, S0 + 1);
		
		if (method == 1)
			Euler(pw, beta, gama, ro, alfa, h, S0, nd);
		else
			RK4(pw, beta, gama, ro, alfa, h, S0, nd);
		pw.flush();
		return (str);



	}

	static void Euler(PrintWriter pw, double beta, double gama, double ro, double alfa, double h, double S0, int nd) throws Exception
	{
		int	days = 1, nSteps = 0;
		double	S, I, R, I0 = 1, R0 = 0;

		do
		{
			S = S0 + h * fS(beta, S0, I0); 
			I = I0 + h * fI(beta, gama, ro, alfa, S0, I0, R0);
			R = R0 + h * fR(beta, gama, ro, alfa, S0, I0, R0); 
			S0 = S;
			I0 = I;
			R0 = R;
			nSteps++;
			if (nSteps * h >= days)
				printToFile(pw, days++, S0, I0, R0, S0 + I0 + R0);
		} while (days < nd);
	}

	static double fS(double beta, double S, double I)
	{
		return ((-beta) * S * I);
	}

	static double fI(double beta, double gama, double ro, double alfa, double S, double I, double R)
	{
		return (ro * beta * S * I - (gama * I) + alfa * R);
	}

	static double fR(double beta, double gama, double ro, double alfa, double S, double I, double R)
	{
		return (gama * I - (alfa * R) + ((1 - ro) * beta * S * I));
	}

	static void printToFile(PrintWriter pw, int day, double S, double I, double R, double N) throws Exception
	{

		if (day == 0)
			pw.printf("dia;S;I;R;N\n");
		pw.printf("%s;%f;%f;%f;%f\n", day, S, I, R, N);
	}

	static void RK4(PrintWriter pw, double beta, double gama, double ro, double alfa, double h, double S0, int nd) throws Exception
	{
		int	days = 1, nSteps = 0;
		double	S, I, R, I0 = 1, R0 = 0, Sk, Ik, Rk;
		double k1_S, k1_I, k1_R, k2_S, k2_I, k2_R, k3_S, k3_I, k3_R, k4_S, k4_I, k4_R;

		do
		{
			k1_S = h * fS(beta, S0, I0);
			k1_I = h * fI(beta, gama, ro, alfa, S0, I0, R0);
			k1_R = h * fR(beta, gama, ro, alfa, S0, I0, R0);
			k2_S = h * fS(beta, (S0 + k1_S/2), (I0 + k1_I/2));
			k2_I = h * fI(beta, gama, ro, alfa, S0 + k1_S/2, I0 + k1_I/2, R0 + k1_R/2);
			k2_R = h * fR(beta, gama, ro, alfa, S0 + k1_S/2, I0 + k1_I/2, R0 + k1_R/2);
			k3_S = h * fS(beta, S0 + k2_S/2, I0 + k2_I/2);
			k3_I = h * fI(beta, gama, ro, alfa, S0 + k2_S/2, I0 + k2_I/2, R0 + k2_R/2);
			k3_R = h * fR(beta, gama, ro, alfa, S0 + k2_S/2, I0 + k2_I/2, R0 + k2_R/2);
			k4_S = h * fS(beta, S0 + k3_S, I0 + k3_I);
			k4_I = h * fI(beta, gama, ro, alfa, S0 + k3_S, I0 + k3_I, R0 + k3_R);
			k4_R = h * fR(beta, gama, ro, alfa, S0 + k3_S, I0 + k3_I, R0 + k3_R);

			S = (k1_S + 2 * k2_S + 2 * k3_S + k4_S) / 6;
			I = (k1_I + 2 * k2_I + 2 * k3_I + k4_I) / 6;
			R = (k1_R + 2 * k2_R + 2 * k3_R + k4_R) / 6;
			
			Sk = S0 + S;
			Ik = I0 + I;
			Rk = R0 + R;

			S0 = Sk;
			I0 = Ik;
			R0 = Rk;

			nSteps++;
			if (nSteps * h >= days)
				printToFile(pw, days++, Sk, Ik, Rk, Sk + Ik + Rk);
		} while (days < nd);
	}

	public static void gnuplot(String file_out)
	{
		String file_input = file_out;
		String[] file_name = file_out.split("\\.");

		file_out = file_name[0] + ".png";
		createScript(file_input, file_out);
		makeGraphic();
	}

	static void createScript(String file_input, String file_out)
	{
		String script = "script.txt";

		try 
		{
			File file = new File (script);
			PrintWriter pw = new PrintWriter(file);

			pw.printf("output=\"%s\"\nset datafile separator \";\"\nset output output\nset terminal pngcairo\nset title \"%s\"\nset xlabel \"Days\"\nset ylabel \"Population\"\nplot \"%s\" every ::1 using 1:2 w lines title \"Susceptible\",  \\\n\"%s\" every ::1 using 1:3 w lines title \"Infected\", \\\n\"%s\" every ::1 using 1:4 w lines title \"Recovered\"", file_out, file_input, file_input, file_input, file_input);
			pw.flush();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	static void makeGraphic()
	{
		try 
		{
			Runtime runtime = Runtime.getRuntime();
			Process plot = runtime.exec("gnuplot script.txt");
			plot.waitFor();
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}

	static boolean verifyVars(String[] data)
	{
		int	days = Integer.parseInt(data[4]);
		int	population = Integer.parseInt(data[3]);
		double	step = Double.parseDouble(data[2]);

		if (step <= 0 || step > 1 || population <= 0 || days <= 0)
			return (false);
		return (true);
	}

	public static void main(String[] argv) throws Exception
	{
		int		ret;
		int		index;
		boolean		imode = checkArgc(argv);
		String[]	data;
		String[][] 	menus;

		ret = 0;
		index = 0;
		Sec menu = new Sec();
		data = new String[5];
		menus = new String[10][10];

		if (imode)
		{
			menu.load(menus);
			while (ret != 1)
			{
				ret = menu.start(menus, index);
				if (ret == 2)
					return ;
				if (ret == 3)
				{
					menu.printVars(menus[1], data);
					index = 0;
				}
				else if (ret > 5)
				{
					changeVars(menus[1][ret - 10], data, ret - 10);
					index = 1;
				}
			}
		}		
		else
		{
			int	i;
			int	j;

			i = 0;
			j = 0;
			while (i < 5)
			{
				data[i] = argv[j];
				i++;
				j += 2;
			}
		}
		if (verifyVars(data))
			calc(data);
		else
			System.out.printf("Invalid Variables!\n");
	}
}
