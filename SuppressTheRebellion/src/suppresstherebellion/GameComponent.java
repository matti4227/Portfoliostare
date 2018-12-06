package suppresstherebellion;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @author matti
 */
public class GameComponent extends JComponent implements ActionListener, KeyListener{
    
    private BufferedImage background;
    private TieFighter tieFighter;
    private Xwing xwing;
    ArrayList<XwingBlasterShot> xwingBlasterShots = new ArrayList<XwingBlasterShot>();
    ArrayList<TieFighterBlasterShot> tieFighterBlasterShots = new ArrayList<TieFighterBlasterShot>();
    ArrayList<Heart> hearts = new ArrayList<Heart>();
    private Point heartPosition;
    private Levels level = new Levels();
    private Dimension spaceSize = new Dimension(600, 400);
    private Timer timer = new Timer(40, this);
    JPanel jPanel;
    private int counter = 0;
    private int add = 0;
    private int lastHeart = 9;
    boolean gameEnded = false;
    private boolean won = false;
    boolean lost = false;
    private int xwingFlag = 0;
    private int tieFighterFlag = 0;
    private int fireRatio = 17;
    private MediaPlayer gameMusic;
    private MediaPlayer menuMusic;
    private AudioClip tieFighterLaserShotSound;
    private AudioClip xwingLaserShotSound;
    
