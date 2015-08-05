
package roadrunners;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.imageio.ImageIO;
import sun.tools.jar.Main;


public class CarEnemy {
    
    private static BufferedImage img;
    private int x;
    private int y;
    
    private int carH = 49;
    private int carW = 26;
    
    Random rand = new Random();
    
    public static int enemySpeed = Game.gameSpeed;

    
    static ArrayList<CarEnemy> enemies = Game.enemies;
    
    boolean isFarEnough = true;
 
    int randHelper = 1;
    int newX ;
    int newY;
    
    private void genCoords()
    {
        isFarEnough = true;
        newX = rand.nextInt( 620 - 180 - 26) + 180 ; 
        newY  = rand.nextInt( 10) - rand.nextInt(2000) - 1000;
    }
    
    private void farEnough()
    {
      
        do
        {
            genCoords();
            
            Rectangle newEnemyRect = new Rectangle(newX, newY, carW, carH);
            
            for( int i = 0; i < enemies.size(); i++)
            {
                CarEnemy tempCar = enemies.get(i);
                Rectangle intercept = new Rectangle(tempCar.getX()
                , tempCar.getY()
                , tempCar.carW
                , tempCar.carH);
                
                if( newEnemyRect.intersects(intercept))
                {
                    System.out.println("Interception occured, car: "+i);
                    isFarEnough = false;
                }
            }
            
                
        } while(!isFarEnough);
        this.x = newX;
        this.y = newY;
        

    }
    
    public CarEnemy()
    {  
      // rand = new Random(); 
      // x = rand.nextInt( 620 - 180 - 26) + 180 ; 
      // y = rand.nextInt( 10) - rand.nextInt(2000) - 1000;
        farEnough();
    }
  
    public CarEnemy getEnemy()
    {
        return this;
    }
    
    public void setX(int x)
    {
        this.x = x;
    }
    public void setY(int y)
    {
        this.y = y;
    }
    
    public int getX()
    {
        return x;
    }
    
    public int getY()
    {
        return y;
    }
          
     static BufferedImage loadImage()
    {
     try{
               // img = ImageIO.read(new File("C:/Users/BaiGergi/Desktop/CarRedEnemy.png"));
           URL redEnemyURL = Main.class.getResource("/CarRedEnemy.png");
           img = ImageIO.read(redEnemyURL);
                }
                catch (IOException e) 
                    {
                        e.printStackTrace();
                    }
     System.out.println("ENEMY loadImage called");
     //System.out.println("Enemy car widh: " + img.getWidth());
     System.out.println("Enemy car height: " + img.getHeight());
     //System.out.println(img.getHeight());
     
    // x = Canvas.WIDTH/2;
    //y = 100 + img.getHeight()*2;
     
    //System.out.println("Enemy loaded");
     return img;
     
    }
    
    public void move()
    {
        if( y >= Game.HEIGHT)
        {
            farEnough();
             //y = rand.nextInt(1)- rand.nextInt(200);
            // randCoordX();
             //int a = rand.nextInt(-20);
        }
        else{
        this.y += Game.gameSpeed;
        }
    }
}


