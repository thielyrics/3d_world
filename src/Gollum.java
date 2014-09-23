import java.awt.List;
import javax.swing.JOptionPane;
import com.threed.jpct.*;

@SuppressWarnings("serial")
public class Gollum extends Character  {
	
	public Gollum(MazePanel pane , String texture) {
		super(pane, texture);		
	}
	  
	@Override
	public void collision(CollisionEvent arg0) {
		QuestionGen question = new QuestionGen();
		List list = question.getQuestion();
		String userans = JOptionPane.showInputDialog(list.getItem(0));
		String answer = list.getItem(1);
		trollpane.gui.toFront();
		trollpane.resetMovement();
		if (userans == null) {

		} else {			
			if (userans.equals(answer)) {				
				JOptionPane.showMessageDialog(null, "That is correct. You may now pass");
				translate(0, 0, 100);				
			} else {
				JOptionPane.showMessageDialog(null, "That is the wrong answer!");						
				setTexture("gandalf2");						
			}
		}
		trollpane.gui.toFront();
		trollpane.resetMovement();
	}
}

