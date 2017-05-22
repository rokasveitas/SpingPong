public class SpingThing
{
    public Vector acc; //acceleration
    public Vector vel; //velocity
    public Vector pos; //position
    public String type; //paddle, ball, net, etc.
    public double size; //width if paddle, radius if ball
    
    public SpingThing(double size, String type)
    {
        acc = new Vector(0, 0);
        vel = new Vector(0, 0);
        pos = new Vector(0, 0);
        this.size = size;
        this.type = type;
    }
    
    public String toString()
    {
        String out = "";
        out += "acc is " + acc + "\n";
        out += "vel is " + vel + "\n";
        out += "pos is " + pos + "\n";
        out += "type is " + type + "\n";
        out += "size is " + size + "\n";
        return out;
    }
    
    public void timeInc(double dt) //changes vel and pos as time changes by dt
    {
        pos.setX(pos.getX() + vel.getX()*dt);
        pos.setY(pos.getY() + vel.getY()*dt);
        vel.setX(vel.getX() + acc.getX()*dt);
        vel.setY(vel.getY() + acc.getY()*dt);
    }
}