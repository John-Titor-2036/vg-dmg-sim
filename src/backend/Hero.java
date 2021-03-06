package backend;

import java.util.ArrayList;
/*

Authors: Jerry Zhou, Degaulle Dai

 */

public class Hero implements Abilities
{
    private Hero target;
    
    private String name;

    private final int MAX_LEVEL = 11;
    private double health;
    private double fullHealth;
    private double energy;
    private double fullEnergy;
    private int weaponDmg;
    private double atkSpeed;
    private int armor;
    private int shield;
    private double defaultFcd;
    private double defaultScd;
    private double defaultUcd;
    private double firstCd;
    private double secondCd;
    private double thirdCd;
    private double firstEnergyCost;
    private double secondEnergyCost;
    private double thirdEnergyCost;
    private double[] firstAbilityScaling;
    private double[] secondAbilityScaling;
    private double[] thirdAbilityScaling;
    private double firstDamage;
    private double secondDamage;
    private double thirdDamage;
    
    ArrayList<Buff> buffs = new ArrayList<Buff>();

    /**
     * constructor
     */
    public Hero(String name, int lvl,  int weaponDmg, int maxWeaponDmg, int health, int maxHealth, int energy, int maxEnergy, int armor, int maxArmor, int shield, int maxShield, double atkSpeed, double maxAtkSpeed, int healthRegen, int maxHealthRegen)
    {
        this.name = name;
        this.weaponDmg = weaponDmg + (maxWeaponDmg-weaponDmg)/MAX_LEVEL * (lvl-1);
        this.health = health + (maxHealth-health)/MAX_LEVEL * (lvl-1);
        this.energy = energy + (maxEnergy-energy)/MAX_LEVEL * (lvl-1);
        this.armor = armor + (maxArmor-armor)/MAX_LEVEL * (lvl-1);
        this.shield = shield + (maxShield-shield)/MAX_LEVEL * (lvl-1);
        this.atkSpeed = atkSpeed + (maxAtkSpeed-atkSpeed)/MAX_LEVEL * (lvl-1);
        fullEnergy = this.energy;
        fullHealth = this.health;
        firstCd = 0;
        secondCd = 0;
        thirdCd = 0;
    }
    
    public void setDefaultCd(double fcd, double scd, double ucd){
        defaultFcd = fcd;
        defaultScd = scd;
        defaultUcd = ucd;
    }
    
    public void setEnergyCost(int first, int second, int third){
        firstEnergyCost = first;
        secondEnergyCost = second;
        thirdEnergyCost = third;
    }
    
    public void setAbilityScaling(double[] first, double[] second, double[] third){
        firstAbilityScaling = first;
        secondAbilityScaling = second;
        thirdAbilityScaling = third;
    }    
    public void setFirstDamage(int num){ firstDamage = firstAbilityScaling[num]; }
    public void setSecondDamage(int num){ secondDamage = secondAbilityScaling[num]; }
    public void setThirdDamage(int num){ thirdDamage = thirdAbilityScaling[num]; }
    
    /**
     * gets stats 
     */
    public Hero getTarget(){ return target; }
    public String getName(){ return name; }
    public double getHealth(){ return health; }    
    public double getEnergy(){ return energy; }
    public int getWeaponDmg(){ return weaponDmg; }
    public int getArmor(){ return armor; }
    public int getShield(){ return shield; }
    public double getAtkSpeed(){ return atkSpeed; }
    public double getFullHealth(){return fullHealth; }
    public double getFullEnergy(){return fullEnergy; }
    public double[] getEnergyCost(){ return new double[]{0, firstEnergyCost, secondEnergyCost, thirdEnergyCost}; } 
    public double getFirstCd(){return firstCd;}
    public double getSecondCd(){return secondCd;}
    public double getThirdCd(){return thirdCd;}
    public double[] getDefaultCd(){ return new double[]{0, defaultFcd, defaultScd, defaultUcd};}    
    public double getFirstDamage(){ return firstDamage; }
    public double getSecondDamage(){ return secondDamage; }
    public double getThirdDamage(){ return thirdDamage; }
    
    /**
     * changes stats
     */
    public void setTarget(Hero targ){ this.target = targ; }
    public void setHealth(double health){
        if(health <= 0) health = 0;
        this.health = health;
    }
    public void setEnergy(double energy){ 
        if(energy >= fullEnergy) energy = fullEnergy;
        this.energy = energy; 
    }
    public void setArmor( int armor){ this.armor = armor; }
    public void setShield(int shield){ this.shield = shield; }
    public void setAtkSpeed(double atkSpd){ this.atkSpeed = atkSpd; }
    public void setFirstCd(double fcd){ this.firstCd = fcd; }
    public void setSecondCd(double scd) {this.secondCd = scd; }
    public void setThirdCd(double ucd){ this.thirdCd = ucd; }
    /** Type 1 = Weapon Damage
     *Type 2 = Crystal Damage
     *Type 3 = Crittable Weapon Damage
     *Type 4 = Basic Attack Weapon Component
     *Type anything else = True Damage
     */
    public double calculateDamage(int type, double damage, double pierce)
    {
        double damageTaken = 0;
        int critRng;
        int ratio = 1;
        int critChance =1;
        int critDmg = 1;
        if(type == 1)
        {
            damageTaken = ( (damage*pierce*ratio)+((damage*ratio*(1-pierce))/(1+(0.01*(armor)))) );
        }
        else if (type == 2)
        {
            damageTaken = ( (damage*pierce*ratio)+((damage*ratio*(1-pierce))/(1+(0.01*(shield)))) );
        }
        else if (type == 3)
        {
            critRng = (int)(Math.random()*1.01);
            if (critRng<= critChance)
                damageTaken = ( (damage*critDmg*pierce*ratio) + ((damage*critDmg*(1-pierce))/(1+(0.01*(armor)))) );
            else
                damageTaken = ( (damage*pierce*ratio) + ((damage*(1-pierce))/(1+(0.01*(armor)))) );
        }
        else if (type == 4)
        {
            critRng = (int)(Math.random()*1.01);
            if (critRng<= critChance)
                damageTaken =  ( ((weaponDmg+damage)*critDmg*pierce) + (((weaponDmg+damage)*critDmg*(1-pierce))/(1+(0.01*(armor)))) );
            else
                damageTaken = ( ((weaponDmg+damage)*pierce) + (((weaponDmg+damage)*(1-pierce))/(1+(0.01*(armor)))) );
        }
        else
        {
            damageTaken = damage;
        }
        return damageTaken;
    }
    
    
    //Buffs functions
    public void applyBuff(String type, double effect, int time){
        buffs.add(new Buff(this, type, effect, time));
    }    
    public void manageBuffs(){
        for(int i=0; i<buffs.size(); i++){
            Buff buff = buffs.get(i);
            if(buff.getTime() <= 0) buffs.remove(i);
            else buff.tick();
        }
    }
    

    public boolean firstAbility() { return false; }
    public boolean secondAbility() { return false; }
    public boolean thirdAbility() { return false; }
    
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
            if(thirdCd == 0){
                thirdCd = defaultUcd;
                return true;
            }
            else thirdCd--; 
            return false;
        }
        return false;
    }
    
    public boolean hasEnergy(int ability){
        if(ability == 1) return (energy - firstEnergyCost >= 0);
        if(ability == 2) return (energy - secondEnergyCost >= 0);
        if(ability == 3) return (energy - thirdEnergyCost >= 0);
        return false;
    }
    
    public double basicAttack(double dmg){
        return calculateDamage(1, dmg, 0);
    }
} 