import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;


import javax.swing.JPanel;
import javax.swing.Timer;

import com.threed.jpct.Camera;
import com.threed.jpct.FrameBuffer;
import com.threed.jpct.Matrix;
import com.threed.jpct.SimpleVector;
import com.threed.jpct.World;
import com.threed.jpct.util.KeyMapper;
import com.threed.jpct.util.KeyState;


@SuppressWarnings("serial")
public class MazePanel extends JPanel implements ComponentListener, ActionListener
{
	World world;
	FrameBuffer buffer;
	MazeGUI gui;
	MazeGen.Maze gen;
	
	// Jumping has some serious bugs which we couldn't work out, but set this to true if you want to play with it anyway.
	private final boolean allowJumping = false;
	
	private boolean left = false;
	private boolean right = false;
	public boolean forward = false;
	private boolean back = false;
	private boolean strafe_right = false;
	private boolean strafe_left = false;
	private boolean levitate_up = false;
	private boolean levitate_down = false;
		
	private boolean jump = false;
	private boolean jumping = false;

		
	private SimpleVector UP = new SimpleVector(0f, 0f, 1f);
	private SimpleVector DOWN = new SimpleVector(0f, 0f, -1f);
		
	private Matrix playerDirection = new Matrix();
	private SimpleVector tempVector = new SimpleVector();
	private KeyMapper keyMapper = null;	
		
	private final float headToCeiling = 4f;
	private final float headToFloor = 27f;
	private final float forwardCollideDistance = 4f;
		
	private final float initialVelocity = 1.5f;
	private final float g = 0.1f;
	private final float moveSpeed = 1.5f;
		
	private float zVelocity = initialVelocity;
	
	
	public MazePanel(MazeGUI maze)
	{
		super();
		gui = maze;
		addComponentListener(this);
		buffer = new FrameBuffer(500,500, FrameBuffer.SAMPLINGMODE_NORMAL);
		world = new World();		
		keyMapper = new KeyMapper(maze);
	}
	
	public Camera getCamera() {return world.getCamera();}

	public void buildAll(int wid, int hei, MazeGen.Maze maze) {
		world.buildAllObjects();	
		gen = maze;
		getCamera().setPosition(StartFrame.gen.getStartCoor());		
		getCamera().setOrientation(new SimpleVector(1,0,0), new SimpleVector(0,0,1));		
		Timer time = new Timer(10, this);
		time.start();
		
	}
	
	public void paintComponent(Graphics g)
	{
		buffer.clear(Color.RED);
		world.renderScene(buffer);
		world.draw(buffer);
		buffer.update();
		buffer.display(g);
	}

	@Override
	public void componentResized(ComponentEvent arg0) 
	{
		if(getHeight()*getWidth() != 0)
			buffer = new FrameBuffer(this.getWidth(),this.getHeight(),FrameBuffer.SAMPLINGMODE_NORMAL);
		repaint();
	}

	@Override
	public void componentHidden(ComponentEvent arg0) {}
	public void componentMoved(ComponentEvent arg0) {}
	public void componentShown(ComponentEvent arg0) {}

	
	@Override
	public void actionPerformed(ActionEvent e) {
		poll();
		doMovement();
		check();
	}
	
