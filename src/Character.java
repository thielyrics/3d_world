import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

import com.threed.jpct.*;

@SuppressWarnings("serial")
public abstract class Character extends Object3D implements ActionListener, CollisionListener  {
	public MazePanel trollpane;

	public Character(MazePanel pane, String texture) {
	    super(12);
		trollpane = pane;
	    float hei = 25f;
	    float wid = 22.5f;
	    float dep = 1f;
	    genTriangles(texture, wid, hei, dep);
	}
	
	public Character(){
		super(12);
	}
	    
	public void genTriangles(String texture, float wid, float hei, float dep){    
	    SimpleVector upperLeftFront = new SimpleVector(-wid,-dep,-hei);
	    SimpleVector upperRightFront = new SimpleVector(wid,-dep,-hei);
	    SimpleVector lowerLeftFront = new SimpleVector(-wid,dep,-hei);
	    SimpleVector lowerRightFront = new SimpleVector(wid,dep,-hei);
	    
	    SimpleVector upperLeftBack = new SimpleVector(-wid,-dep,hei);
	    SimpleVector upperRightBack = new SimpleVector(wid,-dep,hei);
	    SimpleVector lowerLeftBack = new SimpleVector(-wid,dep,hei);
	    SimpleVector lowerRightBack = new SimpleVector(wid,dep,hei);
        
	   // Front
        addTriangle(upperLeftFront,0,0, lowerLeftFront,0,1, upperRightFront,1,0);
        addTriangle(upperRightFront,1,0, lowerLeftFront,0,1, lowerRightFront,1,1);
       
        // Back
        addTriangle(upperLeftBack,1,0, upperRightBack,0,0, lowerLeftBack,1,1);
        addTriangle(upperRightBack,0,0, lowerRightBack,0,1, lowerLeftBack,1,1);
       
        // Upper
        addTriangle(upperLeftBack,0,0, upperLeftFront,0,1, upperRightBack,1,0);
        addTriangle(upperRightBack,1,0, upperLeftFront,0,1, upperRightFront,1,1);
       
        // Lower
        addTriangle(lowerLeftBack,1,0, lowerRightBack,0,0, lowerLeftFront,1,1);
        addTriangle(lowerRightBack,0,0, lowerRightFront,0,1, lowerLeftFront,1,1);
       
        // Left
        addTriangle(upperLeftFront,1,1, upperLeftBack,1,0, lowerLeftFront,0,1);
        addTriangle(upperLeftBack,1,0, lowerLeftBack,0,0, lowerLeftFront,0,1);
       
        // Right
        addTriangle(upperRightFront,0,1, lowerRightFront,1,1, upperRightBack,0,0);
        addTriangle(upperRightBack,0,0, lowerRightFront, 1,1, lowerRightBack,1,0);

        setTexture(texture);
		setCollisionMode(Object3D.COLLISION_CHECK_OTHERS);
		setAdditionalColor(Color.WHITE);
		addCollisionListener(this);
		build();
		Timer time = new Timer(10, this);
		time.start();
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub		
	}

	@Override
	public void collision(CollisionEvent ce){
		
	}

	@Override
	public boolean requiresPolygonIDs() {
		// TODO Auto-generated method stub
		return false;
	}
}