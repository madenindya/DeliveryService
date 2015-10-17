package WechselnDelivery;

import java.util.LinkedHashSet;
import java.util.Set;

import aima.core.agent.Action;
import aima.core.search.framework.ActionsFunction;
import aima.core.search.framework.ResultFunction;

/**
 * @author madenindya 
 * Class to define successor function
 */
public class WechselnDeliveryFunctionFactory {

	private static ActionsFunction _actionFunction = null;
	private static ResultFunction _resultFunction = null;

	public static ActionsFunction getActionFunction() {
		if (_actionFunction == null)
			_actionFunction = new WDActionsFunction();
		return _actionFunction;
	}

	public static ResultFunction getResultFunction() {
		if (_resultFunction == null)
			_resultFunction = new WDResultFunction();
		return _resultFunction;
	}

	private static class WDActionsFunction implements ActionsFunction {

		@Override
		/**
		 * check for possible actions from a certain state
		 * return list of all possible actions
		 */
		public Set<Action> actions(Object s) {
			WechselnDeliveryState state = (WechselnDeliveryState) s;

			Set<Action> acts = new LinkedHashSet<Action>();

			boolean au = state.canMove('U', 'A');
			boolean al = state.canMove('L', 'A');
			boolean ad = state.canMove('D', 'A');
			boolean ar = state.canMove('R', 'A');
			boolean bu = state.canMove('U', 'B');
			boolean bl = state.canMove('L', 'B');
			boolean bd = state.canMove('D', 'B');
			boolean br = state.canMove('R', 'B');
			
			if (au && bu) acts.add(WechselnDeliveryState.UU);
			if (au && bl) acts.add(WechselnDeliveryState.UL);
			if (au && bd) acts.add(WechselnDeliveryState.UD);
			if (au && br) acts.add(WechselnDeliveryState.UR);
			if (al && bu) acts.add(WechselnDeliveryState.LU);
			if (al && bl) acts.add(WechselnDeliveryState.LL);
			if (al && bd) acts.add(WechselnDeliveryState.LD);
			if (al && br) acts.add(WechselnDeliveryState.LR);
			if (ad && bu) acts.add(WechselnDeliveryState.DU);
			if (ad && bl) acts.add(WechselnDeliveryState.DL);
			if (ad && bd) acts.add(WechselnDeliveryState.DD);
			if (ad && br) acts.add(WechselnDeliveryState.DR);
			if (ar && bu) acts.add(WechselnDeliveryState.RU);
			if (ar && bl) acts.add(WechselnDeliveryState.RL);
			if (ar && bd) acts.add(WechselnDeliveryState.RD);
			if (ar && br) acts.add(WechselnDeliveryState.RR);
			
			// System.out.println(acts);
			return acts;
			
		}
	}

	private static class WDResultFunction implements ResultFunction {

		@Override
		/**
		 * Check for all possible action with its constraint
		 * return new state which is the result of certain action
		 */
		public Object result(Object s, Action a) {
			WechselnDeliveryState state = (WechselnDeliveryState) s;

			boolean au = state.canMove('U', 'A');
			boolean al = state.canMove('L', 'A');
			boolean ad = state.canMove('D', 'A');
			boolean ar = state.canMove('R', 'A');
			boolean bu = state.canMove('U', 'B');
			boolean bl = state.canMove('L', 'B');
			boolean bd = state.canMove('D', 'B');
			boolean br = state.canMove('R', 'B');
			
			if (WechselnDeliveryState.UU.equals(a) && au && bu) return state.moveUpUp();
			if (WechselnDeliveryState.UL.equals(a) && au && bl) return state.moveUpLeft();
			if (WechselnDeliveryState.UD.equals(a) && au && bd) return state.moveUpDown();
			if (WechselnDeliveryState.UR.equals(a) && au && br) return state.moveUpRight();
			if (WechselnDeliveryState.LU.equals(a) && al && bu) return state.moveLeftUp();
			if (WechselnDeliveryState.LL.equals(a) && al && bl) return state.moveLeftLeft();
			if (WechselnDeliveryState.LD.equals(a) && al && bd) return state.moveLeftDown();
			if (WechselnDeliveryState.LR.equals(a) && al && br) return state.moveLeftRight();
			if (WechselnDeliveryState.DU.equals(a) && ad && bu) return state.moveDownUp();
			if (WechselnDeliveryState.DL.equals(a) && ad && bl) return state.moveDownLeft();
			if (WechselnDeliveryState.DD.equals(a) && ad && bd) return state.moveDownDown();
			if (WechselnDeliveryState.DR.equals(a) && ad && br) return state.moveDownRight();
			if (WechselnDeliveryState.RU.equals(a) && ar && bu) return state.moveRightUp();
			if (WechselnDeliveryState.RL.equals(a) && ar && bl) return state.moveRightLeft();
			if (WechselnDeliveryState.RD.equals(a) && ar && bd) return state.moveRightDown();
			if (WechselnDeliveryState.RR.equals(a) && ar && br) return state.moveRightRight();
			
			// The Action is not understood or is a NoOp
			// the result will be the current state.
			return s;
		}

	}
}
