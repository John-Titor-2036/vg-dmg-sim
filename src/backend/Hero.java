package backend;

import java.util.ArrayList;
/*

Authors: Jerry Zhou, Degaulle Dai

 */

public class Hero implements Abilities
{
    private String name;

    private final int MAX_LEVEL = 11;
    private double health;
    private double fullHealth;
    private int energy;
    private int fullEnergy;
    private int weaponDmg;
    private double atkSpeed;
    private int armor;
    private int shield;
    private double defaultFcd;
    private double defaultScd;
    private double defaultUcd;
    private double firstCd;
    private double secondCd;
    private double ultCd;
    
    ArrayList<Buff> buffs = new ArrayList<Buff>();

    /**
     * constructor
     */
    public Hero(String name, int lvl,  int weaponDmg, int maxWeaponDmg, int health, int maxHealth, int energy, int maxEnergy, int armor, int maxArmor, int shield, int maxShield, double atkSpeed, double maxAtkSpeed, int healthRegen, int maxHealthRegen)
    {
        this.name = name;
        this.weaponDmg = weaponDmg + (maxWeaponDmg-weaponDmg)/MAX_LEVEL * lvl;
        this.health = health + (maxHealth-health)/MAX_LEVEL * lvl;
        this.energy = energy + (maxEnergy-energy)/MAX_LEVEL * lvl;
        this.armor = armor + (maxArmor-armor)/MAX_LEVEL * lvl;
        this.shield = shield + (maxShield-shield)/MAX_LEVEL * lvl;
        this.atkSpeed = atkSpeed + (maxAtkSpeed-atkSpeed)/MAX_LEVEL * lvl;
        fullEnergy = this.energy;
        fullHealth = this.health;
        firstCd = 0;
        secondCd = 0;
        ultCd = 0;
    }
    
    public void setDefaultCd(double fcd, double scd, double ucd){
        defaultFcd = fcd;
        defaultScd = scd;
        defaultUcd = ucd;
    }

    /**
     * gets stats
     */
    public String getName(){ return name; }
    public double getHealth(){ return health; }    
    public int getEnergy(){ return energy; }
    public int getWeaponDmg(){ return weaponDmg; }
    public int getArmor(){ return armor; }
    public int getShield(){ return shield; }
    public double getAtkSpeed(){ return atkSpeed; }
    public double getFullHealth(){return fullHealth; }
    /**
     * changes stats
     */
    public void setHealth(double health){this.health = health;}
    public void setEnergy(int energy){ this.energy = energy; }
    public void setArmor( int armor){ this.armor = armor; }
    public void setShield(int shield){ this.shield = shield; }
    public void setAtkSpeed(double atkSpd){ this.atkSpeed = atkSpd; }

    /** Type 1 = Weapon Damage
     *Type 2 = Crystal Damage
     *Type 3 = Crittable Weapon Damage
     *Type 4 = Basic Attack Weapon Component
     *Type anything else = True Damage
     */
    public double calculateDamage(int type, int damage, double pierce)
    {
        int damageTaken = 0;
        int critRng;
        int ratio = 1;
        int critChance =1;
        int critDmg = 1;
        if(type == 1)
        {
            damageTaken = (int) ( (damage*pierce*ratio)+((damage*ratio*(1-pierce))/(1+(0.01*(armor)))) );
        }
        else if (type == 2)
        {
            damageTaken =(int) ( (damage*pierce*ratio)+((damage*ratio*(1-pierce))/(1+(0.01*(shield)))) );
        }
        else if (type == 3)
        {
            critRng = (int)(Math.random()*1.01);
            if (critRng<= critChance)
                damageTaken =(int) ( (damage*critDmg*pierce*ratio) + ((damage*critDmg*(1-pierce))/(1+(0.01*(armor)))) );
            else
                damageTaken =(int) ( (damage*pierce*ratio) + ((damage*(1-pierce))/(1+(0.01*(armor)))) );
        }
        else if (type == 4)
        {
            critRng = (int)(Math.random()*1.01);
            if (critRng<= critChance)
                damageTaken =(int) ( ((weaponDmg+damage)*critDmg*pierce) + (((weaponDmg+damage)*critDmg*(1-pierce))/(1+(0.01*(armor)))) );
            else
                damageTaken =(int) ( ((weaponDmg+damage)*pierce) + (((weaponDmg+damage)*(1-pierce))/(1+(0.01*(armor)))) );
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
    

    public double firstAbility() {
        return 0;
    }
    public double secondAbility() {
        return 0;
    }
    public double ultimateAbility() {
        return 0;
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