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
public class Krul extends Hero implements Abilities{    
            //name, lvl, wpDmg, maxWpDmg, hp, maxHp, energy, maxEnergy, armor, maxArmor, shield, maxShield, atkSpd, maxAtkSpd
    
    
    // TODO: Actually calcualte the cd's according to the items

    
    public Krul(int level, int first, int second, int third){
        super("Krul", level, 68, 140, 643, 1501, 220, 506, 20, 75, 20, 75, 1.0, 1.36, 1, 12);  
        setDefaultCd(8, 10, 50);
        
    }
    public Krul(){
        super("Krul", 12, 68, 140, 643, 1501, 220, 506, 20, 75, 20, 75, 1.0, 1.36, 1, 12);    
        setDefaultCd(8, 10, 50);
    }
    
    @Override
    public boolean firstAbility(){
        if(onCd(1)) return false;
        System.out.println(getName() + ": \t" + "Dead Man's Rush");
        getTarget().setHealth(getTarget().getHealth()-calculateDamage(2, 215, 0));
        return true;
    }
    @Override
    public boolean secondAbility(){
        if(onCd(2)) return false;
        System.out.println(getName() + ": \t" + "Spectral Smite");
        getTarget().setHealth(getTarget().getHealth()-calculateDamage(2, 350, 0));
        return true;
    }
    @Override
    public boolean thirdAbility(){
        if(onCd(3)) return false;
        System.out.println(getName() + ": \t" + "From Hell's Heart");
        getTarget().setHealth(getTarget().getHealth()-calculateDamage(2, 650, 0));
        return true;
    }
    
}
