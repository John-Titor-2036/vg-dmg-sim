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
    
    private final int defaultFcd = 13;
    private final double defaultScd = 20/getAtkSpeed();
    private final int defaultUcd = 90;
    private int firstCd = 0;
    private double secondCd = 0;
    private int ultCd = 0;
        //name, lvl, wpDmg, maxWpDmg, hp, maxHp, energy, maxEnergy, armor, maxArmor, shield, maxShield, atkSpd, maxAtkSpd
    public Ardan(int level){
        super("Ardan", level, 66, 131, 646, 1405, 180, 422, 20, 86, 20, 86, 1.0, 1.36, 1, 12);    
    }
    public Ardan(){
        super("Ardan", 12, 66, 131, 646, 1405, 180, 422, 20, 86, 20, 86, 1.0, 1.36, 1, 12);    
    }
    
    public int firstAbility(){
        System.out.println("Ardan \t" + "Vanguard");
        return calculateDamage(2, 215, 0);
    }
    public int secondAbility(){
        System.out.println("Ardan \t" + "Fisted in the ass");
        //applyBuff("atkSpeed", .9);
        return 0;
    }
    public int ultimateAbility(){
        System.out.println("Ardan \t" + "Useless AF gauntlet");
        return calculateDamage(2, 480, 100);
    }
    public boolean onCd(int ability){
        if(ability == 1){
            if(firstCd == 0){
                firstCd = defaultFcd;
                return true;
            }
            else firstCd--;
            return false;
        }
        if(ability == 2){
            if(secondCd == 0){
                secondCd = defaultScd;
                return true;
            }
            else secondCd--;   
            return false;
        }
        if(ability == 3){
            if(ultCd == 0){
                ultCd = defaultUcd;
                return true;
            }
            else ultCd--;    
            return false;
        }
        return false;
    }
}
