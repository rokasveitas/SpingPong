public class Paddle extends SpingThing
{
    public double ang; //current angle of paddle
    public double omg; //omega; angular velocity
    public double alp; //alpha; angular acceleration
    private Vector unitV; //unit vector in direction of paddle for dotting with collisions
    
    
    public Paddle(double width, Vector pos, double ang)
    {
        super(width, "paddle");
        this.pos = pos;
        this.ang = ang;
        this.unitV = Vector.unit(ang);
        omg = 0;
        alp = 0;
    }
    
    public String toString()
    {
        String out = super.toString();
        out += "ang is " + ang + "\n";
        out += "omg is " + omg + "\n";
        out += "alp is " + alp + "\n";
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
        unitV.rotate(omg*dt);
        omg += alp * dt;
        if(omg >= .1)
            omg = .1;
        else if(omg <= -.1)
            omg = -.1;
        ang %= 2 * Math.PI;
    }

}




