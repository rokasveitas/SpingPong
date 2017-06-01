public class Paddle extends SpingThing
{
    public double ang; //current angle of paddle
    public double omg; //omega; angular velocity
    public double alp; //alpha; angular acceleration
    private Vector unitV; //unit vector in direction of paddle for dotting with collisions
    public int which; //either 1 or 2 depending on which paddle it is
    
    
    public Paddle(double width, Vector pos, double ang)
    {
        super(width, "paddle");
        this.pos = pos;
        this.ang = ang;
        this.unitV = Vector.unit(ang);
        this.unitV.y *= -1;
        omg = 0;
        alp = 0;
    }
    
    public String toString()
    {
        /*String out = super.toString();
        out += "ang is " + ang + "\n";
        out += "omg is " + omg + "\n";
        out += "alp is " + alp + "\n";
        */
       String out = "" + unitV;
        return out;
    }
    
    public Vector getUnV()
    {
        return unitV;
    }
    
    public void timeInc(double dt) //changes vel and pos as time changes by dt
    {
        super.timeInc(dt);
        ang += omg * dt;
        ang %=  Math.PI;
		ang += Math.PI;  //deals with
		ang %= Math.PI;  //  % problems
        unitV = Vector.unit(ang);
        omg += alp * dt;
        if(omg >= .1)
            omg = .1;
        else if(omg <= -.1)
            omg = -.1;
        
    }

}




