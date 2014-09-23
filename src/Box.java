import javax.swing.*;

@SuppressWarnings("serial")
public abstract class Box extends Character{
	
	public JFrame frame;
	static MazeGen.Maze gen;
	MazeGUI gui;

	public Box(MazeGUI mgui, String texture) {
		super();
		gui = mgui;	 
		this.genTriangles(texture, 6, 6, 6);
	}
	
	public Box(String texture){
		super();
		this.genTriangles(texture, 6, 6, 6);
	}
}