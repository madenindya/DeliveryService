package WechselnDelivery;

import aima.core.search.framework.GoalTest;

/**
 * @author madenindya
 * Class to define goal test
 */
public class WechselnDeliveryGoalTest implements GoalTest {

	@Override
	public boolean isGoalState(Object state) {
		WechselnDeliveryState test = (WechselnDeliveryState) state;
		return test.isGoal();
	}

}
