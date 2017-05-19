import java.lang.Math;

public class Ball extends SpingThing
{
    public static final double SPIN_K = 1;      //constant for how much spin affects vel on bounce
    public static final double SPIN_DEC = .8;   //decay factor for spin on bounce
    public static final double MAG_K = 1;       //Magnus effect constant
    public static final double G_ACC = 1;       //acceleration of gravity
    public double spin; //positive is clockwise
    
    public Ball(double rad, Vector pos, Vector vel)
    {
        super(rad, "ball");
        this.pos = pos;
        this.vel = vel;
        this.acc = new Vector(0, 0);
    }
    
    public void timeInc(double dt)
    {
        double magMag = MAG_K * Math.pow(size, 3) * spin * vel.getR(); //Magnus force magnitude
        Vector magF = Vector.unit(vel.getTheta() - Math.PI);
        magF.scale(magMag);
        acc = Vector.add(magF, new Vector(0, -1*G_ACC));
        super.timeInc(dt);
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
            case "net":
            
                break;
            case "paddle":
                
                break;
            default:
                throw new Exception("SpingThing col needs type.");
        }
    }
}