import java.awt.*;
import java.awt.event.*;
import java.applet.*;
import javax.swing.*;
public class SpingPong extends Applet implements KeyListener, MouseListener{
    int width, height, gameSpeed;
    boolean running = true, startscreen = true;
    Dimension offDimension;
    Image offImage;
    Graphics offGraphics;
    public void init(){
        width = getSize().width;
        height = getSize().height;
        addKeyListener(this);
        addMouseListener(this);
        gameSpeed = 15;
    }
    public void start(){
        
    }
    public void update(Graphics g){
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
        }else{
            startscreen = false;
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
        repaint();
        e.consume();
    }
    public void keyReleased( KeyEvent e ) {
        char c = e.getKeyChar();
        int keyCode = e.getKeyCode();
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
        while(game.running){
            //Game Loop
            try{Thread.sleep(game.gameSpeed);}catch(Exception k){}
            game.repaint();
        }
    }
}
