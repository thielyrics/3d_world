import javax.swing.*;

import com.threed.jpct.Texture;
import com.threed.jpct.TextureManager;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;


@SuppressWarnings("serial")
public class MazeGUI extends JFrame implements ActionListener, WindowListener
{
	
	MazePanel pane;
	static MazeGen.Maze gen;
	int wid = 700;
	int hei = 700;
	static StartFrame start = new StartFrame();
	
	
	public MazeGUI()
	{
		super("3D Maze Project");        
        setSize(wid,hei+100);
        setLocationRelativeTo(null);
        setResizable(false);
        addWindowListener(this);
		JMenuBar menuBar = new JMenuBar();
		JMenu file = menuBar.add(new JMenu("File"));
		JMenuItem exit = file.add(new JMenuItem("Exit"));
		exit.addActionListener(this);
		
		pane = new MazePanel(this);
				
		Container content = this.getContentPane();
		content.setLayout(new BorderLayout());
		content.add(menuBar, BorderLayout.NORTH);
		content.add(pane, BorderLayout.CENTER);
		
	}
	
	public static void main(String[] imAmeme)
	{		
		TextureManager.getInstance().addTexture("footprint", new Texture("textures/footprint.jpg"));
		TextureManager.getInstance().addTexture("wall",new Texture("textures/wall.jpg"));
		TextureManager.getInstance().addTexture("floor",new Texture("textures/floor.jpg"));
		TextureManager.getInstance().addTexture("ceiling",new Texture("textures/ql0.jpg"));		
		TextureManager.getInstance().addTexture("gollum", new Texture("textures/gollum.jpg"));
		TextureManager.getInstance().addTexture("sauron", new Texture("textures/sauron.jpg"));
		TextureManager.getInstance().addTexture("gandalf2", new Texture("textures/gandalfwrong.jpg"));
		TextureManager.getInstance().addTexture("shire", new Texture("textures/shire.jpg"));
		TextureManager.getInstance().addTexture("ring", new Texture("textures/ring.jpg"));	
		TextureManager.getInstance().addTexture("samwise", new Texture("textures/samwise.jpg"));				
		start.StartAll();
		
	}	

	public void actionPerformed(ActionEvent e) 
	{
		if(e.getActionCommand().equals("Exit")){
			int s = JOptionPane.showConfirmDialog(null, "Are you sure you wish to exit?");
	        System.out.println(s);
		    if(s == 0){
		    	System.gc();
			    System.exit(0);
		    }
		}
	}

	@Override
	public void windowActivated(WindowEvent arg0) {}

	@Override
	public void windowClosed(WindowEvent arg0) {}

	@Override
	public void windowClosing(WindowEvent arg0) {
		System.gc();
		System.exit(0);		
	}

	@Override
	public void windowDeactivated(WindowEvent arg0) {}

	@Override
	public void windowDeiconified(WindowEvent arg0) {}

	@Override
	public void windowIconified(WindowEvent arg0) {}

	@Override
	public void windowOpened(WindowEvent arg0) {}
}
