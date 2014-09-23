
import javax.swing.JOptionPane;

import com.threed.jpct.*;

@SuppressWarnings("serial")
public class Samwise extends Character {

	public Samwise(MazePanel pane , String texture) {
		super(pane, texture);
	}
	  
	@Override
	public void collision(CollisionEvent arg0) {
		Point userloc = trollpane.retrieveLoc();
		int userx = userloc.x*2+1;
		int usery = userloc.y*2+1;
		int wallx = (int)getTransformedCenter().x / 25;
		int wally = (int)getTransformedCenter().y / 25;
		int dirx = userx-wallx;
		int diry = usery-wally;
		userx = userx - 2*(dirx);
		usery = usery - 2*(diry);
		int dir = MazeGen.getDir(-dirx,-diry);
		int bestDir = 0;
		int userVal = trollpane.gen.arr[userx][usery];
		for(int i = 1; i < 4; i++)
		{
			int temp = (dir+i)&3;
			if(trollpane.gen.arr[userx-MazeGen.cos(temp)][usery-MazeGen.sin(temp)] != 0)
			{
				int val = trollpane.gen.arr[userx-MazeGen.cos(temp)*2][usery-MazeGen.sin(temp)*2];
				if(userVal < val)
				{
					bestDir = i;
					userVal = val;
				}
			}
		}		
		activateDialog(bestDir);
	}
	
	private void activateDialog(int dir)
	{	
		String type = "not take this path";
		switch(dir)
		{
		case 1:
			type = "go left";
			break;
		case 2:
			type = "continue forward";
			break;
		case 3:
			type = "go right";
			break;
		}
		JOptionPane.showMessageDialog(null, "Words from Samwise: You need to " + type + "!");		
		trollpane.gui.toFront();		
		translate(0,0,55);
		trollpane.resetMovement();
	}
	
}