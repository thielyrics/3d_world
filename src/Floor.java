
import com.threed.jpct.*;

@SuppressWarnings("serial")
public class Floor extends Object3D implements CollisionListener
{
	public MazePanel floorPane;
	public Floor() 
	{
	    super(12);
	    float hei = 1f;
	    float wid = 25f;
	    float dep = 25f;
	    genTriangles(hei,wid,dep);

	}
	
	public Floor(String texture)
	{
		super(12);
	    float hei = 1f;
	    float wid = 25f;
	    float dep = 25f;
	    genTriangles(hei,wid,dep);
	    setTexture(texture);
	}
	
	public Floor(int mazeWidth, int mazeHeight, MazePanel pane) 
	{
	    super(12);
	    float hei = 1f;
	    float wid = mazeWidth*25f;
	    float dep = mazeHeight*25f;
	    genTriangles(hei,wid,dep);
	    floorPane = pane;
	}
	
	public void genTriangles(float hei, float wid, float dep)
	{
	    SimpleVector upperLeftFront = new SimpleVector(-wid,-dep,-hei);
	    SimpleVector upperRightFront = new SimpleVector(wid,-dep,-hei);
	    SimpleVector lowerLeftFront = new SimpleVector(-wid,dep,-hei);
	    SimpleVector lowerRightFront = new SimpleVector(wid,dep,-hei);
	    
	    SimpleVector upperLeftBack = new SimpleVector(-wid,-dep,0);
	    SimpleVector upperRightBack = new SimpleVector(wid,-dep,0);
	    SimpleVector lowerLeftBack = new SimpleVector(-wid,dep,0);
	    SimpleVector lowerRightBack = new SimpleVector(wid,dep,0);
	    
	    // Front
	    addTriangle(upperLeftFront,0,0, lowerLeftFront,0,1, upperRightFront,1,0);
	    addTriangle(upperRightFront,1,0, lowerLeftFront,0,1, lowerRightFront,1,1);
	    
	    // Back
	    addTriangle(upperLeftBack,0,0, upperRightBack,1,0, lowerLeftBack,0,1);
	    addTriangle(upperRightBack,1,0, lowerRightBack,1,1, lowerLeftBack,0,1);
	    
	    // Upper
	    addTriangle(upperLeftBack,0,0, upperLeftFront,0,1, upperRightBack,1,0);
	    addTriangle(upperRightBack,1,0, upperLeftFront,0,1, upperRightFront,1,1);
	    
	    // Lower
	    addTriangle(lowerLeftBack,0,0, lowerRightBack,1,0, lowerLeftFront,0,1);
	    addTriangle(lowerRightBack,1,0, lowerRightFront,1,1, lowerLeftFront,0,1);
	    
	    // Left
	    addTriangle(upperLeftFront,0,0, upperLeftBack,1,0, lowerLeftFront,0,1);
	    addTriangle(upperLeftBack,1,0, lowerLeftBack,1,1, lowerLeftFront,0,1);
	    
	    // Right
	    addTriangle(upperRightFront,0,0, lowerRightFront,0,1, upperRightBack,1,0);
	    addTriangle(upperRightBack,1,0, lowerRightFront, 0,1, lowerRightBack,1,1);
	    addCollisionListener(this);
	    setCollisionMode(Object3D.COLLISION_CHECK_OTHERS);
	}

	@Override
	public void collision(CollisionEvent ce) {		
		setTexture("footprint");
	}

	@Override
	public boolean requiresPolygonIDs() {
		// TODO Auto-generated method stub
		return false;
	}
}

	