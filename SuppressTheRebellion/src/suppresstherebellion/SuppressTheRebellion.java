package suppresstherebellion;

import javax.swing.JFrame;

/**
 *
 * @author matti
 */
public class SuppressTheRebellion extends JFrame {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Game game = new Game();
        game.setVisible(true);
        game.pack();
        game.setResizable(false);
    }
    
}
