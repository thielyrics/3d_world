
public class Point 
{

	public static class Points
	{
		Point[][] p;
		
		Points(int wid, int hei)
		{
			p = new Point[wid][hei];
		}
		
		public Point get(int x, int y, int meta)
		{
			if(p[x][y] == null)
				p[x][y] = new Point(x,y,meta);
			return p[x][y];
		}
		
		public Point get(int x, int y)
		{
			return p[x][y];
		}
	}

	int x, y;
	int meta;
	
	public Point(int a, int b)
	{
		x = a;
		y = b;
		meta = -1;
	}
	
	public Point(int a, int b, int data)
	{
		x = a;
		y = b;
		meta = data;
	}
	
	public String toString()
	{
		String ret = "(" + x + "," + y + ")";
		if(meta != -1)
			ret += ":" + meta;
		return ret;
	}
}
