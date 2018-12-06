package suppresstherebellion;

/**
 *
 * @author matti
 */
public class Levels {
    
    int level = 1;
    
    public int shots(){
        
        switch(this.level){
            
            case 1: return 45;
            case 2: return 40;
            case 3: return 35;
            case 4: return 31;
            case 5: return 29;
            case 6: return 25;
            case 7: return 22;
            case 8: return 19;
            case 9: return 17;
            case 10: return 15;
            default: return 1;
        }
    }
    
    public void levelUp(){
        ++level;
    }
    
    public int getLevel(){
        return level;
    }
    
    public void resetLevel(){
        this.level = 1;
    }
}
