package backend;

import java.util.ArrayList;

/*

Authors: Jerry Zhou, Degaulle Dai

 */
public class Hero implements Abilities {
 
    private final int MAX_LEVEL = 12;
    
    //variables initialized in constructor
    private String name;
    private double health;
    private double fullHealth;
    private double healthRegen; //new
    private double energy;
    private double fullEnergy;
    private double energyRegen; //new
    private double baseWeaponPower; //should stay the same!
    private double atkSpeed;
    private double armor;
    private double shield;
    //variables that can be changed but have defaults.
    private double critDamage = 1.5; //default crit multiplier.
    private double critChance = 0.00; //default crit chance = 0%.
    private double weaponPower = 0; // added weapon power.
    private double crystalPower = 0; //heroes don't start with any crysal power, hence initialized at 0.
    //special variables
    private final int MAX_BONESAWSTACKS = 7;
    private final double PER_BONESAWSTACK = 0.07;
    private int bonesawStacks = 0; //if enemy has a bonesaw, this will keep track of each stack.
    private final int MAX_BROKENSTACKS = 5;
    private final double PER_BROKENSTACK = 0.07;
    private int brokenStacks = 0; //will be 0 all the time if the hero has not a broken myth.
    private double fortifiedHealth = 0; //fortified health is not set in the constructor, default is 0.
    private double healthBarrier = 0; //from effects such as alpha ult and reflex block. default is 0.

    private final int MAX_BPSTACKS = 25; //breaking point conditionals
    private int bpStacks = 0;
    private int bpDamage = 0;   
    private int weaponPowerCounter = 0; // used for bp stack counting
    
    private boolean isImmune = false; //reflex block
    private boolean isSlowed = false; //shiversteel or frostburn effects, increase skillshot hit percentage by 20%
    private boolean isDead = false;
    
    ArrayList<Buff> buffs = new ArrayList<Buff>();

    /**
     * constructor
     */
    public Hero(String name, int lvl, double health, double maxHealth, double healthRegen, double maxHealthRegen, 
                                      double energy, double maxEnergy, double energyRegen, double maxEnergyRegen, 
                                      double weaponPower, double maxWeaponPower, double atkSpeed, double maxAtkSpeed, 
                                      double armor, double maxArmor, double shield, double maxShield) {
        this.name = name;

        this.health = health + (maxHealth - health) / (MAX_LEVEL - 1) * lvl;
        fullHealth = this.health;
        this.healthRegen = healthRegen + (maxHealthRegen - healthRegen) / (MAX_LEVEL - 1) * lvl;
        this.energy = energy + (maxEnergy - energy) / MAX_LEVEL * lvl;
        fullEnergy = this.energy;
        this.energyRegen = energyRegen + (maxEnergyRegen - energyRegen) / (MAX_LEVEL - 1) * lvl;
        this.weaponPower = weaponPower + (maxWeaponPower - weaponPower) / (MAX_LEVEL - 1) * lvl;
        this.atkSpeed = atkSpeed + (maxAtkSpeed - atkSpeed) / (MAX_LEVEL - 1) * lvl;
        this.armor = armor + (maxArmor - armor) / (MAX_LEVEL - 1) * lvl;
        this.shield = shield + (maxShield - shield) / (MAX_LEVEL - 1) * lvl;
    }

    /**
     * gets stats
     */
    public String getName() {
        return name;
    }
    public double getHealth() {
        return health;
    }
    public double getFullHealth() {
        return fullHealth;
    }
    public double getHealthRegen() {
        return healthRegen;
    }
    public double getEnergy() {
        return energy;
    }
    public double getFullEnergy() {
        return fullEnergy;
    }
    public double getEnergyRegen() {
        return energyRegen;
    }
    public double getBaseWeaponPower() {
        return baseWeaponPower;
    }
    public double getAtkSpeed() {
        return atkSpeed;
    }
    public double getArmor() {
        return armor * (1-PER_BONESAWSTACK*bonesawStacks);
    }
    public double getShield() {
        return shield;
    }
    public double getCritDamage() {
        return critDamage;
    }
    public double getCritChance() {
        return critChance;
    }
    public double getWeaponPower() {
        return weaponPower;
    }
    public double getCrystalPower() {
        return crystalPower*(1+brokenStacks*PER_BROKENSTACK);
    }
    public double getBonesawStacks() {
        return bonesawStacks;
    }
    public double getFortHealth() {
        return fortifiedHealth;
    }
    public double getHealthBarrier() {
        return healthBarrier;
    }
    public boolean getImmunity() {
        return isImmune;
    }
    public boolean getSlow() {
        return isSlowed;
    } //slow increases skillshot hit percentage by 20%
    public boolean getStatus() {
        return isDead;
    }
    
