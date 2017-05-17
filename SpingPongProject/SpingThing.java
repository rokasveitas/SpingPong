public class SpingThing
{
    public Vector acc; //acceleration
    public Vector vel; //velocity
    public Vector pos; //position
    public double size; //width if paddle, radius if ball
    
    public SpingThing(double size)
    {
        acc = new Vector(0, 0);
        vel = new Vector(0, 0);
        pos = new Vector(0, 0);
        this.size = size;
    }
    
    public void timeInc(double dt) //changes vel and pos as time changes by dt
    {
        pos.setX(pos.getX() + vel.getX()*dt);
        pos.setY(pos.getY() + vel.getY()*dt);
        vel.setX(vel.getX() + acc.getX()*dt);
        vel.setY(vel.getY() + acc.getY()*dt);
    }
}