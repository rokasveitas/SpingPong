import java.lang.Math;

public class Vector
{
	double x, y, r, theta;
	public Vector(double x, double y)
	{
		this.x = x;
		this.y = y;
		this.r = Math.sqrt(x*x + y*y);
		this.theta = Math.atan(y / x);
	}
	public double getR()
	{
		return r;
	}
	public double getTheta()
	{
		return theta;
	}
	public double getX();
	{
		return x;
	}
	public double getY()
	{
		return y;
	}
	public void setX()
	{
		this.x = x;
		this.r = Math.sqrt(x*x + y*y);
		this.theta = Math.atan(y / x);
	}
	public void setY)
	{
		this.y = y
		this.r = Math.sqrt(x*x + y*y);
		this.theta = Math.atan(y / x);
	}
	public void rotate(double theta)
	{
		this.theta += theta;
		x = r * Math.cos(this.theta);
		y = r * Math.sin(this.theta);
	}	
	public static Vector add(Vector a, Vector b)
	{
		newX = a.getX() + b.getX();
		newY = a.getY() + b.getY();
		return new Vector(newX, newY);
	}
	public static double dot(Vector a, Vector b)
	{
		return a.getX() * b.getX() + a.getY() * b.getY();
	}
	public static Vector unit(double theta)
	{
		newX = Math.cos(theta);
		newY = Math.sin(theta);
		return new Vector(newX, newY);
	}

}