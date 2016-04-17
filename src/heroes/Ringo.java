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
    
    public Ringo(int level){
        super("Ringo", level, 66, 131, 646, 1405, 180, 422, 20, 86, 20, 86, 1.0, 1.36, 1, 12);
        setDefaultCd(7, 10, 90);
        
    }
    public Ringo(){
        super("Ringo", 12, 66, 131, 646, 1405, 180, 422, 20, 86, 20, 86, 1.0, 1.36, 1, 12);    
        setDefaultCd(7, 10, 90);
    }
    
    @Override
    public double firstAbility(){
        System.out.println(getName() + ": \t" + "Ankleshot");
        return calculateDamage(2, 215, 0);
    }
    @Override
    public double secondAbility(){
        System.out.println(getName() + ": \t" + "Twirling Silver");
        applyBuff("atkSpeed", 0.9, 6);
        return 0;
    }
    @Override
    public double ultimateAbility(){
        System.out.println(getName() + ": \t" + "Hellfire Brew");
        return calculateDamage(2, 480, 0);
    }
    
}
