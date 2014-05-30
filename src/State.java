/**
 * State class used in A Star search
 * State contains a node
 * G value and F value
 * @author mtom521
 *
 */
public class State {
	/**
	 * State Constructor
	 * @param n Node
	 * @param gee g value
	 * @param h heuristic value
	 * @param prev previous state
	 */
	public State(Node n, int gee, int h, State prev){
		this.node = n;
		this.g = gee;
		this.f = this.g + h;
		this.previous = prev;
	}
	
	public Node getNode(){
		return node;
	}
	
	public int getG(){
		return g;
	}
	
	public int getF(){
		return f;
	}
	
	@Override
	public boolean equals(Object o) {
		return node.equals(((State)o).getNode());
	}
	
	@Override
	public String toString() {
		return node.toString();
	}
	
	/**
	 * @return the previous
	 */
	public State getPrevious() {
		return previous;
	}

	private State previous;
	private Node node;
	private int g;
	private int f;
}
