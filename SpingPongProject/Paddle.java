public class Paddle extends SpingThing
{
    public double ang; //current angle of paddle
    public double omg; //omega; angular velocity
    public double alp; //alpha; angular acceleration
    
    public Paddle(double width, double ang, Vector pos)
    {
        super(width);
        this.pos = pos;
        this.vel = new Vector(0, 0);
        this.acc = new Vector(0, 0);
        this.ang = ang;
        this.omg = 0;
        this.alp = 0;
    }
    
    public void timeInc(double dt) //changes vel and pos as time changes by dt
    {
        super.timeInc(dt);
        ang += omg * dt;
        omg += alp * dt;
    }
}