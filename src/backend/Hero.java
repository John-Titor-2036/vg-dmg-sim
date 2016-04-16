package backend;

import java.util.ArrayList;
/*

Authors: Jerry Zhou, Degaulle Dai

 */

public class Hero implements Abilities
{
    private String name;

    private final int MAX_LEVEL = 11;
    private int health;
    private int fullHealth;
    private int energy;
    private int fullEnergy;
    private int weaponDmg;
    private double atkSpeed;
    private int armor;
    private int shield;

    ArrayList<Buff> buffs = new ArrayList<Buff>();

    /**
     * constructor
     */
    public Hero(String name, int lvl,  int weaponDmg, int maxWeaponDmg, int health, int maxHealth, int energy, int maxEnergy, int armor, int maxArmor, int shield, int maxShield, double atkSpeed, double maxAtkSpeed, int healthRegen, int maxHealthRegen)
    {
        this.name = name;
        this.weaponDmg = (maxWeaponDmg-weaponDmg)/MAX_LEVEL * lvl;
        this.health = (maxHealth-health)/MAX_LEVEL * lvl;
        this.energy = (maxEnergy-energy)/MAX_LEVEL * lvl;
        this.armor = (maxArmor-armor)/MAX_LEVEL * lvl;
        this.shield = (maxShield-shield)/MAX_LEVEL * lvl;
        this.atkSpeed = (maxAtkSpeed-atkSpeed)/MAX_LEVEL * lvl;
        fullEnergy = this.energy;
        fullHealth = this.health;
    }

    /**
     * gets stats
     */
    public String getName(){ return name; }
    public int getHealth(){ return health; }    
    public int getEnergy(){ return energy; }
    public int getWeaponDmg(){ return weaponDmg; }
    public int getArmor(){ return armor; }
    public int getShield(){ return shield; }
    public double getAtkSpeed(){ return atkSpeed; }

    /**
     * changes stats
     */
    public void setHealth(int health){this.fullHealth = health;}
    public void setEnergy(int energy){ this.energy = energy; }
    public void setArmor(int armor){ this.armor = armor; }
    public void setShield(int shield){ this.shield = shield; }
    public void setAtkSpeed(double atkSpd){ this.atkSpeed = atkSpd; }

    /** Type 1 = Weapon Damage
     *Type 2 = Crystal Damage
     *Type 3 = Crittable Weapon Damage
     *Type 4 = Basic Attack Weapon Component
     *Type anything else = True Damage
     */
    public int calculateDamage(int type, int damage, double pierce)
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
    

    public int firstAbility() {
        return 0;
    }
    public int secondAbility() {
        return 0;
    }
    public int ultimateAbility() {
        return 0;
    }
    public boolean onCd(int ability) {
        return false;
    }
} 