	private void doMovement() {
		tempVector = playerDirection.getXAxis();

		if (forward) {	
		    world.checkCameraCollision(tempVector, moveSpeed, forwardCollideDistance, true);			
		   }
		if (back){
			SimpleVector backVector = new SimpleVector(-tempVector.x, -tempVector.y, -tempVector.z);
			world.checkCameraCollision(backVector, moveSpeed, forwardCollideDistance, true);			   
		   }		
		if (left) {			  
			getCamera().rotateAxis(getCamera().getZAxis(), .03f);
	        playerDirection.rotateZ(-.03f);
	       }
		if (right) {			  
			getCamera().rotateAxis(getCamera().getZAxis(), -.03f);
	        playerDirection.rotateZ(.03f);
	       }		   
		if (strafe_right){
			SimpleVector rightVector = new SimpleVector(tempVector.y, -tempVector.x, tempVector.z);
			world.checkCameraCollision(rightVector, moveSpeed, forwardCollideDistance, true);		   
		   }
		if (strafe_left){
			SimpleVector leftVector = new SimpleVector(-tempVector.y, tempVector.x, tempVector.z);
			world.checkCameraCollision(leftVector, moveSpeed, forwardCollideDistance, true);			   
		 }
		
		if (allowJumping){
			
			if (levitate_up){
				SimpleVector vector = UP;
				world.checkCameraCollisionEllipsoid(vector, new SimpleVector(4f,30f/2f,4f), 1.5f, 10);			   
			 }
			if (levitate_down){
				SimpleVector vector = DOWN;
				world.checkCameraCollisionEllipsoid(vector, new SimpleVector(4f,30f/2f,4f), 1.5f, 10);			   
			 }
			
			if (jump){
				if (!jumping){
					zVelocity = initialVelocity;
					System.out.println(world.checkCameraCollisionEllipsoid(UP, new SimpleVector(0f, 0f, headToCeiling), zVelocity, 10));
					jumping = true;
				}
			}
			if (jumping){
				zVelocity -= g;
				if (zVelocity > 0f){
					if(world.checkCameraCollision(UP, zVelocity, headToCeiling, false)){
						
					}
				}
				else {
					if(world.checkCameraCollision(DOWN, -zVelocity, headToFloor, false)){
						zVelocity = 0f;
						jumping = false;
					}
				}
				
			}
		}
		
		repaint();		   
	}
	
	public void resetMovement(){
	    left = false;
		right = false;
		forward = false;
		back = false;
		strafe_right = false;
		strafe_left = false;
	}
	
	private void poll() {		
		   KeyState state = null;
		   do {
		      state = keyMapper.poll();
		      if (state != KeyState.NONE) {
		         keyAffected(state);
		      }
		   } while (state!= KeyState.NONE);
		}
		
	private void keyAffected(KeyState state) {
		   int code = state.getKeyCode();
		   boolean event = state.getState();
		   switch (code) {		      
		      case (KeyEvent.VK_LEFT): {	    	
		         left = event;
		         break;
		      }
		      case (KeyEvent.VK_RIGHT): {		    	
		         right = event;
		         break;
		      }			
		      case (KeyEvent.VK_UP): {	    	  
			      forward = event;
			      break;
		      }
		      case (KeyEvent.VK_DOWN):{
		    	  back = event;
		    	  break;
		      }
		      case (KeyEvent.VK_A): {	    	  
			      strafe_left = event;
			      break;
		      }
		      case (KeyEvent.VK_D):{
		    	  strafe_right = event;
		    	  break;
		      }
		      case (KeyEvent.VK_W): { 
		    	  forward = event;
			      break;
		       }
		      case (KeyEvent.VK_S): { 
		    	  back = event;
			      break;
		       }
		      case (KeyEvent.VK_1): {
		    	  levitate_up = event;
		    	  break;
		      }
		      case (KeyEvent.VK_2): {
		    	  levitate_down = event;
		    	  break;
		      }
		      case (KeyEvent.VK_SPACE): {
		    	  jump = event;
			      break;
		      }
		 }
	}
	
	private void check(){
		int xloc = (int)getCamera().getPosition().x/50;
		int yloc = (int)getCamera().getPosition().y/50;
		try {
		MazeGen.floor[xloc][yloc].setAdditionalColor(Color.green);
		} catch(ArrayIndexOutOfBoundsException e){
			dontDoDogDick();
		}
	}	
	public Point retrieveLoc(){
		int xloc = (int)getCamera().getPosition().x/50;
		int yloc = (int)getCamera().getPosition().y/50;
		return new Point(xloc, yloc);
	}																																										private void dontDoDogDick(){}
}
