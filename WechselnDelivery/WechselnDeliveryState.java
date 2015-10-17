package WechselnDelivery;

import java.util.LinkedList;

import aima.core.agent.Action;
import aima.core.agent.impl.DynamicAction;
import aima.core.util.datastructure.XYLocation;

/**
 * @author madenindya 
 * this is the class that represent the state of wechseln
 * delivery state
 */
public class WechselnDeliveryState {

	// attributes
	private char[][] map;
	private int N;
	private XYLocation courierA;
	private XYLocation courierB;
	private LinkedList<XYLocation> customers;

	public static Action UU = new DynamicAction("ATAS,ATAS");
	public static Action UL = new DynamicAction("ATAS,KIRI");
	public static Action UD = new DynamicAction("ATAS,BAWAH");
	public static Action UR = new DynamicAction("ATAS,KANAN");
	public static Action LU = new DynamicAction("KIRI,ATAS");
	public static Action LL = new DynamicAction("KIRI,KIRI");
	public static Action LD = new DynamicAction("KIRI,BAWAH");
	public static Action LR = new DynamicAction("KIRI,KANAN");
	public static Action DU = new DynamicAction("BAWAH,ATAS");
	public static Action DL = new DynamicAction("BAWAH,KIRI");
	public static Action DD = new DynamicAction("BAWAH,BAWAH");
	public static Action DR = new DynamicAction("BAWAH,KANAN");
	public static Action RU = new DynamicAction("KANAN,ATAS");
	public static Action RL = new DynamicAction("KANAN,KIRI");
	public static Action RD = new DynamicAction("KANAN,BAWAH");
	public static Action RR = new DynamicAction("KANAN,KANAN");

	// constructor
	public WechselnDeliveryState(char[][] map, int max, XYLocation courierA,
			XYLocation courierB, LinkedList<XYLocation> c) {
		this.map = map;
		this.N = max;
		this.courierA = courierA;
		this.courierB = courierB;
		this.customers = new LinkedList<XYLocation>();
		for (int i = 0; i < c.size(); i++) {
			this.customers.add(c.get(i));
		}
		updateDelivery();
		// printall();
	}

	// getter
	public XYLocation getCourierA() {
		return courierA;
	}

	public XYLocation getCourierB() {
		return courierB;
	}

	public LinkedList<XYLocation> getCustomers() {
		return customers;
	}

	// decrease number of customer if found
	private void updateDelivery() {
		if (this.customers.contains(courierA))
			this.customers.remove(courierA);
		if (this.customers.contains(courierB))
			this.customers.remove(courierB);
	}

	// check goal state
	public boolean isGoal() {
		return customers.isEmpty();
	}

	// check for certain move for which courier
	public boolean canMove(char act, char agent) {
		XYLocation agen = courierA;
		if (agent == 'B')
			agen = courierB;
		int x = agen.getXCoOrdinate();
		int y = agen.getYCoOrdinate();
		if (act == 'U')
			return canUp(x, y);
		if (act == 'L')
			return canLeft(x, y);
		if (act == 'D')
			return canDown(x, y);
		if (act == 'R')
			return canRight(x, y);
		return false;
	}
	
	// All Possible Action
	public WechselnDeliveryState moveUpUp() {
		return new WechselnDeliveryState(map, N, courierA.up(), courierB.up(),
				customers);
	}

	public WechselnDeliveryState moveUpLeft() {
		return new WechselnDeliveryState(map, N, courierA.up(),
				courierB.left(), customers);
	}

	public WechselnDeliveryState moveUpDown() {
		return new WechselnDeliveryState(map, N, courierA.up(),
				courierB.down(), customers);
	}

	public WechselnDeliveryState moveUpRight() {
		return new WechselnDeliveryState(map, N, courierA.up(),
				courierB.right(), customers);
	}

	public WechselnDeliveryState moveLeftUp() {
		return new WechselnDeliveryState(map, N, courierA.left(),
				courierB.up(), customers);
	}

	public WechselnDeliveryState moveLeftLeft() {
		return new WechselnDeliveryState(map, N, courierA.left(),
				courierB.left(), customers);
	}

	public WechselnDeliveryState moveLeftDown() {
		return new WechselnDeliveryState(map, N, courierA.left(),
				courierB.down(), customers);
	}

	public WechselnDeliveryState moveLeftRight() {
		return new WechselnDeliveryState(map, N, courierA.left(),
				courierB.right(), customers);
	}

	public WechselnDeliveryState moveDownUp() {
		return new WechselnDeliveryState(map, N, courierA.down(),
				courierB.up(), customers);
	}

	public WechselnDeliveryState moveDownLeft() {
		return new WechselnDeliveryState(map, N, courierA.down(),
				courierB.left(), customers);
	}

	public WechselnDeliveryState moveDownDown() {
		return new WechselnDeliveryState(map, N, courierA.down(),
				courierB.down(), customers);
	}

	public WechselnDeliveryState moveDownRight() {
		return new WechselnDeliveryState(map, N, courierA.down(),
				courierB.right(), customers);
	}

	public WechselnDeliveryState moveRightUp() {
		return new WechselnDeliveryState(map, N, courierA.right(),
				courierB.up(), customers);
	}

	public WechselnDeliveryState moveRightLeft() {
		return new WechselnDeliveryState(map, N, courierA.right(),
				courierB.left(), customers);
	}

	public WechselnDeliveryState moveRightDown() {
		return new WechselnDeliveryState(map, N, courierA.right(),
				courierB.down(), customers);
	}

	public WechselnDeliveryState moveRightRight() {
		return new WechselnDeliveryState(map, N, courierA.right(),
				courierB.right(), customers);
	}

	/**
	 * Check whether is it possible to do certain action
	 * @param x, y
	 * @return boolean
	 * check the next move is out of bond or is a river
	 * check if it can move to certain position
	 */
	private boolean canLeft(int x, int y) {
		if (x - 1 < 0 || map[y][x - 1] == 'X')
			return false;
		char px = map[y][x];
		if (px == 'O' || px == 'L')
			return true;
		return false;
	}

	private boolean canRight(int x, int y) {
		if (x + 1 < N && map[y][x + 1] != 'X') {
			char px = map[y][x];
			if (px == 'O' || px == 'R')
				return true;
			return false;
		}
		return false;
	}

	private boolean canUp(int x, int y) {
		if (y - 1 < 0 || map[y - 1][x] == 'X')
			return false;
		char py = map[y][x];
		if (py == 'O' || py == 'U')
			return true;
		return false;
	}

	private boolean canDown(int x, int y) {
		if (y + 1 < N && map[y + 1][x] != 'X') {
			char py = map[y][x];
			if (py == 'O' || py == 'D')
				return true;
			return false;
		}
		return false;
	}

	// to check all attributes in current state
	private void printall() {
		System.out.println("Posisi kurir : " + courierA + " " + courierB
				+ " sisa pengiriman : " + customers.size() + " customer : "
				+ customers);
	}

}
