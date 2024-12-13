import java.util.Scanner;

class Sec
{
	void printMenu(String[][] menus, int n)
	{
		padding(2, '\n');
		padding(40, '_');
		padding(2, '\n');
		padding(14, ' ');
		System.out.printf("UGA-BUGA");
		padding(2, '\n');
		printOptions(menus[n]);
		padding(40, '_');
		padding(2, '\n');
	}

	void printVars(String[] menus, String[] data)
	{
		int	i;
		Scanner sc = new Scanner(System.in);

		i = 0;
		padding(2, '\n');
		padding(40, '_');
		padding(2, '\n');
		padding(14, ' ');
		System.out.printf("UGA-BUGA");
		padding(2, '\n');

		while (i < 5)
		{
			System.out.printf("\t%s - %s\n", menus[i + 1], data[i]);
			i++;
		}
		padding(2, '\n');
		padding(40, '_');
		padding(2, '\n');
		
		System.out.printf("\n\tEnter to confirm!\n");
		sc.nextLine();
	}

	void clear()
	{
		System.out.printf("\033[H\033[2J");
	}

	void printOptions(String[] options)
	{
		int	i;
		int	lines;

		i = 1;
		lines = 6;
		while (i <= Integer.parseInt(options[0]))
			System.out.printf("\t%d - %s\n", i, options[i++]);
		System.out.printf("\t0 - Go Back!\n");
		padding(lines - Integer.parseInt(options[0]), '\n');
	}

	void load(String[][] menus)
	{	
		menus[0][0] = "3";
		menus[0][1] = "Run the Program";
		menus[0][2] = "Change Variables";
		menus[0][3] = "Visualize Variables";

		menus[1][0] = "5";
		menus[1][1] = "File Input";
		menus[1][2] = "Choose Method";
		menus[1][3] = "Size of Step";
		menus[1][4] = "Size of Population";
		menus[1][5] = "Number of Days";
	}

	int start(String[][] menus, int menu)
	{
		int	n;
		
		clear();
		printMenu(menus, menu);
		while (true)
		{
			n = getOption();
			clear();
			if (menu == 0 && n == 1)
				return (1);
			else if (menu == 0 && n == 2)
			{
				printMenu(menus, 1); 
				menu = 1;
			}
			else if (menu == 0 && n == 3)
				return (3);
			else if (menu == 0 && n == 0)
				return (2);
			else if (menu == 1 && n > 0 && n < 6)
				return (n + 10);
			else if (n == 0)
			{
				printMenu(menus, 0);
				menu = 0;
			}
		}
	}

	static int getOption()
	{
		Scanner sc = new Scanner(System.in);
		return (sc.nextInt());
	}

	void padding(int n, char c)
	{
		while (n > 0)
		{
			System.out.printf("%c", c);
			n--;
		}
	}
}
