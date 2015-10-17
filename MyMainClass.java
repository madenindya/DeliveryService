import WechselnDelivery.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import aima.core.agent.Action;
import aima.core.search.framework.GraphSearch;
import aima.core.search.framework.Problem;
import aima.core.search.framework.Search;
import aima.core.search.framework.SearchAgent;
import aima.core.search.informed.AStarSearch;
import aima.core.search.uninformed.IterativeDeepeningSearch;
import aima.core.util.datastructure.XYLocation;

/**
 * @author madenindya 
 * Main Class to read input and create an initial state.
 */

public class MyMainClass {

	static char[][] map;
	static int N = 0;
	static XYLocation courA, courB;
	static LinkedList<XYLocation> customers = new LinkedList<XYLocation>();

	public static void main(String[] args) throws IOException {		
		int jmlPesanan, jmlSungai, jmlOneWay = 0;

		// read from arguments
		String strategy = args[0];
		String input_file = args[1];
		String output_file = args[2];
		
		// read input file
		FileReader fd = new FileReader(input_file);
		BufferedReader br = new BufferedReader(fd);

		// mark position in map to 'O' (courier can move to any direction)
		N = Integer.parseInt(br.readLine());
		map = new char[N][N];
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				map[i][j] = 'O';
			}
		}
		
		// set courier position
		String[] posisi = br.readLine().split(" ");
		courA = new XYLocation(Integer.parseInt(posisi[0]) - 1,
				Integer.parseInt(posisi[1]) - 1);
		courB = new XYLocation(Integer.parseInt(posisi[2]) - 1,
				Integer.parseInt(posisi[3]) - 1);

		String[] infojumlah = br.readLine().split(" ");
		jmlPesanan = Integer.parseInt(infojumlah[0]);
		jmlSungai = Integer.parseInt(infojumlah[1]);
		jmlOneWay = Integer.parseInt(infojumlah[2]);

		for (int i = 0; i < jmlPesanan; i++) {
			String[] dtpesan = br.readLine().split(" ");
			// add list of customers to LinkedList
			addCustomer(Integer.parseInt(dtpesan[0]) - 1,
					Integer.parseInt(dtpesan[1]) - 1);
		}

		for (int i = 0; i < jmlSungai; i++) {
			String[] dtsungai = br.readLine().split(" ");
			// mark all river with 'X'
			addRiver(Integer.parseInt(dtsungai[0]) - 1,
					Integer.parseInt(dtsungai[1]) - 1);
		}

		for (int i = 0; i < jmlOneWay; i++) {
			String[] dtoneway = br.readLine().split(" ");
			int xawal = Integer.parseInt(dtoneway[0]);
			int yawal = Integer.parseInt(dtoneway[1]);
			int xakhir = Integer.parseInt(dtoneway[2]);
			int yakhir = Integer.parseInt(dtoneway[3]);
			// mark all one way with its symbol
			addOneWay(xawal - 1, yawal - 1, xakhir - 1, yakhir - 1);
		}

		br.close();
		// printmap();
		
		// Search for the solution based on chosen strategy
		String output = wechselnDeliveryDemo(strategy);
		
		// Write to output File
		File file = new File(output_file);
		// if file doesn't exists, then create it
		if (!file.exists()) {
			file.createNewFile();
		}
		FileWriter fw = new FileWriter(file.getAbsoluteFile());
		BufferedWriter bw = new BufferedWriter(fw);
		String [] writetofile = output.split("-");
		bw.write(writetofile[0]);
		bw.newLine();
		bw.append(writetofile[1]);
		bw.close();
		
	}

	private static String wechselnDeliveryDemo(String strategy) {
		try {
			Problem problem = new Problem(new WechselnDeliveryState(map, N,
					courA, courB, customers),
					WechselnDeliveryFunctionFactory.getActionFunction(),
					WechselnDeliveryFunctionFactory.getResultFunction(),
					new WechselnDeliveryGoalTest());
			Search search = null;
			if (strategy.equals("ids")) {
				System.out.println("------- IDS -------");
				search = new IterativeDeepeningSearch();
			} else if (strategy.equalsIgnoreCase("astar1")) {
				System.out.println("------- A* Manhattam -------");
				search = new AStarSearch(new GraphSearch(),	new ManhattamHeuristicFunction());
			} else if (strategy.equals("astar2")) {
				System.out.println("------- A* Straight Line -------");
				search = new AStarSearch(new GraphSearch(), new StraightLineHeuristicFunction());
			}
			SearchAgent agent = new SearchAgent(problem, search);
			String output = printActions(agent.getActions());
			printInstrumentation(agent.getInstrumentation());
			return output;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private static String printActions(List<Action> actions) {
		String ca = "";
		String cb = "";
		for (int i = 0; i < actions.size(); i++) {
			String act = actions.get(i).toString();
			String[] move = act.split("==")[1].split("]")[0].split(",");
			ca += move[0] + " ";
			cb += move[1] + " ";
		}
		System.out.println(ca);
		System.out.println(cb);
		return (ca +"-"+ cb);
	}

	private static void printInstrumentation(Properties p) {
		Iterator<Object> keys = p.keySet().iterator();
		while (keys.hasNext()) {
			String key = (String) keys.next();
			String property = p.getProperty(key);
			System.out.println(key + " : " + property);
		}

	}

	private static void addCustomer(int x, int y) {
		customers.add(new XYLocation(x, y));
	}

	private static void addRiver(int x, int y) {
		map[y][x] = 'X';
	}

	private static void addOneWay(int xawal, int yawal, int xakhir, int yakhir) {
		if (xawal == xakhir) {
			if (yawal > yakhir) { // UP
				for (int j = yakhir; j <= yawal; j++)
					map[j][xawal] = 'U';
			} else { // DOWN
				for (int j = yawal; j <= yakhir; j++)
					map[j][xawal] = 'D';
			}
		} else {
			if (xawal > xakhir) { // LEFT
				for (int j = xakhir; j <= xawal; j++)
					map[yawal][j] = 'L';
			} else { // RIGHT
				for (int j = xawal; j <= xakhir; j++)
					map[yawal][j] = 'R';
			}
		}
	}

	private static void printmap() {
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				System.out.print(map[i][j] + " ");
			}
			System.out.println();
		}
	}

}
