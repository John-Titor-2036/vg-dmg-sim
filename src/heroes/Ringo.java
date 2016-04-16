/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package heroes;

import backend.Abilities;
import backend.Hero;

/**
 *
 * @author Jerry
 */
public class Ringo extends Hero implements Abilities{
        //name, lvl, wpDmg, maxWpDmg, hp, maxHp, energy, maxEnergy, armor, maxArmor, shield, maxShield, atkSpd, maxAtkSpd
    
    
    // TODO: Actually calcualte the cd's according to the items
    private final int defaultFcd = 7;
    private final int defaultScd = 10;
    private final int defaultUcd = 90;
    private int firstCd = 0;
    private int secondCd = 0;
    private int ultCd = 0;
    
    public Ringo(int level){
        super("Ringo", level, 66, 131, 646, 1405, 180, 422, 20, 86, 20, 86, 1.0, 1.36, 1, 12);     
        
    }
    public Ringo(){
        super("Ringo", 12, 66, 131, 646, 1405, 180, 422, 20, 86, 20, 86, 1.0, 1.36, 1, 12);    
    }
    
    @Override
    public int firstAbility(){
        System.out.println("ANKLESHOT !!!!");
        return calculateDamage(2, 215, 0);
    }
    @Override
    public int secondAbility(){
        System.out.println("SPEEDY MOVES !!!");
        applyBuff("atkSpeed", 0.9, 6);
        return 0;
    }
    @Override
    public int ultimateAbility(){
        System.out.println("HELLFIRE BREW!!!");
        return calculateDamage(2, 480, 100);
    }
    
    public boolean onCd(int ability){
        if(ability == 1){
            if(firstCd == 0){
                firstCd = defaultFcd;
                return true;
            }
            else firstCd--; return false;       
        }
        if(ability == 2){
            if(secondCd == 0){
                secondCd = defaultScd;
                return true;
            }
            else secondCd--; return false;    
        }
        if(ability == 3){
            if(ultCd == 0){
                ultCd = defaultUcd;
                return true;
            }
            else ultCd--; return false;            
        }
        return false;
    }
}
