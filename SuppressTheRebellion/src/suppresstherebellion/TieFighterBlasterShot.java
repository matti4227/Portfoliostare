package suppresstherebellion;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author matti
 */
public class TieFighterBlasterShot {
    private Point position = new Point();
    private BufferedImage tieFighterBlasterShot;
    private boolean flying = true;
    
    public TieFighterBlasterShot(Point position){
        this.position.x = position.x;
        this.position.y = position.y;
        try {
            this.tieFighterBlasterShot = ImageIO.read(TieFighterBlasterShot.class.getResource("/suppresstherebellion/resources/pictures/GreenLaserShot.png"));
        } catch (IOException ex) {
            Logger.getLogger(TieFighterBlasterShot.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void fly(){
        this.position.x+=10;
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
    
    public Point getCurrentPosition(){
        return position;
    }
    
    public void draw(Graphics2D graphics2d){
        graphics2d.drawImage(this.tieFighterBlasterShot, this.position.x, this.position.y, (ImageObserver) null);
    }
}
