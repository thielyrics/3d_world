import javax.swing.JOptionPane;
import com.threed.jpct.*;

@SuppressWarnings("serial")
public class Sauron extends Character  {

	public Sauron(MazePanel pane , String texture) {
		super(pane, texture);		
	}   

	@Override
	public void collision(CollisionEvent arg0) {
		String question = JOptionPane.showInputDialog("You can keep it only after you give it to someone else. What is it?").toLowerCase();
		String answer = "your word";
		if(question.equals(answer)){
			trollpane.gui.toFront();		
			translate(0,0,100);
			trollpane.resetMovement();
		}
		else{
			trollpane.gui.toFront();
			trollpane.resetMovement();
		}
	}
}