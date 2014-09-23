import com.threed.jpct.*;

@SuppressWarnings("serial")
public class Wall extends Object3D
{
	public Wall() 
	{
	    super(12);
	    float hei = 27.3f;
	    float wid = 27.3f;
	    float dep = 2.5f;
	    
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
	    setTexture("wall");

	    rotateZ((float)Math.PI/1000);
	}
}

	