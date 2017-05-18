import java.lang.Math;

public class Vector
{
    double x, y, r, theta;
    public Vector(double x, double y)
    {
        this.x = x;
        this.y = y;
        this.r = Math.sqrt(x*x + y*y);
        try{
            this.theta = Math.atan(y / x);
        } catch(Exception e)
        {
            this.theta = Math.atan((y + .0001) / x);
        }
        if(x < 0)
        {
            if(y >= 0) //quad II
                theta += Math.PI;
            else
                theta -= Math.PI;
        }
    }
    public double getR()
    {
        return r;
    }
    public double getTheta()
    {
        return theta;
    }
    public double getX()
    {
        return x;
    }
    public double getY()
    {
        return y;
    }
    public void setX(double x)
    {
        this.x = x;
        this.r = Math.sqrt(x*x + y*y);
        this.theta = Math.atan(y / x);
    }
    public void setY(double y)
    {
        this.y = y;
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
        double newX = a.getX() + b.getX();
        double newY = a.getY() + b.getY();
        return new Vector(newX, newY);
    }
    public static double dot(Vector a, Vector b)
    {
        return a.getX() * b.getX() + a.getY() * b.getY();
    }
    public static Vector unit(double theta)
    {
        double newX = Math.cos(theta);
        double newY = Math.sin(theta);
        return new Vector(newX, newY);
    }
}
