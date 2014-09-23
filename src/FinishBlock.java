import java.awt.event.ActionEvent;

import com.threed.jpct.*;

import javax.swing.*;

@SuppressWarnings("serial")
public class FinishBlock extends Box{

	public JFrame frame;
	static MazeGen.Maze gen;
	MazeGUI gui;

	public FinishBlock(MazeGUI mgui, String texture) {
		super(mgui, texture);
		gui = mgui;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {		
		rotateZ(.03f);
	}

	@Override
	public void collision(CollisionEvent arg0) {
		System.out.println("You Win");
		gui.setVisible(false);
		translate(0,0,50);
		JOptionPane.showMessageDialog(null, "Congratulations! You have defeated Sauron!");
		StartFrame start = new StartFrame();
		start.StartAll();
	}	
}