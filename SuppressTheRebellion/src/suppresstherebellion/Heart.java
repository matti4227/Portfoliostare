package suppresstherebellion;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author matti
 */
public class Heart {

    private BufferedImage heart;
    private Point heartPosition = new Point();
    private int heartFrame;
    
    public Heart(Point position){
        this.heartPosition = position;
        try {
            heart = ImageIO.read(Heart.class.getResource("/suppresstherebellion/resources/pictures/Heart.jpg"));
        } catch (IOException ex) {
            Logger.getLogger(Heart.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private BufferedImage getHeartFrame() {
        BufferedImage frame = new BufferedImage(19, 19, 2);
        Graphics2D graphics2D = (Graphics2D) frame.getGraphics();
        graphics2D.drawImage(heart.getSubimage(chosenFrame() * 19, 0, 19, 20), null, null);
        return frame;
    }
    
    public int chosenFrame() {
        if (heartFrame == 29) {
            heartFrame = 0;
        } else {
            heartFrame++;
        }
        return heartFrame;
    }
    
    public void draw(Graphics2D g) {
        g.drawImage(getHeartFrame(), heartPosition.x, heartPosition.y, null);
    }
}
