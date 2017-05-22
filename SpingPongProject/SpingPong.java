import java.awt.*;
import java.awt.event.*;
import java.applet.*;
import javax.swing.*;
public class SpingPong extends Applet implements KeyListener, MouseListener{
    int width, height, gameSpeed, player1score, player2score;
    boolean running = true, startscreen = true;
    Dimension offDimension;
    Image offImage;
    Graphics offGraphics;
    Paddle paddle1;
    Paddle paddle2;
    Ball ball;
    SpingThing net;
    SpingThing table;
    public void init(){
        width = getSize().width;
        height = getSize().height;
        addKeyListener(this);
        addMouseListener(this);
        gameSpeed = 15;
        net = new SpingThing(150,"net");
        table = new SpingThing(200,"table");
        paddle1 = new Paddle(50,new Vector(100,getSize().height/2), 3*Math.PI/4);
        paddle2 = new Paddle(50,new Vector(getSize().width - 100, getSize().height/2), Math.PI/4);
        ball = new Ball(10, new Vector(getSize().width/2,getSize().height/2),new Vector(0,0));
        player1score = 0;
        player2score = 0;
        Friction fric = new Friction();
        fric.start();
    }
    public void start(){
        
    }
    public void update(Graphics g){
        width = getSize().width;
        height = getSize().height;
        net.pos.x = width/2;
        net.pos.y = height/2 + 150;
        table.pos.x = 0;
        table.pos.y = height - 10;
        table.size = width;
        /*
         * Shapes:
         * -Line g.drawLine(int x1, int y1, int x2, int y2);
         * -Rectangle 
         *     filled in: g.fillRect(int x, int y, int width, int height);
         *     not filled in: g.drawRect(int x, int y, int width, int height);
         * -Circles
         *     same as rectangle except fillOval and drawOval
         * Change Color:
         *  g.setColor(Colors.red);
         * Change font:
         *  g.setFont(Font font);
        */
        if ((offGraphics == null) || (getSize().width != offDimension.width) || (getSize().height != offDimension.height) ) {
            offDimension = getSize();
            offImage = createImage(getSize().width, getSize().height);
            offGraphics = offImage.getGraphics();
            g.drawImage(offImage, 0, 0, this);
        }
        if(startscreen){
            offGraphics.setColor(Color.black);
            offGraphics.fillRect(0,0,width,height);
            g.drawImage(offImage, 0, 0, this);
        }else{
            startscreen = false;
            //Background
            offGraphics.setColor(Color.black);
            offGraphics.fillRect(0, 0, getSize().width, getSize().height);
            //Net 
            offGraphics.setColor(Color.white);
            offGraphics.drawLine((int)net.pos.x,(int)net.pos.y,(int)net.pos.x,(int)(net.pos.y - net.size));
            //Table
            offGraphics.fillRect(300, height/2 + 150,(int)( width - 600), 10);
            //Scoreboard
            offGraphics.setFont(new Font("Futura",Font.PLAIN, 55));
            offGraphics.drawString(Integer.toString(player1score),getSize().width/2 - 50, 65);
            offGraphics.drawString(Integer.toString(player2score),getSize().width/2 + 20, 65);
            //Paddle1
            int tempx = (int) (50*Math.cos(Math.PI*2 - paddle1.ang));
            int tempy = (int) (50*Math.sin(Math.PI*2 - paddle1.ang));
            offGraphics.drawLine((int)(paddle1.pos.x + tempx),(int)( paddle1.pos.y + tempy), (int)(paddle1.pos.x - tempx), (int)(paddle1.pos.y - tempy));
            //Paddle2
            tempx = (int) (50*Math.cos(Math.PI*2 - paddle2.ang));
            tempy = (int) (50*Math.sin(Math.PI*2 - paddle2.ang));
            offGraphics.drawLine((int)(paddle2.pos.x + tempx),(int)( paddle2.pos.y + tempy), (int)(paddle2.pos.x - tempx), (int)(paddle2.pos.y - tempy));
            //Ball
            offGraphics.setColor(new Color(255,255,255));
            offGraphics.fillOval((int)ball.pos.x - 5, (int)ball.pos.y - 5, 10, 10);
            g.drawImage(offImage, 0, 0, this);
        }
    }
    public void paint(Graphics g){
        update(g);
    }
    public void mouseExited( MouseEvent e ) { }
    public void mousePressed( MouseEvent e ) { }
    public void mouseReleased( MouseEvent e ) { }
    public void mouseClicked( MouseEvent e ) { }
    public void mouseDragged( MouseEvent e ) { }
    public void mouseEntered( MouseEvent e ) { }
    public void mouseMoved( MouseEvent e ) { }
    public void keyTyped( KeyEvent e ) { }
    public void keyPressed(KeyEvent e ) {
        char c = e.getKeyChar();
        int keyCode = e.getKeyCode();
        /*
        Example:
        if(c == 'w'){
            moveUp();
        }
        Make sure to stop the action in keyReleased!
        */
        if(c == 'b'){
            running = false;
            stop();
            destroy();
            System.exit(0);
        }
        if(c == '1'){
            startscreen = false;
        }
        //Paddle1
        //  Movement
        if(c == 'w'){
            paddle1.acc.y = -1;
        }
        if(c == 'a'){
            paddle1.acc.x = -1;
        }
        if(c == 's'){
            paddle1.acc.y = 1;
        }
        if(c == 'd'){
            paddle1.acc.x = 1;
        }
        //  Spin
        if(c == 'f'){
            paddle1.alp = .01;
        }
        if(c == 'g'){
            paddle1.alp = -.01;
        }
        if(c == 'p')
        {
            System.out.println(paddle1);
        }
        //Paddle2
        //  Movement
        if(keyCode == KeyEvent.VK_UP){
            paddle2.acc.y = -1;
        }
        if(keyCode == KeyEvent.VK_DOWN){
            paddle2.acc.y = 1;
        }
        if(keyCode == KeyEvent.VK_LEFT){
            paddle2.acc.x = -1;
        }
        if(keyCode == KeyEvent.VK_RIGHT){
            paddle2.acc.x = 1;
        }
        //  Spin
        if(c == '1'){
            paddle2.alp = .01;
        }
        if(c == '2'){
            paddle2.alp = -.01;
        }
        repaint();
        e.consume();
    }
    public void keyReleased( KeyEvent e ) {
        char c = e.getKeyChar();
        int keyCode = e.getKeyCode();
        //Paddle1
        //  Movement
        if(c == 'w'){
            paddle1.acc.y = 0;
        }
        if(c == 'a'){
            paddle1.acc.x = 0;
        }
        if(c == 's'){
            paddle1.acc.y = 0;
        }
        if(c == 'd'){
            paddle1.acc.x = 0;
        }
        //  Spin
        if(c == 'f'){
            paddle1.alp = 0;
        }
        if(c == 'g'){
            paddle1.alp = 0;
        }
        //Paddle2
        //  Movement
        if(keyCode == KeyEvent.VK_UP){
            paddle2.acc.y = 0;
        }
        if(keyCode == KeyEvent.VK_DOWN){
            paddle2.acc.y = 0;
        }
        if(keyCode == KeyEvent.VK_LEFT){
            paddle2.acc.x = 0;
        }
        if(keyCode == KeyEvent.VK_RIGHT){
            paddle2.acc.x = 0;
        }
        //  Spin
        if(c == '1'){
            paddle2.alp = 0;
        }
        if(c == '2'){
            paddle2.alp = 0;
        }
    }
    class Friction extends Thread{
        public void run(){
            while(true){
                paddle1.vel.x *= .75;
                paddle1.vel.y *= .75;
                paddle1.omg *= .95;
                paddle2.vel.x *= .75;
                paddle2.vel.y *= .75;
                paddle2.omg *= .95;
                try{Thread.sleep(40);}catch(Exception k){}
            }
        }
    }
    public static void main(String[] args){
        JFrame frame = new JFrame();
        frame.setSize(1922, 1030);
        final SpingPong game = new SpingPong();
        frame.getContentPane().add(game);
        frame.addWindowListener(new WindowAdapter() 
        {
           public void windowClosing(WindowEvent we){
               game.stop();
               game.destroy();
               System.exit(0);
            }
        });
        game.init();
        game.start();
        frame.setVisible(true);
        game.repaint();
        game.paddle1.pos.x = 200;
        game.paddle1.pos.y = game.getSize().height/2;
        game.paddle2.pos.x = game.getSize().width - 200;
        game.paddle2.pos.y = game.getSize().height/2;
        game.ball.pos.x = 200;
        game.ball.pos.y = game.getSize().height/2 - 400;
        while(game.running){
            //Game Loop
            game.paddle1.timeInc(1);
            game.paddle2.timeInc(1);
            try{Thread.sleep(game.gameSpeed);}catch(Exception k){}
            game.repaint();
        }
    }
}
