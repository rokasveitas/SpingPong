public class Ball extends SpingThing
{
    public double spin; //positive is clockwise
    
    public Ball(double rad, Vector pos, Vector vel)
    {
        super(rad);
        this.pos = pos;
        this.vel = vel;
        this.acc = new Vector(0, 0);
    }
}