import java.util.EventListener;
import java.util.EventObject;

/**
 * Maze Listener
 * @author mtom521
 *
 */

public interface MazeListener extends EventListener {
	
	public void playerMoved(EventObject e);
	
	public void mazeRestarted(EventObject e);
	
	public void playerFinished(EventObject e);
	
	public void treasureCollected(EventObject e);
	
	public void fuelConsumed(EventObject e);
}