    /**
     * changes stats
     */
    public void changeHealth(double addedHealth) {
        health+= addedHealth;
        fullHealth+= addedHealth;
    }
    public void changeHealthRegen(double addedHealthRegen) {
        healthRegen+= addedHealthRegen;
    }
    public void changeEnergy(double addedEnergy) {
        energy+= addedEnergy;
        fullEnergy+= addedEnergy;
    }
    public void changeEnergyRegen(double addedEnergyRegen) {
        energyRegen+= addedEnergyRegen;
    }
    public void changeAtkSpeed(double addedAtkSpeed) {
        atkSpeed+= addedAtkSpeed;
    }
    public void changeArmor(double addedArmor) {
        armor+= addedArmor;
    }
    public void changeShield(double addedShield) {
        shield+= addedShield;
    }
    public void changeCritDamage(double addedCritDamage) {
        critDamage+= addedCritDamage;
    }
    public void changeCritChance(double addedCritChance) {
        if(critChance + addedCritChance < 1)
            critChance+= addedCritChance;
        else
            critChance = 1;
    }
    public void changeWeaponPower(double addedWeaponPower) {
        weaponPower+= addedWeaponPower;
    }
    public void changeCrystalPower(double addedCrystalPower) {
       crystalPower+= addedCrystalPower;
    }

    public void addBonesawStack() {
        if(bonesawStacks<MAX_BONESAWSTACKS)
            bonesawStacks+= 1;
        else
            bonesawStacks = 7;
    }
    public void removeBonesawStack() {
        if(bonesawStacks<=0)
            bonesawStacks = 0;
        else
            bonesawStacks -= 1;
    }
    public void addBrokenStack() {
        if(brokenStacks<MAX_BROKENSTACKS)
            brokenStacks+= 1;
        else
            brokenStacks = 5;
    }
    public void removeBrokenStack() {
        if(brokenStacks<=0)
            brokenStacks = 0;
        else
            brokenStacks -= 1;
    }
    
    public void addWeaponDamage(double output) {
        bpDamage+=output;
    }
    public void BPStackCheck() {
        if(brokenStacks<MAX_BPSTACKS) {
            int stackRequirement= 0;
            for (int y = 0; y<bpStacks; y++) {
                stackRequirement += (125 + y*5);
            }
            if (weaponPowerCounter>=stackRequirement) {
                bpStacks+=1;
                bpDamage+=10;
            }
        }
        else
            brokenStacks = 25;
    }
    public void removeBPStack() {
        if(bpStacks<=0)
            bpStacks = 0;
        else {
            bpStacks -= 1;
            bpDamage-=10;
    }
    }
    
    public void changeFortHealth(double addedFortHealth) {
        fortifiedHealth+= addedFortHealth;
    }
    public void resetFortHealth() {
        fortifiedHealth = 0;
    }
    public void changeHealthBarrier(double addedBarrier) {
        if (healthBarrier+addedBarrier<=0)
            healthBarrier = 0;
        else
        healthBarrier+= addedBarrier;
    }
    public void resetHealthBarrier() {
        healthBarrier = 0;
    }

    public void bpToggle() {
        hasBP = !hasBP;
    }
    public void immuneToggle() {
        isImmune = !isImmune;
    }
    public void slowToggle() {
        isSlowed = !isSlowed;
    }
    public void kill() {
        isDead = true;
    }

    /**
     * Damage and Healing functions:
     * Fortified health absorbs 50% of damage and health barriers absorb 100%
     */
    
