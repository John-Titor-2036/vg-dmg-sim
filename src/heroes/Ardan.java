/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package heroes;

import backend.Hero;

/**
 *
 * @author Jerry
 */
public class Ardan extends Hero{

        //name, lvl, wpDmg, maxWpDmg, hp, maxHp, energy, maxE nergy, armor, maxArmor, shield, maxShield, atkSpd, maxAtkSpd
    public Ardan(int level, int first, int second, int third){
        super("Ardan", level, 80, 154, 801, 1615, 100, 100, 30, 96, 20, 86, 1.0, 1.36, 1, 12);
        setDefaultCd(13, 20, 90);
        setEnergyCost(0, 100, 0);
    }
    public Ardan(){
        super("Ardan", 12, 66, 131, 646, 1405, 180, 422, 20, 86, 20, 86, 1.0, 1.36, 1, 12);    
        setDefaultCd(13, 20, 90);
    }
    
    public double basicAttack(double damage){
        setEnergy(getEnergy() + 15);
        if(onCd(2)) setSecondCd(getSecondCd()-3);
        return calculateDamage(1, damage, 0);
    }
    
    public boolean firstAbility(){
        if(onCd(1)) return false;
        System.out.println(getName() + ": \t" + "Vanguard");
        getTarget().setHealth(getTarget().getHealth()-calculateDamage(2, 250, 0));
        return true;
    }
    public boolean secondAbility(){
        System.out.println(getName() + ": \t" + "Blood for Blood");
        getTarget().setHealth(getTarget().getHealth()-calculateDamage(1, getWeaponDmg()+190, 0));
        return true;
    }
    public boolean thirdAbility(){
        if(onCd(3)) return false;
        System.out.println(getName() + ": \t" + "Gauntlet");
        getTarget().setHealth(getTarget().getHealth()- calculateDamage(2, 50, 0));
        return true;
    }
    
    
}
