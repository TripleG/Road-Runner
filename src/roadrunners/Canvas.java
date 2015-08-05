
package roadrunners;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import sun.tools.jar.Main;


public class Canvas extends JPanel{
    public static final int HEIGHT = Game.HEIGHT;
    public static final int WIDTH = Game.WIDTH;
    private BufferedImage playerIMG;
    private BufferedImage enemyIMG;
    private BufferedImage treeIMG;
    private CarPlayer player = new CarPlayer();
    static Font font = new Font("TimesRoman", Font.BOLD, 16);
    private JLabel labelResult = new JLabel();
    private JLabel labelInstructions = new JLabel();
    
    static ArrayList<CarEnemy> enemies ;//= new ArrayList<CarEnemy>();
    
      
    private int enemySpeed = CarEnemy.enemySpeed;
    
    private Image offscreen = null;
    public static boolean isKeyRight = false;
    public static boolean isKeyLeft = false;
    
    private int treeY = 20;
    private int roadMarkY = 0;
    private final int numberOfMarks = 6;
    private final int roadMarkHeight = 50;
    private final int roadMarkWidth = 3;
    private Rectangle[] roadMarks = new Rectangle[numberOfMarks];
    
    
    public void init()
    {            
        enemies = Game.enemies;
        setSize(HEIGHT,WIDTH);
        playerIMG = player.loadImage();
        enemyIMG = CarEnemy.loadImage();
        player.setX(Canvas.WIDTH/2);
        player.setY(Canvas.HEIGHT - 49*2)  ;
        loadImage(); //tree image
        setDoubleBuffered(true);
        for(int i = 0; i < numberOfMarks; i++)
        {
            roadMarks[i] = new Rectangle(WIDTH/2,roadMarkY,roadMarkWidth,roadMarkHeight);
            roadMarkY+=100;
        }
        roadMarkY = 0;
        
  
        this.setBackground(Color.gray.darker().darker().darker());
  
    }
        
    public void restart()
    {
        player.setX(Canvas.WIDTH/2);
        player.setY(Canvas.HEIGHT - 49*2)  ;
        player.resetDistTravelled();
        player.isAlive = true;
        CarEnemy.enemySpeed=1;
        Game.gameSpeed=2;
    }
   public  CarPlayer getPlayer()
   {
       return player;
   }
    public void update()
    {
        
        //player.calcMovedDistance();
        player.isCollided();
        player.calcMovedDistance();
        if(isKeyRight)
        {
            if(player.getX() + Game.playerSpeed <= WIDTH/2 + 220 - CarPlayer.carW)
            {
                player.setX(player.getX() + Game.playerSpeed);
            }
           
            this.remove(labelInstructions);
        }
        if(isKeyLeft)
        {
            if(player.getX() - Game.playerSpeed >= WIDTH/2 - 220 )
            {
            
                player.setX(player.getX() - Game.playerSpeed);
            }
            this.remove(labelInstructions);
        }
        for( int i = 0; i < Game.numberOfRedEnemies; i++)
        {
          // enemies[i].move(); 
            enemies.get(i).move();
        }
        if(player.getMovedDistance() % 1200 == 0)
        {
            enemies.add(new CarEnemy());
           // System.out.println("N Enemies: " + enemies.size());
            CarEnemy.enemySpeed+=1;
            Game.gameSpeed+=2;
            System.out.println("game speed: " + Game.gameSpeed);
            System.out.println("enemy speed: " + CarEnemy.enemySpeed);
            
        }
        this.repaint();
    }
    

    
   
   
    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        
                
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
                              
                g.setColor(getBackground());
               // g.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);
                
                paintRoad(g);
                paintBorders(g);
		
                paintEnemies(g, enemies);
                paintPlayer(g);
  
                paintStatistics(g);
               
    }
    private void paintEnemies(Graphics g, ArrayList<CarEnemy> enemies)
    {
        for( int i = 0; i < Game.numberOfRedEnemies; i++)
        {
           //g.drawImage(enemyIMG, enemy.getX(),enemy.getY(), this);
            g.drawImage(enemyIMG, enemies.get(i).getX(),enemies.get(i).getY(), this);
        }
    }
    private void paintPlayer(Graphics g)
    {
        g.drawImage(playerIMG, player.getX(),player.getY(), this);
           
    }
    
    
    private void paintRoad(Graphics g)
    {   
            
        g.setColor(Color.white.darker().darker());
        for(int i = 0; i < numberOfMarks; i++)
        {
            g.drawRect(WIDTH/2, roadMarks[i].y, roadMarks[i].width, roadMarks[i].height);
            g.fillRect(WIDTH/2, roadMarks[i].y, roadMarks[i].width, roadMarks[i].height);
        if(roadMarks[i].y > HEIGHT)
        {
            roadMarks[i].y= -110;
        }
        else{
            roadMarks[i].y += Game.gameSpeed+1;//2;
        }
        }
        
        
    }
     
    private void paintBorders(Graphics g)
    {
        Rectangle borderL = new Rectangle(WIDTH/2 - 250,-1,30,HEIGHT-30);
        Rectangle borderR = new Rectangle(WIDTH/2 + 220,-1,30,HEIGHT-30);
              
        g.setColor(Color.yellow.darker());
        
        g.drawRect(borderL.x, borderL.y, borderL.width, borderL.height );
        g.fillRect(borderL.x, borderL.y, borderL.width, borderL.height );
        g.drawRect(borderR.x, borderR.y, borderR.width, borderR.height );
        g.fillRect(borderR.x, borderR.y, borderR.width, borderR.height);
        g.setColor(Color.green.darker().darker());
        
        g.fillRect(0,0,150,HEIGHT);
        g.fillRect(650,0,150,HEIGHT);
        
        
       // g.drawImage(treeIMG, 10, treeY, null);
       
    }

    private void paintStatistics(Graphics g)
    {
        g.setColor(Color.black.darker());
        g.setFont(font); 
        g.drawString("Distance Travelled: \n", 1, 16);
        g.drawString( Integer.toString(player.getMovedDistance()), 5, 36);
        
        g.drawString("Press ENTER", 2,100);
        g.drawString("  to restart", 2,120);        
    }
    
public void loadImage()
    {
        URL playerURL = Main.class.getResource("/Tree.png");
        
     try{
                //img = ImageIO.read(new File("C:/Users/BaiGergi/Desktop/CarPlayer.png"));
                treeIMG = ImageIO.read(playerURL);
                }
                catch (IOException e) 
                    {
                        e.printStackTrace();
                    }
     System.out.println("TREE loadImage called");
     //System.out.println(img.getHeight());
    
    }
     
public void moveTree()
{
    if(treeY <= HEIGHT)
    {
        treeY++;
    }
}
    
}

