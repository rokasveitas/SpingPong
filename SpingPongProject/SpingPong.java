import java.awt.*;
import java.awt.event.*;
import java.applet.*;
import javax.swing.*;
import java.io.*;
import java.net.URL;
import javax.sound.sampled.*;
import javax.swing.*;
public class SpingPong extends Applet implements KeyListener, MouseListener{
    public static final double TBL_BOUNCE = .75; //coef of elasticity of ball/table collision
    public static final double WALL_BOUNCE = .5; //coef of elast of paddle/wall collision
    public static final double NET_BOUNCE = .35;
    int width, height, gameSpeed, player1score, player2score, whoWon;
    boolean running = true, startscreen = true, rules = false, serve = false, abovep1 = true, abovep2 = false, prevAbovep1 = false, prevAbovep2 = false, swathp1 = false, swathp2 = false, gameover = false;
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
        paddle1.which = 1;
        paddle2 = new Paddle(50,new Vector(getSize().width - 100, getSize().height/2), Math.PI/4);
        paddle2.which = 2;
        ball = new Ball(10, new Vector(getSize().width/2,getSize().height/2),new Vector(0,0));
        player1score = 0;
        player2score = 0;
        Friction fric = new Friction();
        fric.start();
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("C:\\Users\\veitasr8238\\Desktop\\SpingPong\\hiphopwii.wav").getAbsoluteFile());
            Clip c = AudioSystem.getClip();
            c.open(audioInputStream);
            FloatControl gainControl = (FloatControl) c.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(-20.0f);
            c.start();
        } catch(Exception ex) {
            //System.out.println("Error.");
            ex.printStackTrace();
        }
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
            offGraphics.fillRect(0, 0, getSize().width, getSize().height);
            offGraphics.setColor(Color.white);
            Font h = new Font("Impact", Font.BOLD, 108);
            offGraphics.setFont(h);
            offGraphics.drawString("Sping Pong",650, 200);
            //creating 'Sping Pong' text
        
        
            offGraphics.setColor(Color.white);
            Font z = new Font("Impact", Font.BOLD, 32);
            offGraphics.setFont(z);
            offGraphics.drawString("Press '1' to play       Press '?' for help",675, 550);
            //creating text instructions
        
            offGraphics.setColor(Color.blue);  
            offGraphics.drawLine(880,400,820,350);
            //creating blue paddle graphic
        
            offGraphics.setColor(Color.red);  
            offGraphics.drawLine(1000,400,1060,350);
            //creating red paddle graphic

        
            offGraphics.setColor(Color.black);
            offGraphics.drawOval(900, 300, 80, 80);
            offGraphics.drawOval(920, 325, 5, 5);
            offGraphics.drawOval(955, 325, 5, 5);
            offGraphics.drawArc(923,265,115,20,0,-180);
            offGraphics.setColor(Color.yellow);
            offGraphics.fillOval(900, 300, 80, 80);
            offGraphics.setColor(Color.black);
            offGraphics.fillOval(920, 325, 5, 5);
            offGraphics.fillOval(955, 325, 5, 5);
            offGraphics.fillArc(923, 345, 35, 20, 0, -180);

            //creating smiley ping pong graphic

            if(rules){
               offGraphics.setColor(Color.pink); 
               offGraphics.fillRect(0, 0, getSize().width, getSize().height);
               
               offGraphics.setFont(h);
               offGraphics.setColor(Color.red);
               offGraphics.drawString("Press m to Return to Menu" , 340 , 220);  
               
               
               Font i = new Font("Impact", Font.ITALIC, 30);
               offGraphics.setFont(i);
               offGraphics.setColor(Color.blue);
               offGraphics.drawString("Press the Space bar to drop the ball and serve to your opponent.", 610, 300);
               offGraphics.drawString(" Players will switch off serving every two points. A point is scored", 600, 330);
               offGraphics.drawString("when a player fails to serve correctly, fails to return the ball,", 600, 360);
               offGraphics.drawString("or a player strikes the ball with his/her paddle more than once in ", 600, 390); 
               offGraphics.drawString("succession. The first player to reach 11 points wins the game, however,", 600, 420);
               offGraphics.drawString(" a game must be won by at least a two point margin.", 600, 450);
                              
               Font p = new Font("Impact", Font.ITALIC, 60);
               offGraphics.setFont(p);
               offGraphics.setColor(Color.red);
               offGraphics.drawString("Controls:" , 700 , 620); 
               Font y = new Font("Impact", Font.ITALIC, 35);
               offGraphics.setFont(y);
               offGraphics.setColor(Color.black);
               offGraphics.drawString("Player 1:" , 700 , 670);
               Font j = new Font("Impact", Font.ITALIC, 30);
               offGraphics.setFont(j);
               offGraphics.setColor(Color.blue);
               offGraphics.drawString("WASD, FG (Rotate left/right)" , 700 , 710);
               Font o = new Font("Impact", Font.ITALIC, 35);
               offGraphics.setFont(o);
               offGraphics.setColor(Color.black);
               offGraphics.drawString("Player 2:" , 700 , 755);
               Font f = new Font("Impact", Font.ITALIC, 30);
               offGraphics.setFont(f);
               offGraphics.setColor(Color.blue);
               offGraphics.drawString("Arrow keys, 1 and 2 (Rotate left/right)" , 700 , 795);
               g.drawImage(offImage, 0, 0, this);
            }
            if(gameover){
                
                
               //Background color
               offGraphics.setColor(Color.black);
               offGraphics.fillRect(0,0, getSize().width, getSize().height);
               
               //Player 1 wins
               offGraphics.setFont(h);
               offGraphics.setColor(Color.red);
               String winner = "";
               if(whoWon == 1){
                   winner = "Player 1 ";
               }else{
                   winner = "Player 2 ";
               }
                
               offGraphics.drawString(winner +"Wins!!! " + "\n\n\n" , 620 , 400);  
               
               //Back to home screen
               offGraphics.setColor(Color.yellow);
               offGraphics.fillRect(800, 600, 320, 50);
               
               Font i = new Font("Impact", Font.ITALIC, 25);
               offGraphics.setFont(i);
               offGraphics.setColor(Color.blue);
               offGraphics.drawString("Press 'p' to return to menu..." + "\n\n\n" , 813, 632);  
               
               //Smiley Face!
               offGraphics.setColor(Color.black);
               offGraphics.drawOval(920, 450, 80, 80);
               offGraphics.drawOval(940, 475, 10, 10);
               offGraphics.drawOval(975, 475, 10, 10);
               offGraphics.drawArc(943, 415, 1, 20, 0 , -180);
               offGraphics.setColor(Color.yellow);
               offGraphics.fillOval(920, 450, 80, 80);
               offGraphics.setColor(Color.black);
               offGraphics.fillOval(940, 475, 5, 5);
               offGraphics.fillOval(975, 475, 5, 5);
               offGraphics.fillArc(943, 495, 35, 20, 0, -180);
               
               g.drawImage(offImage, 0, 0, this);
               
               
            }
            g.drawImage(offImage, 0, 0, this);
        }else{ //real game graphics
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
            offGraphics.fillOval((int)ball.pos.x - 6, (int)ball.pos.y - 6, 12, 12);
            if(ball.spin > 8)
                offGraphics.setColor(new Color(0, 0, 51));
            else if(ball.spin > 7)
                offGraphics.setColor(new Color(0, 0, 102));
            else if(ball.spin > 6)
                offGraphics.setColor(new Color(0, 0, 153));
            else if(ball.spin > 5)
                offGraphics.setColor(new Color(0, 0, 204));
            else if(ball.spin > 4)
                offGraphics.setColor(new Color(0, 0, 255));
            else if(ball.spin > 3)
                offGraphics.setColor(new Color(51, 51, 255));
            else if(ball.spin > 2)
                offGraphics.setColor(new Color(102, 102, 255));
            else if(ball.spin > 1)
                offGraphics.setColor(new Color(153, 153, 255));
            else if(ball.spin > 0)
                offGraphics.setColor(new Color(204, 204, 255));   
            else if(ball.spin == 0)
                offGraphics.setColor(new Color(255, 255, 255));
            else if(ball.spin > -1)
                offGraphics.setColor(new Color(255, 204, 204));
            else if(ball.spin > -2)
                offGraphics.setColor(new Color(255, 153, 153));
            else if(ball.spin > -3)
                offGraphics.setColor(new Color(255, 102, 102));
            else if(ball.spin > -4)
                offGraphics.setColor(new Color(255, 51, 51));
            else if(ball.spin > -5)
                offGraphics.setColor(new Color(255, 0, 0));
            else if(ball.spin > -6)
                offGraphics.setColor(new Color(204, 0, 0));
            else if(ball.spin > -7)
                offGraphics.setColor(new Color(153, 0, 0));
            else if(ball.spin > -8)
                offGraphics.setColor(new Color(102, 0, 0));
            else if(ball.spin > -9)
                offGraphics.setColor(new Color(51, 0, 0));
            else
                offGraphics.setColor(new Color(255, 255, 255));
            offGraphics.fillOval((int)ball.pos.x - 5, (int)ball.pos.y - 5, 10, 10);
            g.drawImage(offImage, 0, 0, this);
            offGraphics.setColor(new Color(255, 255, 255));
        }
    }
    public void paint(Graphics g){
        update(g);
    }
    public void mouseExited( MouseEvent e ) { }
    public void mousePressed( MouseEvent e ) { }
    public void mouseReleased( MouseEvent e ) { }
    public void mouseClicked( MouseEvent e )
    {
        //System.out.println(e.getPoint());
    }
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
        if(c == '6'){
            Sound meme = new Sound();
        }
        if(c == 'b'){
            running = false;
            stop();
            destroy();
            System.exit(0);
        }
        //Title Screen
        //  Start Screen
        if(c == '1'){
            startscreen = false;
        }
        //      Rules
        if(c == '?'){
            rules = true;
        }
        if(c == 'm'){
            rules = false;
        }
        //Serve
        if(keyCode == KeyEvent.VK_SPACE || c == '0'){
            serve = true;
        // //System.out.println("Space");
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

        if(c == 'j')
        {
            paddle1.alp = 0;
            paddle1.omg = 0;
        }

        if(c == 'h')
        {
            paddle1.omg = 0;
            paddle1.alp = 0;
        }

        // Spin, but vel instead of alp
        if(c == 'o')
        {
            paddle1.omg = .05;
        }
        if(c == 'i')
        {
            paddle1.omg = -.05;
        }
        //Debug
        /*
        if(c == 't')
            {
            //System.out.println(paddle2);
        }
        if(c == 'p')
        {
            gameover = false;
            //System.out.println(ball);
        }
        if(c == 'q')
        {
            try{ball.collide(paddle1);} catch(Exception e1){}
        }
        if(c == 'e')
            {
            try{ball.collide(paddle2);} catch(Exception e2){}
        }
        if(c == 'z')
        {
            startscreen = true;
        }
        if(c == '7')
        {
            ball.vel = new Vector(0, 0);
        }
        */
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

        if(c == '3')
        {
            paddle2.omg = 0;
            paddle2.alp = 0;
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
            if(paddle1.acc.y < 0){
                paddle1.acc.y = 0;
            }
        }
        if(c == 'a'){
            if(paddle1.acc.x < 0){
                paddle1.acc.x = 0;
            }
        }
        if(c == 's'){
            if(paddle1.acc.y > 0){
                paddle1.acc.y = 0;
            }
        }
        if(c == 'd'){
            if(paddle1.acc.x > 0){
                paddle1.acc.x = 0;
            }
        }
        //  Spin
        if(c == 'f'){
            if(paddle1.alp > 0){
                paddle1.alp = 0;
            }
        }
        if(c == 'g'){
            if(paddle1.alp < 0){
                paddle1.alp = 0;
            }
        }
        if(c == 'o' || c == 'i')
        {
            paddle1.omg = 0;
        }
        //Paddle2
        //  Movement
        if(keyCode == KeyEvent.VK_UP){
            if(paddle2.acc.y < 0){
                paddle2.acc.y = 0;
            }
        }
        if(keyCode == KeyEvent.VK_DOWN){
            if(paddle2.acc.y > 0){
                paddle2.acc.y = 0;
            }
        }
        if(keyCode == KeyEvent.VK_LEFT){
            if(paddle2.acc.x < 0){
                paddle2.acc.x = 0;
            }
        }
        if(keyCode == KeyEvent.VK_RIGHT){
            if(paddle2.acc.x > 0){
                paddle2.acc.x = 0;
            }
        }
        //  Spin
        if(c == '1'){
            if(paddle2.alp > 0){
                paddle2.alp = 0;
            }
        }
        if(c == '2'){
            if(paddle2.alp < 0){
                paddle2.alp = 0;
            }
        }
        
        
    }
    class Friction extends Thread{
        public void run(){
            while(true){
                paddle1.vel.x *= .8;
                paddle1.vel.y *= .8;
                paddle1.omg *= .95;
                paddle2.vel.x *= .8;
                paddle2.vel.y *= .8;
                paddle2.omg *= .95;
                ball.vel.x *= .999;
                ball.vel.y *= .999;
                try{Thread.sleep(40);}catch(Exception k){}
            }
        }
    }
    Point closestpointonline(float lx1, float ly1, float lx2, float ly2, float x0, float y0){
        float A1 = ly2 - ly1;
        float B1 = lx1 - lx2;
        double C1 = A1*lx1 + B1*ly1;
        double C2 = -B1*x0 + A1*y0;
        double det = A1*A1 - -B1*B1;
        double cx = 0;
        double cy = 0;
        if(det != 0){
            cx = (float)((A1*C1 - B1*C2)/det);
            cy = (float)((A1*C2 - -B1*C1)/det);
        }else{
            cx = x0;
            cy = y0;
        }
        return new Point((int)(cx+.5),(int)(cy+.5));
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
        game.ball.pos.x = game.getSize().width/2;
        game.ball.pos.y = game.getSize().height/2 - 400;
        //Game rule variables
        boolean pointscored = true;
        String server = "one";
        int lefthit = 0,righthit = 0,p1hit = 0,p2hit = 0, nexthitter = 1, servecount = 0, delay = 21;
        while(game.running){
            //Game Loop
            // //System.out.println("In loop");
            // //System.out.println("Startscreen is " + game.startscreen);
            //StartScreen Loop
            if(game.startscreen){
                game.paddle1.pos.x = 200;
                game.paddle1.pos.y = game.getSize().height/2;
                game.paddle2.pos.x = game.getSize().width - 200;
                game.paddle2.pos.y = game.getSize().height/2;
                game.ball.pos.x = game.paddle1.pos.x;
                game.ball.pos.y = game.getSize().height/2 - 400;
                game.ball.vel = new Vector(0, 0);
            }
            //Point Scored Reset
            if(pointscored){
                //System.out.println("pointscored true");
                game.serve = false;
                game.paddle1.pos.x = 200;
                game.paddle1.pos.y = game.getSize().height/2;
                game.paddle2.pos.x = game.getSize().width - 200;
                game.paddle2.pos.y = game.getSize().height/2;
                game.ball.vel = new Vector(0, 0);
                game.ball.spin = 0;
                if(servecount > 1){
                    if(server.equals("one")){
                        server = "two";
                    }else{
                        server = "one";
                    }
                    servecount = 0;
                }
                if(server.equals("one")){
                    game.ball.pos.x = game.paddle1.pos.x;
                    lefthit = 1;
                    righthit = 0;
                    nexthitter = 1;
                    servecount++;
                }else if(server.equals("two")){
                    game.ball.pos.x = game.paddle2.pos.x;
                    righthit = 1;
                    lefthit = 0;
                    nexthitter = 2;
                    servecount++;
                }
                game.ball.pos.y = game.getSize().height/2 - 400;
                
                game.abovep1 = (game.ball.pos.y) <= (Math.tan(game.paddle1.ang) * (game.paddle1.pos.x - game.ball.pos.x) + game.paddle1.pos.y);
                game.abovep2 = (game.ball.pos.y) <= (Math.tan(game.paddle2.ang) * (game.paddle2.pos.x - game.ball.pos.x) + game.paddle2.pos.y);
                game.prevAbovep1 = game.abovep1;
                game.prevAbovep2 = game.abovep2;
                
                double p1dx = Math.abs(50 * Math.cos(game.paddle1.ang));
                double p2dx = Math.abs(50 * Math.cos(game.paddle2.ang));
                
                game.swathp1 = game.paddle1.pos.x - p1dx <= game.ball.pos.x && game.ball.pos.x <= game.paddle1.pos.x + p1dx;
                game.swathp2 = game.paddle2.pos.x - p2dx <= game.ball.pos.x && game.ball.pos.x <= game.paddle2.pos.x + p2dx;
                    
                try{Thread.sleep(500);}catch(Exception e){}
                pointscored = false;
            }
            //Check collisions
            // //System.out.println("Checking collinsions");
            int p1tempx = (int) Math.abs(50*Math.cos(Math.PI*2 - game.paddle1.ang));
            int p1tempy = (int) Math.abs(50*Math.sin(Math.PI*2 - game.paddle1.ang));
            int p2tempx = (int) Math.abs(50*Math.cos(Math.PI*2 - game.paddle2.ang));
            int p2tempy = (int) Math.abs(50*Math.sin(Math.PI*2 - game.paddle2.ang));
            //Left wall
            if(game.paddle1.pos.x - p1tempx <= 0){
                if(game.paddle1.vel.x < 0){
                    game.paddle1.vel.x *= -WALL_BOUNCE;
                    //System.out.println("Wall Bounce");
                }
            }
            if(game.ball.pos.x <= 0){
                if(nexthitter == 1){
                    if(lefthit == 1){
                        pointscored = true;
                        game.player2score++;
                    }
                    if(lefthit == 0){
                        pointscored = true;
                        game.player1score++;
                    }
                }
                if(nexthitter == 2){
                    pointscored = true;
                    game.player2score++;
                }
            }
            //Right wall
            if(game.paddle2.pos.x + p2tempx >= game.getSize().width){
                if(game.paddle2.vel.x > 0){
                    game.paddle2.vel.x *= -WALL_BOUNCE;
                }
            }
            if(game.ball.pos.x >= game.getSize().width){
                if(nexthitter == 2){
                    if(righthit == 1){
                        pointscored = true;
                        game.player1score++;
                    }
                    if(righthit == 0){
                        pointscored = true;
                        game.player2score++;
                    }
                }
                if(nexthitter == 1){
                    pointscored = true;
                    game.player1score++;
                }
            }
            //Middle wall
            if(game.paddle1.pos.x + p1tempx >= game.getSize().width/2){
                if(game.paddle1.vel.x > 0){
                    game.paddle1.vel.x *= -WALL_BOUNCE;
                }
            }
            if(game.paddle2.pos.x - p2tempx <= game.getSize().width/2){
                if(game.paddle2.vel.x < 0){
                    game.paddle2.vel.x *= -WALL_BOUNCE;
                }
            }
            //Upper wall
            if(game.paddle1.pos.y - p1tempy <= 0){
                if(game.paddle1.vel.y < 0){
                    game.paddle1.vel.y *= -WALL_BOUNCE;
                }
            }
            if(game.paddle2.pos.y - p2tempy <= 0){
                if(game.paddle2.vel.y < 0){
                    game.paddle2.vel.y *= -WALL_BOUNCE;
                }
            }
            //Lower wall
            if(game.paddle1.pos.y + p1tempy >= game.getSize().height){
                if(game.paddle1.vel.y > 0){
                    game.paddle1.vel.y *= -WALL_BOUNCE;
                }
            }
            if(game.paddle2.pos.y + p2tempy >= game.getSize().height){
                if(game.paddle2.vel.y > 0){
                    game.paddle2.vel.y *= -WALL_BOUNCE;
                }
            }
            if(game.ball.pos.y >= game.getSize().height){
                if(nexthitter == 1){
                    if(lefthit == 1){
                        pointscored = true;
                        game.player2score++;
                    }
                    if(lefthit == 0){
                        pointscored = true;
                        game.player1score++;
                    }
                }
                if(nexthitter == 2){
                    if(righthit == 1){
                        pointscored = true;
                        game.player1score++;
                    }
                    if(righthit == 0){
                        pointscored = true;
                        game.player2score++;
                    }
                }
            }
            //Table
            if(game.ball.pos.y >= game.getSize().height/2 + 150 && (game.ball.pos.x <= game.getSize().width - 300 && game.ball.pos.x >= 300)){
                if(game.ball.vel.y > 0){
                    try {
                        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("C:\\Users\\veitasr8238\\Desktop\\SpingPong\\PONG.wav").getAbsoluteFile());
                        Clip c = AudioSystem.getClip();
                        c.open(audioInputStream);
                        c.start();
                    } catch(Exception ex) {
                        //System.out.println("Error.");
                        ex.printStackTrace();
                    }
                    //System.out.println("Colliding with table");
                    try{game.ball.collide(game.table);}catch(Exception k){ }
                    if(game.ball.pos.x <= game.getSize().width/2){
                        lefthit++;
                        if(lefthit > 1){
                            //System.out.println(lefthit);
                            pointscored = true;
                            game.player2score++;
                        }
                        if(nexthitter == 2){
                            //System.out.println(lefthit);
                            pointscored = true;
                            game.player2score++;
                        }
                    }else{
                        righthit++;
                        if(righthit > 1){
                            //System.out.println(righthit);
                            pointscored = true;
                            game.player1score++;
                        }
                        if(nexthitter == 1){
                            //System.out.println(righthit);
                            pointscored = true;
                            game.player1score++;
                        }
                    }
                }
            }
            // //System.out.println("Should be checking ball hitting paddle...");
            
            
            game.prevAbovep1 = game.abovep1;
            game.prevAbovep2 = game.abovep2;
            game.abovep1 = (game.ball.pos.y) <= (Math.tan(game.paddle1.ang) * (game.paddle1.pos.x - game.ball.pos.x) + game.paddle1.pos.y);
            game.abovep2 = (game.ball.pos.y) <= (Math.tan(game.paddle2.ang) * (game.paddle2.pos.x - game.ball.pos.x) + game.paddle2.pos.y);
            
            double p1dx = Math.abs(50 * Math.cos(game.paddle1.ang));
            double p2dx = Math.abs(50 * Math.cos(game.paddle2.ang));
            
            game.swathp1 = game.paddle1.pos.x - p1dx <= game.ball.pos.x && game.ball.pos.x <= game.paddle1.pos.x + p1dx;
            game.swathp2 = game.paddle2.pos.x - p2dx <= game.ball.pos.x && game.ball.pos.x <= game.paddle2.pos.x + p2dx;
            //ball x paddle1
            if((game.prevAbovep1 != game.abovep1) && game.swathp1 && delay > 50){
                try {
                    AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("C:\\Users\\veitasr8238\\Desktop\\SpingPong\\PONG.wav").getAbsoluteFile());
                    Clip c = AudioSystem.getClip();
                    c.open(audioInputStream);
                    c.start();
                } catch(Exception ex) {
                    //System.out.println("Error.");
                    ex.printStackTrace();
                }
                try{game.ball.collide(game.paddle1);} catch(Exception v){};
                if(nexthitter == 2){
                    pointscored = true;
                    game.player2score++;
                }
                if(nexthitter == 1){
                    if(lefthit == 0){
                        pointscored = true;
                        game.player2score++;
                    }
                    nexthitter = 2;
                }
                lefthit = 0;
                righthit = 0;
                delay = 0;
            }
            //ball x paddle2
            if((game.prevAbovep2 != game.abovep2) && game.swathp2 && delay > 50){
                try {
                    AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("C:\\Users\\veitasr8238\\Desktop\\SpingPong\\PONG.wav").getAbsoluteFile());
                    Clip c = AudioSystem.getClip();
                    c.open(audioInputStream);
                    c.start();
                } catch(Exception ex) {
                    //System.out.println("Error.");
                    ex.printStackTrace();
                }
                try{game.ball.collide(game.paddle2);} catch(Exception v1){};
                if(nexthitter == 1){
                    pointscored = true;
                    game.player1score++;
                }
                if(nexthitter == 2){
                    if(righthit == 0){
                        pointscored = true;
                        game.player1score++;
                    }
                    nexthitter = 1;
                }
                lefthit = 0;
                righthit = 0;
                delay = 0;
            }
            
            //Ball x Net
            if(game.ball.pos.x <= game.getSize().width/2 && game.ball.pos.y >= game.getSize().height/2){
                if(game.ball.pos.x + game.ball.vel.x >= game.getSize().width/2){
                    game.ball.vel.x *= -NET_BOUNCE;
                }
            }else if(game.ball.pos.x + game.ball.vel.x <= game.getSize().width/2 && game.ball.pos.y >= game.getSize().height/2){
                game.ball.vel.x *= -NET_BOUNCE;
            }
            //Moves the things
            //Checks for game win
            if(game.player1score > 10 || game.player2score > 10){
                if(Math.abs(game.player1score - game.player2score) > 1){
                    if(game.player1score > game.player2score)
                        game.whoWon = 1;
                    else
                        game.whoWon = 2;
                    game.player1score = 0;
                    game.player2score = 0;
                    lefthit = 0; righthit = 0;p1hit = 0;p2hit = 0; nexthitter = 1; servecount = 0; server = "one";
                    game.startscreen = true;
                    game.gameover = true;
                }
            }
            //    //System.out.println("Moving things.");
            game.paddle1.timeInc(1);
            game.paddle2.timeInc(1);
            if(game.serve){
                game.ball.timeInc(1);
                //  //System.out.println("Served");
            }
            delay++;
            try{Thread.sleep(game.gameSpeed);}catch(Exception k){}
            // //System.out.println("Repainting");
            game.repaint();
            ////System.out.println("Ending loop");
        }
    }
}
