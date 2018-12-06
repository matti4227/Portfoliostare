package suppresstherebellion;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.IOException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author matti
 */
public class Xwing {
    private Point position = new Point();
    private BufferedImage xwing;
    private BufferedImage tempXwing;
    private int targetPoint;
    private boolean dead = false;
    private boolean flying;
    private final Dimension size;
    private Point resetPosition = new Point();
    private int life = 10;
    
    public Xwing(Dimension size){
        this.position.x = size.width-size.width/5;
        this.position.y = size.height*2/5;
        this.resetPosition.x = size.width-size.width/5;
        this.resetPosition.y = size.height*2/5;
        this.targetPoint = size.height*2/5;
        this.size = size;
        try {
            this.tempXwing = ImageIO.read(Xwing.class.getResource("/suppresstherebellion/resources/pictures/X-wing.png"));
            AffineTransform at = new AffineTransform();
            at.scale(0.035, 0.035);
            xwing = new BufferedImage(tempXwing.getWidth(), tempXwing.getHeight(), BufferedImage.TYPE_INT_ARGB);
            AffineTransformOp ato = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
            this.xwing = ato.filter(tempXwing, xwing);
        } catch (IOException ex) {
            Logger.getLogger(Xwing.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void whereToFly(){
        Random random = new Random();
        this.targetPoint = (random.nextInt(this.size.height-130) + 10)/10*10;
    }
    
    public void fly(){
        if(this.position.y<this.targetPoint)
            this.position.y+=5;
        if(this.position.y>this.targetPoint)
            this.position.y-=5;
        if(this.position.y==this.targetPoint)
            this.whereToFly();
    }
    
    public boolean isFlying(){
        return flying;
    }
    
    public void setFlying(){
        this.flying = true;
    }
    
    public void setNotFlying(){
        this.flying = false;
    }
    
    public void beDead(){
        this.dead = true;
    }
    
    public void beAlive(){
        this.dead = false;
        this.life = 10;
    }
    
    public boolean isDead(){
        return dead;
    }
    
    public void isHit(){
        --life;
    }
    
    public int getLife(){
        return life;
    }
 
    public Point getCurrentPosition(){
        return position;
    }
    
    public void setPositionDefault(){
        this.position.x = this.resetPosition.x;
        this.position.y = this.resetPosition.y;
    }
    
    public void draw(Graphics2D graphics2d){
        graphics2d.drawImage(this.xwing, this.position.x, this.position.y-21, (ImageObserver) null);
    }
}
