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
    
    public Ringo(int level, int first, int second, int third){
        super("Ringo", level, 66, 131, 646, 1405, 180, 422, 20, 86, 20, 86, 1.0, 1.36, 1, 12);
        setDefaultCd(7, 10, 90);
        setEnergyCost(90, 90, 130);
        setAbilityScaling(new double[]{1.25, 80, 125, 170, 215, 350}, new double[]{.8, 0, 0, 0, 0, 0}, new double[]{.75, 250, 365, 480});
    }
    public Ringo(){
        super("Ringo", 12, 66, 131, 646, 1405, 180, 422, 20, 86, 20, 86, 1.0, 1.36, 1, 12);    
        setDefaultCd(7, 10, 90);
        setEnergyCost(90, 90, 130);
    }
    
    @Override
    public boolean firstAbility(){
        if(onCd(1)) System.out.print(onCd(1));
        System.out.println(getName() + ": \t" + "Ankleshot");
        getTarget().setHealth(getTarget().getHealth()- calculateDamage(2, getFirstDamage(), 0));
        return true;
    }
    @Override
    public boolean secondAbility(){
        if(onCd(2)) return false;
        System.out.println(getName() + ": \t" + "Twirling Silver");
        applyBuff("atkSpeed", 0.9, 6);
        return true;
    }
    @Override
    public boolean thirdAbility(){
        if(onCd(3)) return false;
        System.out.println(getName() + ": \t" + "Hellfire Brew");
        getTarget().setHealth(getTarget().getHealth()-calculateDamage(2, getThirdDamage(), 0));
        return true;
    }
    
}
