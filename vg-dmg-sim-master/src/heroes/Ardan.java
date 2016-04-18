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
    public Ardan(int level){
        super("Ardan", level, 66, 131, 646, 1405, 180, 422, 20, 86, 20, 86, 1.0, 1.36, 1, 12);
        setDefaultCd(13, 20/getAtkSpeed(), 90);
    }
    public Ardan(){
        super("Ardan", 12, 66, 131, 646, 1405, 180, 422, 20, 86, 20, 86, 1.0, 1.36, 1, 12);    
        setDefaultCd(13, 20/getAtkSpeed(), 90);
    }
    
    public double firstAbility(){
        System.out.println(getName() + ": \t" + "Vanguard");
        return calculateDamage(2, 250, 0);
    }
    public double secondAbility(){
        System.out.println(getName() + ": \t" + "Blood for Blood");
        return calculateDamage(1, getWeaponDmg()+190, 0);
    }
    public double ultimateAbility(){
        System.out.println(getName() + ": \t" + "Gauntlet");
        return calculateDamage(2, 50, 0);
    }
}
