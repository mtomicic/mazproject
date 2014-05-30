/**
 * ManhattenHeuristic implements the Heuristic interface
 * estimates the cost to the goal node by adding the absolute values of the difference in x and y
 * @author mtom521
 *
 */
public class ManhattenHeuristic implements Heuristic {
	/**
	 * estimates the distances to the goal node by adding the absolute values of the difference in x and y
	 */
	public int getH(Node curr, Node goal) {
		return Math.abs(curr.getxPos() - goal.getxPos()) 
				+ Math.abs(curr.getyPos() - goal.getyPos());
	}

}
