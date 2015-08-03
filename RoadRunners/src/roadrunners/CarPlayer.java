package roadrunners;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Random;
import javax.imageio.ImageIO;
import static roadrunners.CarEnemy.enemies;
import sun.tools.jar.Main;

public class CarPlayer {
    private BufferedImage img;
    private int x;
    private int y;
    public static int carW = 26;
    public static int carH = 49;
    //CarPlayer player = new CarPlayer();
    private int distTravelled = 0;
    public boolean isAlive = true;
    
    private int movedDist = 0;
    
    public void calcMovedDistance()
    {
        if(isAlive)
        {
             distTravelled++;
            
        }
        else
        {
            Game.updateTimer.stop();
        }
    }
    
    public int getMovedDistance()
    {
        return distTravelled;
    }
    
    public void isCollided()
    {
       // boolean flag = false;
        Rectangle playerRect = new Rectangle(this.getX()+1
                , this.getY()+1
                , carW-1
                , carH-1);
                
        for( int i = 0; i < enemies.size(); i++)
            {
                CarEnemy tempCar = enemies.get(i);
                Rectangle intercept = new Rectangle(tempCar.getX()+1
                , tempCar.getY()+1
                , carW-1
                , carH-1);
                
                if( playerRect.intersects(intercept))
                {
                    System.out.println("Player crashed in car: "+i);
                    //flag = true;
            //        isFarEnough = false;
                    isAlive = false;
                }
            }
        //return flag;
    }
    public CarPlayer getPlayer()
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
          
    public BufferedImage loadImage()
    {
        URL playerURL = Main.class.getResource("/CarPlayer.png");
        
     try{
                //img = ImageIO.read(new File("C:/Users/BaiGergi/Desktop/CarPlayer.png"));
                img = ImageIO.read(playerURL);
                }
                catch (IOException e) 
                    {
                        e.printStackTrace();
                    }
     System.out.println("PLAYER loadImage called");
     //System.out.println(img.getHeight());
     x = Canvas.WIDTH/2;
     y = Canvas.HEIGHT - img.getHeight()*2;
     
     return img;
     
    }
    
}
