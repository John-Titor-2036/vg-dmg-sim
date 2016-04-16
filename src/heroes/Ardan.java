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
        //name, lvl, wpDmg, maxWpDmg, hp, maxHp, energy, maxEnergy, armor, maxArmor, shield, maxShield, atkSpd, maxAtkSpd
    public Ardan(int level){
        super("Ardan", level, 66, 131, 646, 1405, 180, 422, 20, 86, 20, 86, 1.0, 1.36, 1, 12);    
    }
    public Ardan(){
        super("Ardan", 12, 66, 131, 646, 1405, 180, 422, 20, 86, 20, 86, 1.0, 1.36, 1, 12);    
    }
    
    public int firstAbility(){
        return calculateDamage(2, 215, 0);
    }
    public int secondAbility(){
        //applyBuff("atkSpeed", .9);
        return 0;
    }
    public int ultimateAbility(){
        return calculateDamage(2, 480, 100);
    }
}