    public GameComponent(JPanel jPanel){
        addKeyListener(this);
        this.jPanel = jPanel;
        setBounds(5, 0, jPanel.getWidth(), jPanel.getHeight());
        try {
            this.background = ImageIO.read(SuppressTheRebellion.class.getResource("/suppresstherebellion/resources/pictures/Space.jpg"));
        } catch (IOException ex) {
            Logger.getLogger(GameComponent.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.tieFighter = new TieFighter(this.spaceSize);
        this.xwing = new Xwing(this.spaceSize);
        for(int i = 0; i < 10; ++i){
            heartPosition = new Point(350 + add, 290);
            hearts.add(new Heart(heartPosition));
            add+=20;
        }
        
        URL gm = SuppressTheRebellion.class.getResource("/suppresstherebellion/resources/music/GameMusic.mp3");
        gameMusic = new MediaPlayer(new Media(gm.toString()));
        gameMusic.setOnEndOfMedia(new Runnable() {
            public void run() {
                gameMusic.seek(Duration.ZERO);
            }
        });
        gameMusic.setVolume(0.05);
        URL mm = SuppressTheRebellion.class.getResource("/suppresstherebellion/resources/music/MenuMusic.mp3");
        menuMusic = new MediaPlayer(new Media(mm.toString()));
        menuMusic.setOnEndOfMedia(new Runnable() {
            public void run() {
                menuMusic.seek(Duration.ZERO);
            }
        });
        gameMusic.setVolume(0.05);
        
        URL tieFighterSound = SuppressTheRebellion.class.getResource("/suppresstherebellion/resources/music/TieFighterLaserShotSound.wav");
        tieFighterLaserShotSound = new AudioClip(tieFighterSound.toString());
        URL xwingSound = SuppressTheRebellion.class.getResource("/suppresstherebellion/resources/music/X-wingLaserShotSound.wav");
        xwingLaserShotSound = new AudioClip(xwingSound.toString());
        
    }
    
    public void gameStart(){
        timer.start();
    }
    
    public void gamePause() {
        timer.stop();
    }
    
    public void gameUnPause() {
        timer.start();
    }
    
    public void gameRestart(){
        timer.stop();
        tieFighter.setPositionDefault();
        xwing.setPositionDefault();
        xwingBlasterShots.clear();
        tieFighterBlasterShots.clear();
        hearts.clear();
        add = 0;
        for(int i = 0; i < 10; ++i){
            heartPosition = new Point(350 + add, 290);
            hearts.add(new Heart(heartPosition));
            add+=20;
        }
        lastHeart = 9;
        level.resetLevel();
        counter = 0;
        xwingFlag = 0;
        tieFighterFlag = 0;
        fireRatio = 17;
        xwing.beAlive();
        tieFighter.beAlive();
        won = false;
        lost = false;
        gameEnded = false;
    }
    
    public void endGame(String end){
        if("Youwon".equals(end)){
            gameEnded = true;
            won = true;
        }
        if("Youlost".equals(end)){
            gameEnded = true;
            lost = true;
        }
    }
    public void playGameMusic(){
        gameMusic.play();
    }
    
    public void stopGameMusic(){
        gameMusic.stop();
    }
    
    public void pauseGameMusic(){
        gameMusic.pause();
    }
    
    public void playMenuMusic(){
        menuMusic.play();
    }
    
    public void stopMenuMusic(){
        menuMusic.stop();
    }
    
    public void playTieFighterLaserShotSound(){
        tieFighterLaserShotSound.play(0.05);
    }
    
    public void playxwingLaserShotSound(){
        xwingLaserShotSound.play(0.1);
    }

    @Override
    public void paintComponent(Graphics graphics){
        Graphics2D graphics2d = (Graphics2D) graphics;
        graphics2d.drawImage(this.background, 0, 0, this.spaceSize.width, this.spaceSize.height-50, (ImageObserver) null);
        if(xwing!=null)
            xwing.draw(graphics2d);
        if(tieFighter!=null)
            tieFighter.draw(graphics2d);
        for(XwingBlasterShot xShot : xwingBlasterShots){
            if(xShot!=null && xShot.isFlying())
            xShot.draw(graphics2d);
        }
        for(TieFighterBlasterShot tieShot : tieFighterBlasterShots){
            if(tieShot!=null && tieShot.isFlying())
            tieShot.draw(graphics2d);
        }
        if(lost==false){
            for(Heart h : hearts){
                if(hearts!=null)
                    h.draw(graphics2d);
            }
        }
        if(level.getLevel()<=10 && lost == false){
            graphics2d.setColor(Color.blue);
            graphics2d.fillRect(240, 285, 100, 30);
            graphics2d.setColor(Color.red);
            graphics2d.setFont(new Font("Arial", Font.BOLD, 18));
            graphics2d.drawString("Level : " + level.getLevel(), 253, 305);
        }
        if(won == true){
            graphics2d.setColor(Color.yellow);
            graphics2d.setFont(new Font("Arial", Font.BOLD, 70));
            graphics2d.drawString("You Won! :D", 90, 200);
            timer.stop();
        }
        if(lost == true){
            graphics2d.setColor(Color.yellow);
            graphics2d.setFont(new Font("Arial", Font.BOLD, 70));
            graphics2d.drawString("You Lost! :(", 90, 200);
            timer.stop();
        } 
    }
    
    public void flyUp(){
        tieFighter.flyUp();
    }
    
    public void flyDown(){
        tieFighter.flyDown();
    }
    
    public void shoot(String fighter){
        if(fighter == "X-Wing"){
            xwingBlasterShots.add(new XwingBlasterShot(this.xwing.getCurrentPosition()));
            this.playTieFighterLaserShotSound();
        }
        if(fighter == "TieFighter"){
            tieFighterBlasterShots.add(new TieFighterBlasterShot(this.tieFighter.getCurrentPosition()));
            this.playxwingLaserShotSound();
        }
    }
    
    private double pitagoras(Point point1, Point point2){
        return Math.sqrt(Math.pow(point1.x+3-point2.x+3, 2)+Math.pow(point1.y-point2.y, 2));
    }
    
    private void removeXwingShots(){
        while(xwingFlag > 0){
            for(XwingBlasterShot xShot : xwingBlasterShots){
                if(xShot.isFlying()==false){
                    xwingBlasterShots.remove(xShot);
                    --xwingFlag;
                    break;
                }
            }
        }
    }
    
    private void removeTieFighterShots(){
        while(tieFighterFlag > 0){
            for(TieFighterBlasterShot xShot : tieFighterBlasterShots){
                if(xShot.isFlying()==false){
                    tieFighterBlasterShots.remove(xShot);
                    --tieFighterFlag;
                    break;
                }   
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        for(XwingBlasterShot xShot : xwingBlasterShots){
            if(xShot.isFlying()==false){
                ++xwingFlag;
            }else if(pitagoras(this.tieFighter.getCurrentPosition(),xShot.getCurrentPosition())<26){
                xShot.setNotFlying();
                tieFighter.beDead();
            }else if(xShot.getCurrentPosition().x<0){
                xShot.setNotFlying();
            }else{
                xShot.fly();
            }
        }
        if(xwingFlag > 0){
            removeXwingShots();
            xwingFlag = 0;
        }
        
        for(TieFighterBlasterShot tieShot : tieFighterBlasterShots){
            if(tieShot.isFlying()==false){
                ++tieFighterFlag;
            }else if(pitagoras(this.xwing.getCurrentPosition(),tieShot.getCurrentPosition())<26){
                tieShot.setNotFlying();
                xwing.isHit();
                hearts.remove(lastHeart);
                --lastHeart;
                if(xwing.getLife() == 0)
                    xwing.beDead();
                level.levelUp();
            }else if(tieShot.getCurrentPosition().x>this.getWidth()){
                tieShot.setNotFlying();
            }else{
                tieShot.fly();
            }
        }
        if(tieFighterFlag > 0){
            removeTieFighterShots();
            tieFighterFlag = 0;
        }
        
        if(xwing.isDead())
            endGame("Youwon");
        if(tieFighter.isDead())
            endGame("Youlost");
        
        if(xwing.isDead() == false)
            xwing.fly();
        if(counter % level.shots() == 0)
            shoot("X-Wing");
        
        ++counter;
        --fireRatio;
        this.repaint();
    }

    @Override
    public void keyTyped(KeyEvent ke) {
        if(ke.getKeyChar()=='8' || ke.getKeyChar()=='w'){
            if(tieFighter.getCurrentPosition().y>20)
                flyDown();
        }
        if(ke.getKeyChar()=='5' || ke.getKeyChar()=='s'){
            if(tieFighter.getCurrentPosition().y<250)
                flyUp();
        }
        if(ke.getKeyChar()==' '){
            if(fireRatio <= 0){
                shoot("TieFighter");
                fireRatio = 17;
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent ke) {
    }
    
    @Override
    public void keyReleased(KeyEvent ke) {
    }
}
