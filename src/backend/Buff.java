package backend;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Jerry
 */
public class Buff {    
    
    private Hero target;
    private String type;
    private double effect;
    private int time;
    
    public Buff(Hero target, String type, double effect, int time){
        this.target = target;
        this.type = type;
        this.effect = effect;
        this.time = time;
        
        if(target != null){
            apply();
        }
    }
    
    public void apply(){
        switch (type.toLowerCase()){
            case "health": target.setHealth((int)(target.getHealth()+effect));
                            break;
            case "armor": target.setArmor((int)(target.getArmor()+effect));
                           break;
            case "shield": target.setShield((int)(target.getShield()+effect));
                            break;
            case "atkSpeed": target.setAtkSpeed((int)(target.getAtkSpeed()+effect));
                              break;
            default: break;
        }
    }
    
    public void tick(){
        time--;
    }
    
    public int getTime(){
        return time;
    }
    
}
