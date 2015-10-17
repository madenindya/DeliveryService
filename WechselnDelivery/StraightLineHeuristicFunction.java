package WechselnDelivery;

import java.util.LinkedList;

import aima.core.search.framework.HeuristicFunction;
import aima.core.util.datastructure.XYLocation;

/**
 * @author madenindya
 * Class to define heuristic function-2
 * Straight Line Distance
 */
public class StraightLineHeuristicFunction implements HeuristicFunction{

	@Override
	public double h(Object s) {
		WechselnDeliveryState state = (WechselnDeliveryState) s;
		double retVal = 0;
		double [] distanceA = getDistanceList(state.getCourierA(),
				state.getCustomers());
		double [] distanceB = getDistanceList(state.getCourierB(),
				state.getCustomers());
		double [] minAll = getAllMin(distanceA, distanceB);
		// find the max value from the minimum list
		for (int i = 0; i < minAll.length; i++)
			retVal = minAll[i] > retVal ? minAll[i] : retVal;
		return retVal; 
	}

	// get minimum distance of all customers from the two couriers
	private double [] getAllMin(double[] distanceA, double[] distanceB) {
		double [] res = new double [distanceA.length];
		for (int i = 0; i < res.length; i++) {
			res[i] = distanceA[i] < distanceB[i] ? distanceA[i] : distanceB[i];
		}
		return res;
	}

	// get list of distance from one courier to all customers
	private double[] getDistanceList(XYLocation cr, LinkedList<XYLocation> customers) {
		int N = customers.size();
		double [] evaluate = new double[N];
		for (int i = 0; i < N; i++) {
			evaluate[i] = straightLineDistance(cr, customers.get(i));
		}
		return evaluate;
	}

	// get distance of two XYLocation
	private double straightLineDistance(XYLocation a, XYLocation b) {
		int x = a.getXCoOrdinate() - b.getXCoOrdinate();
		int y = a.getYCoOrdinate() - b.getYCoOrdinate();
		return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
	}

}
