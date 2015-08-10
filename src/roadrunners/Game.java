
package roadrunners;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import sun.tools.jar.Main;

public class Game implements KeyListener{
    public static final int HEIGHT = 500;
    public static final int WIDTH = 800;
    public static  int numberOfRedEnemies = 10;
    int FPS = 120;
    //CarPlayer player = new CarPlayer();
    public static Timer updateTimer;
    public static ArrayList<CarEnemy> enemies = new ArrayList<CarEnemy>();
    public static int gameSpeed = 2;
    public static int playerSpeed = 2;
    static Canvas canvas;
    static JFrame frame ;
    
    public int getFrameH()
    {
        return HEIGHT;
    }
    public int getFrameW()
    {
        return WIDTH;
    }
    
    public void init()// throws InterruptedException
    {  
        for( int i = 0; i < numberOfRedEnemies; i++)
        {
            CarEnemy tempEnemy = new CarEnemy();
            enemies.add(tempEnemy);

        }
    }
    
    public static void main(String[] args) 
    {  
          
       
       Timer enemySpawnTimer;
      frame = new JFrame("Road Runner");
       Game game = new Game();
      
       //canvas = new Canvas(game.player, game.enemy);
       canvas = new Canvas();
       canvas.init();
       game.init();
       frame.setResizable(false);
       frame.add(canvas);
       frame.setSize(game.getFrameW(), game.getFrameH() );
       frame.setLocationRelativeTo(null);
       frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       frame.setVisible(true);
       frame.addKeyListener(game);
       
      
               
       updateTimer = new Timer(1000/game.FPS, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {

            canvas.update();
        }
        });
       
       updateTimer.start();
       
       Thread trd = new Thread()
       {
           public void run() {
            
               System.out.println(Thread.currentThread().getName());
         }
       };
       trd.start();
        //canvas.repaint();
       
       
       enemySpawnTimer = new Timer(3000, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent ae) {

           // canvas.update();
        }
        });
       enemySpawnTimer.start();
        
       
    }

    
    public void keyPressed(KeyEvent e) {
       
        if(e.getKeyCode() == KeyEvent.VK_RIGHT)
        {
             //while(e.getKeyCode() != KeyEvent.VK_LEFT)
            Canvas.isKeyLeft = false;
            Canvas.isKeyRight = true;
           // System.out.println("Key pressed on EDT: " + SwingUtilities.isEventDispatchThread());
            

        }
        
        if(e.getKeyCode() == KeyEvent.VK_LEFT)
        {
           // while(e.getKeyCode() != KeyEvent.VK_RIGHT)
            Canvas.isKeyRight = false;
            Canvas.isKeyLeft = true;

        }
        if (e.getKeyCode() == KeyEvent.VK_ENTER && !canvas.getPlayer().isAlive)
        {
          enemies.clear();
          this.init();  
          canvas.restart();                   
         
            //updateTimer.start();
            updateTimer.restart();
        }
        

    }

    @Override
    public void keyReleased(KeyEvent e) {
     if( e.getKeyCode() == KeyEvent.VK_RIGHT)
     {
         Canvas.isKeyRight = false;
     }
     if( e.getKeyCode() == KeyEvent.VK_LEFT)
     {
         Canvas.isKeyLeft = false;
     }
    
    }
     @Override
    public void keyTyped(KeyEvent e) {
    
    }

}