    public void takeDamage(double damage) {
        double damageLeft = damage;
        if (healthBarrier>0) {
            if(healthBarrier<damageLeft) {
                damageLeft-=healthBarrier; //subtract barrier from damage and move on.
                healthBarrier = 0; //barrier is destoyed
            }
            else {
                healthBarrier-=damageLeft; // barrier absorbs damage
                damageLeft = 0;
            }
        }
        if(fortifiedHealth>0 && damageLeft > 0) { //check for fHealth and make sure the barrier didn't absorb it all
            double fortSplit = damageLeft/2; //50% absorbtion
            if(fortifiedHealth<=fortSplit) {
                if(health + fortifiedHealth <= damageLeft) { //if the damage is enough to knock out both fHealth and health combined,
                    health = 0;
                    fortifiedHealth = 0;
                    isDead = true; //the hero dies.
                }
                else {
                    health-=(damageLeft-fortifiedHealth); //otherwise fortified health is destoyed
                    fortifiedHealth = 0; // and the rest of the damage is dealt to health
                }
            }
            else { 
                fortifiedHealth-= fortSplit;
                if (health<fortSplit){ //if the health can't absorb its half of the damage,
                    health = 0;
                    fortifiedHealth = 0;
                    isDead = true; //the hero dies.
                }
                else
                    health-=fortSplit;
            }
        }
        else if(damageLeft > 0) { //make sure the barrier didnt absorb it all
            if (health<damageLeft) { //if the health is less than the damage remaining,
                health = 0;
                isDead = true; //the hero dies.
            }
            else {
                health-=damageLeft;
            }
        }
    }
    public void heal(double amount) {
        if(health+amount>=fullHealth) 
            health = fullHealth;
        else
            health+=amount;
    }
    public void fountain() {
        double missingPercent;
        for(int x = 1; x<=3;x++) {
            health+=healthRegen;
            missingPercent = (fullHealth-health)/fullHealth;
            health+= 2*100*missingPercent;
        }
    }
    
    //Buffs functions
    public void applyBuff(String type, double effect, int time) {
        buffs.add(new Buff(this, type, effect, time));
    }

    public void manageBuffs() {
        for (int i = 0; i < buffs.size(); i++) {
            Buff buff = buffs.get(i);
            if (buff.getTime() <= 0) {
                buffs.remove(i);
            } else {
                buff.tick();
            }
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

    /**
     * Type 1 = WP or CP Ability Damage 
     * Type 2 = Crittable Weapon Damage 
     * Type 3 = Basic Attack Weapon Component 
     * Type 4+ = True Damage
     */
    public double calculateDamage(int type, double weaponPower, double power, double pierce, double critChance, double critDmg, double ratio, double defense, boolean isSkillshot, double skillshotChance) {
        double damageTaken = 0;
        if(isSkillshot = true) {
         if((Math.random()*1.01)>skillshotChance)
             return damageTaken;
        }
        if (type == 1) {
            damageTaken = ((power * pierce * ratio) + ((power * ratio * (1 - pierce)) / (1 + (0.01 * (defense)))));
        } else if (type == 2) {
            double critRng = (Math.random() * 1.01);
            if (critRng <= critChance) {
                damageTaken = ((power * critDmg * pierce * ratio) + ((power * critDmg * (1 - pierce)) / (1 + (0.01 * (defense)))));
            } else {
                damageTaken = ((power * pierce * ratio) + ((power * (1 - pierce)) / (1 + (0.01 * (defense)))));
            }
        } else if (type == 3) {
            double critRng = (Math.random() * 1.01);
            if (critRng <= critChance) {
                damageTaken = (((weaponPower + power) * critDmg * pierce) + (((weaponPower + power) * critDmg * (1 - pierce)) / (1 + (0.01 * (defense)))));
            } else {
                damageTaken = (((weaponPower + power) * pierce) + (((weaponPower + power) * (1 - pierce)) / (1 + (0.01 * (defense)))));
            }
        } else {
            damageTaken = power;
        }
        return damageTaken;
    }
}
