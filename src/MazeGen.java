import java.awt.Color;
import java.util.ArrayList;

import com.threed.jpct.Object3D;
import com.threed.jpct.Primitives;
import com.threed.jpct.SimpleVector;
import com.threed.jpct.Texture;
import com.threed.jpct.TextureManager;
import com.threed.jpct.World;

@SuppressWarnings("unused")
public class MazeGen 
{

	private MazeGen(){}
	static FinishBlock finish;	
	static MazePanel pane;
	public static Object3D[][] floor, ceiling;
	
	public static class Maze
	{
		
		int[][] arr;
		int width, height;
		
		Point start;
		Point end;
		
		
		public Maze(int wid, int hei)
		{
			width = wid;
			height = hei;
			arr = new int[width*2+1][height*2+1];
			start = new Point(1,1);
			end = new Point(2*width-1,2*height-1);
			arr[end.x][end.y] = 2;
		}
		
		public Maze(int wid, int hei, Point St, Point En)
		{
			width = wid;
			height = hei;
			arr = new int[width*2+1][height*2+1];
			start = new Point(St.x*2+1,St.y*2+1);
			end = new Point(En.x*2+1,En.y*2+1);
			arr[end.x][end.y] = 2;
		}
		
		public Maze(int wid, int hei, boolean val)
		{
			width = wid;
			height = hei;
			arr = new int[width*2+1][height*2+1];
			start = new Point((int)(Math.random()*width)*2+1,(int)(Math.random()*height)*2+1);
			end = new Point((int)(Math.random()*width)*2+1,(int)(Math.random()*height)*2+1);
			while(start.x == end.x && start.y == end.y)
				end = new Point((int)(Math.random()*width)*2+1,(int)(Math.random()*height)*2+1);
			arr[end.x][end.y] = 2;
		}
		
		public SimpleVector getStartCoor()
		{
			return new SimpleVector(start.x*25f,start.y*25f,0f);
		}
		
		public SimpleVector getEndCoor()
		{
			return new SimpleVector(end.x*25f,end.y*25f,0f);
		}
	}
	
	public static Maze generateMaze(int width, int height,MazePanel panel)
	{
		Maze ret = new Maze(width,height);
		pane = panel;
		return generateAnd3D(ret);
	}
	
	public static Maze generateMaze(int width, int height, Point start, Point end, MazePanel panel)
	{
		Maze ret = new Maze(width,height,start,end);
		pane = panel;
		return generateAnd3D(ret);
	}
	
	public static Maze generateRandomMaze(int width, int height, MazePanel panel)
	{
		Maze ret = new Maze(width,height,true);
		pane = panel;
		return generateAnd3D(ret);
	}
	
	public static Maze generateAnd3D(Maze maze)
	{
		gen(maze);
		floor = new Object3D[maze.width][maze.height];
		ceiling = new Object3D[maze.width][maze.height];
		translateTo3D(maze);
		return maze;
	}
	
	private static void translateTo3D(Maze maze)
	{
		Object3D fblock = new FinishBlock(pane.gui, "ring");
		
		fblock.translate(maze.getEndCoor());
		pane.world.addObject(fblock);
		
		Object3D sblock = new StartBlock("shire");		
		sblock.translate(maze.getStartCoor());
		pane.world.addObject(sblock);
		for(int i = 0; i <= maze.width; i++)
		{
			for(int j = 0; j <= maze.height; j++)
			{
				if(j != maze.height)
				{
					Object3D obj = getObject(maze,i*2,j*2+1);
					if(obj != null)
						addObject(obj,i,j,0,2);
				}
				if(i != maze.width)
				{
					Object3D obj = getObject(maze,i*2+1,j*2);
					if(obj != null)
						addObject(obj,i,j,0,1);
					if(j != maze.height)
					{
						Object3D temp = new Floor("floor");
						floor[i][j] = temp;
						addObject(temp,i,j,-1,0);
						temp = new Floor("ceiling");
						ceiling[i][j] = temp;
						addObject(temp,i,j,1,0);
					}
				}
			}
		}
	}
	
	private static Object3D getObject(Maze maze, int x, int y)
	{

		switch(maze.arr[x][y])
		{
		case 0:					
			return new Wall();
		case 4:					
			return new Gollum(pane, "gollum");
		case 5:					
			return new Samwise(pane, "samwise");
		case 6:					
			return new Sauron(pane, "sauron");
		default:
			return null;
		}
	}
	
	private static void addObject(Object3D obj, int x, int y, int z, int val)
	{
		if(val == 0)
		{
			obj.translate(new SimpleVector(50f*x+25 ,50f*y+25,25f*z));
		} else if(val == 1)
		{
			obj.translate(new SimpleVector(25+50f*x ,50f*y,0f));
		} else
		{
			obj.rotateZ((float)Math.PI/2);
			obj.translate(new SimpleVector(50f*x ,25+50f*y,0f));
		}
		obj.setCollisionMode(Object3D.COLLISION_CHECK_OTHERS);
		obj.setAdditionalColor(Color.white);
		obj.build();
		pane.world.addObject(obj);
	}
	

