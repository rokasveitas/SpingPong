<<<<<<< HEAD
public class Paddle extends SpingThing
{
    public double ang; //current angle of paddle
    public double omg; //omega; angular velocity
    public double alp; //alpha; angular acceleration
    
    public void timeInc(double dt) //changes vel and pos as time changes by dt
    {
        super.timeInc(dt);
        ang += omg * dt;
        omg += alp * dt;
    }
}
=======

public class Paddle{
    public Vector acc;
    public Vector vel;
    public Vector pos;
    public double spin;
    public double angle;
    public Paddle(){
        
    }
}
>>>>>>> origin/master
