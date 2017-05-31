import java.lang.Math;

public class Ball extends SpingThing
{
    public static final double SPIN_K = 1;      //constant for how much spin affects vel on bounce
    public static final double SPIN_DEC = .8;   //decay factor for spin on bounce
    public static final double MAG_K = 1;       //Magnus effect constant
    public static final double G_ACC = .25;       //acceleration of gravity
    public static final double COEF_PAD = .7;  //coefficient of elasticity of paddle/ball collision
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
        return "" + pos;
    }
    
    public void timeInc(double dt)
    {
    /*  double magMag = MAG_K * spin * Math.abs(vel.getR()); //Magnus force magnitude
        Vector magF = Vector.unit(vel.getTheta() - Math.PI/2);
        magF.scale(magMag);
      */  //acc = Vector.add(magF, new Vector(0, -1*G_ACC));
        acc = new Vector(0, G_ACC);
        super.timeInc(dt);
    }
     
    public void collide(SpingThing col) throws Exception
    {
        switch(col.type)
        {
            case "table":
                //System.out.println("Col with table");
                this.vel.setY(-1 * this.vel.getY());
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
                temp = Vector.add(temp, p.vel.scaleR(COEF_PAD));
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
