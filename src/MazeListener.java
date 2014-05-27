import java.util.EventListener;
import java.util.EventObject;



public interface MazeListener extends EventListener {
	
	public void playerMoved(EventObject e);
	
	public void mazeRestarted(EventObject e);
	
	public void playerFinished(EventObject e);
}
