import java.lang.Math;

public class Ball extends SpingThing
{
    public static final double SPIN_K = .7;//1      //constant for how much spin affects vel on bounce
    public static final double SPIN_DEC = .8;   //decay factor for spin on bounce
    public static final double MAG_K = 7;       //Magnus effect constant
    public static final double G_ACC = .4;       //acceleration of gravity
    public static final double COEF_PAD = 1.2;//.6  //coefficient of elasticity of paddle/ball collision
    public static final double BALLV_EFFECT = .5;
    public double spin; //positive is clockwise
    
    public Ball(double rad, Vector pos, Vector vel)
    {
        super(rad, "ball");
        this.pos = pos;
        this.vel = vel;
        this.acc = new Vector(0, 0);
    }
    
    public String toString()
    {
        return "" + spin;
    }
    
    public void timeInc(double dt)
    {
        double magMag = MAG_K * spin * Math.abs(vel.getR()); //Magnus force magnitude
        Vector magF = Vector.unit(vel.getTheta() - Math.PI/2);
        magF.scale(magMag);
        acc = Vector.add(magF, new Vector(0, -1*G_ACC));
        acc = new Vector(0, G_ACC);
        super.timeInc(dt);
    }
     
    public void collide(SpingThing col) throws Exception
    {
        switch(col.type)
        {
            case "table":
                //System.out.println("Col with table");
                this.vel.setY(-.85 * this.vel.getY());
                this.vel.setX(this.vel.getX() + SPIN_K * this.spin);
                spin *= SPIN_DEC;
                break;
            case "net":
                this.vel.setY(this.vel.getR() * (-.8));
                this.vel.setX(0);
                break;
            case "paddle":
                //System.out.println("Collision!");
                Paddle p = (Paddle) col;
                //System.out.println("Vel: " + this.vel);
                this.vel.x *= -1;
                Vector par = p.getUnV().scaleR(Vector.dot(p.getUnV(), this.vel));
                
                /*System.out.println("Par: " + par);
                System.out.println("Ang: " + p.ang);
                System.out.println("UnitV: " + p.getUnV() + "\n");
                */
                Vector perp = (Vector.add(this.vel, par.scaleR(-1)));
                
                
                //System.out.println("par is " + par + "\nperp is " + perp);
                //System.out.println("vel is " + this.vel);
                if(p.which == 1)
                    spin += Vector.dot(this.vel, p.getUnV());
                else
                    spin -= Vector.dot(this.vel, p.getUnV());
                spin *= .6;
                par.scale(-1);
                Vector temp = Vector.add(par, perp);
                temp.setY(-1 * temp.getY());
                double dist = Math.sqrt(Math.pow((this.pos.getX()-p.pos.getX()), 2) + Math.pow((this.pos.getY()-p.pos.getY()), 2));
                Vector effVel = Vector.unit(p.getUnV().getTheta() - Math.PI/2);
                effVel.scale(p.omg * dist * 1);
                effVel = Vector.add(effVel, p.vel);
                
                temp = Vector.add(temp.scaleR(BALLV_EFFECT), effVel.scaleR(COEF_PAD));
                this.vel = temp;
                timeInc(3);
                
                /*
                System.out.println("newVel is " + this.vel);
             //   this.vel.scale(-1);
                System.out.println("par is " + par + "\nperp is " + perp);
                System.out.println("vel is " + this.vel);
                System.out.println("ang is " + p.ang);
                System.out.println();
                */break;
            default:
                throw new Exception("SpingThing col needs type.");
        }
    }
}
