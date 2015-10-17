package WechselnDelivery;

import java.util.LinkedList;

import aima.core.search.framework.HeuristicFunction;
import aima.core.util.datastructure.XYLocation;

/**
 * @author madenindya
 * Class to define heuristic function-1
 * Manhattam Distance
 */
public class ManhattamHeuristicFunction implements HeuristicFunction {

	@Override
	public double h(Object s) {
		WechselnDeliveryState state = (WechselnDeliveryState) s;
		int retVal = 0;
		int[] distanceA = getDistanceList(state.getCourierA(),
				state.getCustomers());
		int[] distanceB = getDistanceList(state.getCourierB(),
				state.getCustomers());
		int[] minAll = getAllMin(distanceA, distanceB);
		// find the max value from the minimum list
		for (int i = 0; i < minAll.length; i++)
			retVal = minAll[i] > retVal ? minAll[i] : retVal;
		return retVal;
	}

	// get minimum distance of all customers from the two couriers
	private int[] getAllMin(int[] distanceA, int[] distanceB) {
		int[] res = new int[distanceA.length];
		for (int i = 0; i < res.length; i++) {
			res[i] = distanceA[i] < distanceB[i] ? distanceA[i] : distanceB[i];
		}
		return res;
	}

	// get list of distance from one courier to all customers
	private int[] getDistanceList(XYLocation cr, LinkedList<XYLocation> customers) {
		int N = customers.size();
		int[] evaluate = new int[N];
		for (int i = 0; i < N; i++) {
			evaluate[i] = manhatamDistance(cr, customers.get(i));
		}
		return evaluate;
	}

	// get distance of two XYLocation
	private int manhatamDistance(XYLocation a, XYLocation b) {
		return Math.abs(a.getXCoOrdinate() - b.getXCoOrdinate())
				+ Math.abs(a.getYCoOrdinate() - b.getYCoOrdinate());
	}
}
