package suppresstherebellion;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
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
public class TieFighter {
    private Point position = new Point();
    private BufferedImage tieFighter;
    private BufferedImage tempTieFighter;
    private boolean dead = false;
    private Point resetPosition = new Point();
    
    public TieFighter(Dimension size){
        this.position.x = size.width/30;
        this.position.y = size.height*2/5;
        this.resetPosition.x = size.width/30;
        this.resetPosition.y = size.height*2/5;
        try {
            this.tempTieFighter = ImageIO.read(TieFighter.class.getResource("/suppresstherebellion/resources/pictures/TieFighter.png"));
            AffineTransform at = new AffineTransform();
            at.scale(0.22, 0.22);
            tieFighter = new BufferedImage(tempTieFighter.getWidth(), tempTieFighter.getHeight(), BufferedImage.TYPE_INT_ARGB);
            AffineTransformOp ato = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
            tieFighter = ato.filter(tempTieFighter, tieFighter);
        } catch (IOException ex) {
            Logger.getLogger(TieFighter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void beDead(){
        this.dead = true;
    }
    
    public void beAlive(){
        this.dead = false;
    }
    
    public boolean isDead(){
        return dead;
    }
    
    public Point getCurrentPosition(){
        return position;
    }
    
    public void setPositionDefault(){
        this.position.x = this.resetPosition.x;
        this.position.y = this.resetPosition.y;
    }
    
    public void flyUp(){
        this.position.y+=10;
    }
    
    public void flyDown(){
        this.position.y-=10;
    }
    
    public void draw(Graphics2D graphics2d){
        graphics2d.drawImage(this.tieFighter, this.position.x, this.position.y-21, (ImageObserver) null);
    }
}