    private static void addBlock(Maze maze, int type)
    {
            Point p = new Point((int)(Math.random()*(maze.height-1))*2+1,(int)(Math.random()*(maze.width-1))*2);
            while(maze.arr[p.x][p.y] != 1)
            {
                    p = new Point((int)(Math.random()*(maze.height-1))*2, (int)(Math.random()*(maze.height-1))*2+1);
                    if(maze.arr[p.x][p.y] != 1)
                            p = new Point((int)(Math.random()*(maze.height-1))*2+1,(int)(Math.random()*(maze.width-1))*2);
            }
            maze.arr[p.x][p.y] = type;
    }
	
	private static void gen(Maze maze)
	{
		ArrayList<Point> active = new ArrayList<Point>();
		int size = (maze.height*maze.width)/50+1;
		Point.Points pts = new Point.Points(maze.width*2+1,maze.height*2+1);
		int[][] meta = new int[size][size];
		for(int i = 0; i < size; i++)
		{
			Point p = pts.get((int)(Math.random()*maze.width)*2+1,(int)(Math.random()*maze.height)*2+1,i);
			active.add(p);
			maze.arr[p.x][p.y] = 1;
		}
		while(active.size() > 0)
		{
			int loc = (int)(Math.random()*active.size());
			Point curr = active.remove(loc);
			Point end = openWall(curr,maze, meta, pts);
			if(end != null)
			{
				active.add(curr);
				active.add(end);
			}
		}
		breakEnd(maze);
		for(int i = 0; i < size; i++)
		{
			addBlock(maze,4);
			addBlock(maze,5);
		}
		createMazeHints(maze);
	}
	
	private static Point addWall(Point curr, Maze maze)
	{
		int dir = (int)(Math.random()*4);
		boolean found = false;
		int x2 = 0, y2 = 0;
		for(int i = 0; i < 4 && !found; i++)
		{
			x2 = curr.x + 2*cos((i+dir)&3);
			y2 = curr.y + 2*sin((i+dir)&3);
			if(x2 > 0 && x2 < 2*maze.width)
				if(y2 > 0 && y2 < 2*maze.height)
					if(maze.arr[x2][y2] == 1)
						found = true;
		}
		if(found)
		{
			maze.arr[x2][y2] = 0;
			maze.arr[(curr.x + x2)/2][(curr.y + y2)/2] = 0;
			return new Point(x2,y2);
		}
		return null;
	}
	
	private static Point openWall(Point curr, Maze maze, int[][] meta, Point.Points pts)
	{
		int dir = (int)(Math.random()*4);
		boolean found = false;
		int x2 = 0, y2 = 0;
		for(int i = 0; i < 4 && !found; i++)
		{
			x2 = curr.x + 2*cos((i+dir)&3);
			y2 = curr.y + 2*sin((i+dir)&3);
			if(x2 > 0 && x2 < 2*maze.width)
				if(y2 > 0 && y2 < 2*maze.height)
				{
					if(maze.arr[x2][y2] == 0)
						found = true;
					if(pts.get(x2, y2) != null)
					{
						Point p2 = pts.get(x2,y2);
						if(curr.meta != p2.meta && meta[curr.meta][p2.meta] != 1)
						{
							found = true;
							meta[curr.meta][p2.meta] = 1;
							meta[p2.meta][curr.meta] = 1;
						}
					}
				}
		}
		if(found)
		{
			maze.arr[x2][y2] = 1;
			maze.arr[(curr.x + x2)/2][(curr.y + y2)/2] = 1;
			return pts.get(x2, y2, curr.meta);
		}
		return null;
	}
	
	private static void breakEnd(Maze maze)
	{
		Point curr = maze.end;
		int dir = (int)(Math.random()*4);
		int x2 = 0, y2 = 0;
		for(int i = 0; i < 4; i++)
		{
			x2 = curr.x + 2*cos((i+dir)&3);
			y2 = curr.y + 2*sin((i+dir)&3);
			if(x2 > 0 && x2 < 2*maze.width)
				if(y2 > 0 && y2 < 2*maze.height)
				{
					maze.arr[(curr.x + x2)/2][(curr.y + y2)/2] = 6; 
					return;
				}
		}
	}
	
	private static void createMazeHints(Maze maze)
	{
		recurHint(maze,maze.end.x,maze.end.y,-1);
	}
	
	private static void recurHint(Maze maze, int x, int y, int val)
	{
		if(maze.arr[x][y] > 0 || val > maze.arr[x][y])
		{
			maze.arr[x][y] = val;
			if(x+1 < maze.width*2 && maze.arr[x+1][y] != 0)
				recurHint(maze,x+2,y,val-1);
			if(x > 1 && maze.arr[x-1][y] != 0)
				recurHint(maze,x-2,y,val-1);
			if(y+1 < maze.height*2 && maze.arr[x][y+1] != 0)
				recurHint(maze,x,y+2,val-1);
			if(y > 1 && maze.arr[x][y-1] != 0)
				recurHint(maze,x,y-2,val-1);
		}
	}
	
	public static int sin(int dir)
	{
		return cos(dir + 1);
	}
	
	public static int cos(int dir)
	{
		switch(dir)
		{
		case 0:
			return 0;
		case 1:
			return 1;
		case 2:
			return 0;
		case 3:
			return -1;
		default:
			return 0;
		}
	}
	
	public static int getDir(int x,int y)
	{
		if(x == 0)
		{
			if(y > 0)
				return 0;
			else
				return 2;
		} else if(x > 0)
		{
			return 1;
		}
		return 3;
	}
}
