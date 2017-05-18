public class Ball extends SpingThing
{
    public static final double SPIN_K = 1;
    public static final double SPIN_DEC = .8;
    public double spin; //positive is clockwise
    
    public Ball(double rad, Vector pos, Vector vel)
    {
        super(rad, "ball");
        this.pos = pos;
        this.vel = vel;
        this.acc = new Vector(0, 0);
    }
    
    public void collide(SpingThing col) throws Exception
    {
        switch(col.type)
        {
            case "table":
                this.vel.setY(-1 * this.vel.getY());
                this.vel.setX(this.vel.getX() + SPIN_K * this.spin);
                spin *= SPIN_DEC;
                break;
            default:
                throw new Exception("SpingThing col needs type.");
        }
    }
}