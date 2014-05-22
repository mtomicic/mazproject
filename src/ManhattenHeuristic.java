
public class ManhattenHeuristic implements Heuristic {

	public int getH(Node curr, Node goal) {
		return Math.abs(curr.getxPos() - goal.getxPos()) 
				+ Math.abs(curr.getyPos() - goal.getyPos());
	}

}
