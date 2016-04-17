package backend;

import java.util.ArrayList;

/*

Authors: Jerry Zhou, Degaulle Dai

 */
public class Hero {
 
    private final int MAX_LEVEL = 12;
    
    //variables initialized in constructor
    private String name;
    private double health;
    private double fullHealth;
    private double healthRegen; 
    private double energy;
    private double fullEnergy;
    private double energyRegen; 
    private double baseWeaponPower; //should stay the same!
    private double atkSpeed;
    private double armor;
    private double shield;
    private double accuracy;
    private boolean isRanged;
    //variables that can be changed but have defaults.
    private double critDamage = 1.5; //default crit multiplier.
    private double critChance = 0.00; //default crit chance = 0%.
    private double armorPierce = 0.00;
    private double shieldPierce = 0.00;
    private double wpLifesteal = 0;
    private double cpLifesteal = 0;
    private double weaponPower = 0; // added weapon power.
    private double crystalPower = 0; //heroes don't start with any crysal power, hence initialized at 0.
    private double fortifiedHealth = 0; //fortified health is not set in the constructor, default is 0.
    private double healthBarrier = 0; //from effects such as alpha ult and reflex block. default is 0.

    private int wpCounter = 0; // used for bp stack counting also
    private int cpCounter = 0;
    
    private boolean isImmune; //reflex block
    private boolean isSlowed; //shiversteel or frostburn effects, increase skillshot hit percentage by 20%
    private boolean isDead = false;
    
    private boolean hasSerpent = false;
    private boolean hasBreaking = false;
    private boolean hasTension = false;
    private boolean hasBonesaw = false;
    private boolean hasBroken = false;
    private boolean hasFrostburn = false;
    private boolean hasEve = false;
    private boolean hasAfter = false;
    private boolean hasAlt = false;
    private boolean hasCrucible = false;
    private boolean hasFountain = false;
    private boolean hasAegis = false;
    private boolean hasReflex = false;
    private boolean hasAtlas = false;
    private boolean hasStormguard = false;
    private boolean hasShiver = false;
    
    //stack variables on this char by others
    private final int MAX_BONESAWSTACKS = 7;
    private final double PER_BONESAWSTACK = 0.07;
    private int bonesawStacks = 0; //if enemy has a bonesaw, this will keep track of each stack.
    
    private int atlasTimer = 0;
    
    private int shiverTimer = 0;
    
    //other items with passives
    private double serpentDamage; //serpents variables
    private double fullSerpentDamage;

    private final int MAX_BREAKINGSTACKS = 25; //breaking point variables
    private final int PER_BREAKINGSTACK = 10;
    private int breakingStacks = 0;
    
    private boolean tensionProc = false;
    private int tensionCounter = 0;
    
    private final int MAX_BROKENSTACKS = 5; //broken myth variables
    private final double PER_BROKENSTACK = 0.07;
    private int brokenStacks = 0; //will be 0 all the time if the hero has not a broken myth.
    
    private double eveDamage;
    private double fullEveDamage;
    
    private boolean afterProc = false;
    
    private boolean altProc = false;
    
    private int fountainCooldown = 0;
    private boolean fountainActive = false;
    
    private int reflexCooldown = 0;
    private int crucibleCooldown = 0;   
    private int aegisCooldown = 0;
    private boolean reflexActive = false;
    private boolean crucibleActive = false;
    private boolean aegisActive = false;
    private boolean reflexOn = false;
    private boolean crucibleOn = false;
    private boolean aegisOn = false;
    
    private int atlasCooldown = 15;
    private boolean atlasActive = false;
    
    private int shiverCooldown = 20;
    private boolean shiverActive = false;
    private boolean shiverOn = false;
    private int shiverActiveTimer = 6;

    /**
     * constructor
     */
    public Hero(String name, int lvl, double health, double maxHealth, double healthRegen, double maxHealthRegen, 
                                      double energy, double maxEnergy, double energyRegen, double maxEnergyRegen, 
                                      double weaponPower, double maxWeaponPower, double atkSpeed, double maxAtkSpeed, 
                                      double armor, double maxArmor, double shield, double maxShield, double accuracy, boolean isRanged) {
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
        serpentDamage = 600 + (600)/11*lvl;
        fullSerpentDamage = serpentDamage;
        eveDamage = serpentDamage;
        fullEveDamage = serpentDamage;
        this.accuracy = accuracy;
        this.isRanged = isRanged;
    }

    /**
     * gets stats
     */
    public String getName() {
        return name;
    }
    public boolean getRanged() {
    return isRanged;
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
    public double getArmorPierce() {
        return armorPierce;
    }
    public double getShieldPierce() {
        return shieldPierce;
    }
    public double getWPLifesteal() {
        return wpLifesteal;
    }
    public double getCPLifesteal() {
        return cpLifesteal;
    }
    public double getWeaponPower() {
        return weaponPower;
    }
    public double getCrystalPower() {
        return crystalPower*(1+brokenStacks*PER_BROKENSTACK);
    }
    public double getFortHealth() {
        return fortifiedHealth;
    }
    public double getHealthBarrier() {
        return healthBarrier;
    }
    
    public double getWeaponDamage() {
        return wpCounter;
    }
    public double getCrystalDamage() {
        return cpCounter;
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
    
    public boolean hasSerpent() {
        return hasSerpent;
    }
    public boolean hasBreaking() {
        return hasBreaking;
    }
    public boolean hasTension() {
        return hasTension;
    }
    public boolean hasBonesaw() {
        return hasBonesaw;
    }
    public boolean hasBroken() {
        return hasBroken;
    }
    public boolean hasFrostburn() {
        return hasFrostburn;
    }
    public boolean hasEve() {
        return hasEve;
    }
    public boolean hasAfter() {
        return hasAfter;
    }
    public boolean hasAlt() {
        return hasAlt;
    }
    public boolean hasCrucible() {
        return hasCrucible;
    }
    public boolean hasFountain() {
        return hasFountain;
    }
    public boolean hasAegis() {
        return hasAegis;
    }
    public boolean hasReflex() {
        return hasReflex;
    }
    public boolean hasAtlas() {
        return hasAtlas;
    }
    public boolean hasStormguard() {
        return hasStormguard;
    }
    public boolean hasShiver() {
        return hasShiver;
    }
    //PASSIVE AND ACTIVE ITEMS
    public double getSerpentDamage() {
        return serpentDamage;
    }
    public double getBonesawStacks() {
        return bonesawStacks;
    }
    public double getBrokenStacks() {
        return brokenStacks;
    }
    public boolean getReflexOn() {
        return reflexOn;
    }
    public boolean getAegisOn() {
        return aegisOn;
    }
    public boolean getCrucibleOn() {
        return crucibleOn;
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
    public void changeArmorPierce(double addedArmorPierce) {
        armorPierce+=addedArmorPierce;
    }
    public void changeShieldPierce(double addedShieldPierce) {
        shieldPierce+=addedShieldPierce;
    }
    public void changeWPLifesteal(double addedWPLifesteal) {
        wpLifesteal+= addedWPLifesteal;
    }
    public void changeCPLifesteal(double addedCPLifesteal) {
        cpLifesteal+= addedCPLifesteal;
    }
    public void changeWeaponPower(double addedWeaponPower) {
        weaponPower+= addedWeaponPower;
    }
    public void changeCrystalPower(double addedCrystalPower) {
       crystalPower+= addedCrystalPower;
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

    public void addWeaponDamage(double output) {
        wpCounter+=output;
    }
    public void addCrystalDamage(double output) {
        cpCounter+=output;
    }
    
    public void immuneToggle() {
        isImmune = !isImmune;
    }
    public void slow() {
        isSlowed = true;
    }
    public void deSlow() {
        isSlowed = false;
    }
    public void kill() {
        isDead = true;
    }

    public void getSerpent() {
        hasSerpent= true;
    }
    public void getBreaking() {
        hasBreaking= true;
    }
    public void getTension() {
        hasTension= true;
    }
    public void getBonesaw() {
        hasBonesaw= true;
    }
    public void getBroken() {
        hasBroken= true;
    }
    public void getFrostburn() {
        hasFrostburn= true;
    }
    public void getEve() {
        hasEve= true;
    }
    public void getAfter() {
        hasAfter= true;
    }
    public void getAlt() {
        hasAlt= true;
    }
    public void getCrucible() {
        hasCrucible= true;
        crucibleActive = true;
    }
    public void getFountain() {
        hasFountain= true;
        fountainActive = true;
    }
    public void getAegis() {
        hasAegis= true;
        aegisActive = true;
    }
    public void getReflex() {
        hasReflex= true;
        reflexActive = true;
    }
    public void getAtlas() {
        hasAtlas = true;
        atlasActive = true;
    }
    public void getStormguard() {
        hasStormguard= true;
    }
    public void getShiver() {
        hasShiver= true;
        shiverActive = true;
    }
    //PASSIVE AND ACTIVE ITEMS
    public void useSerpent(double damage) {
        if (health<fullHealth) {
            if (damage < serpentDamage) {
                if (health + damage * 0.35 < fullHealth) {
                    health += damage * 0.25;
                    serpentDamage -= damage;
                } else {
                    double healed = fullHealth - health;
                    health = fullHealth;
                    serpentDamage -= healed * 4;
                }
            }
            else {
                if (health + serpentDamage * 0.35 < fullHealth) {
                    health += serpentDamage * 0.25;
                    serpentDamage = 0;
                } else {
                    double healed = fullHealth - health;
                    health = fullHealth;
                    serpentDamage -= healed * 4;
                }
            }
        }
    }
    
    public void serpentSecond() {
        if(serpentDamage<fullSerpentDamage) {
            if(serpentDamage+fullSerpentDamage/40<fullSerpentDamage) {
                serpentDamage += fullSerpentDamage/40;
            }
            else {
                serpentDamage = fullSerpentDamage;
            }
        }
    }
    
    public void breakingStack() {
        if (breakingStacks < MAX_BREAKINGSTACKS) {
            int stackRequirement = 0;
            for (int y = 0; y < breakingStacks; y++) {
                stackRequirement += (125 + y * 5);
            }
            while (wpCounter > stackRequirement) {
                if (wpCounter >= stackRequirement) {
                    breakingStacks += 1;
                    weaponPower += 10;
                }
                for (int y = 0; y < breakingStacks; y++) {
                    stackRequirement += (125 + y * 5);
                }
            }
        }
    }
    
    public void addBonesawStack() {
        if(bonesawStacks<MAX_BONESAWSTACKS)
            bonesawStacks+= 1;
        else
            bonesawStacks = 7;
    }

    public void addBrokenStack() {
        if(brokenStacks<MAX_BROKENSTACKS)
            brokenStacks+= 1;
        else
            brokenStacks = 5;
    }
    
    public void useEve(double damage) {
        if (health<fullHealth) {
            if (damage < eveDamage) {
                if (health + damage * 0.35 < fullHealth) {
                    health += damage * 0.25;
                    eveDamage -= damage;
                } else {
                    double healed = fullHealth - health;
                    health = fullHealth;
                    eveDamage -= healed * 4;
                }
            }
            else {
                if (health + eveDamage * 0.35 < fullHealth) {
                    health += eveDamage * 0.25;
                    eveDamage = 0;
                } else {
                    double healed = fullHealth - health;
                    health = fullHealth;
                    eveDamage -= healed * 4;
                }
            }
        }
    }
    public void eveSecond() {
        if(eveDamage<fullEveDamage) {
            if(eveDamage+fullEveDamage/40<fullEveDamage) {
                eveDamage += fullEveDamage/40;
            }
            else {
                eveDamage = fullEveDamage;
            }
        }
    }
    
    public void afterActive() {
        afterProc = true;
    }
    
    public void reflexBlock() {
        healthBarrier+= (fullHealth-health)*.25;
        isImmune = true;
        reflexCooldown = 45;
        reflexOn = true;
        reflexActive = false;
    }
    public void crucible() {
        healthBarrier+= (fullHealth-health)*.25;
        isImmune = true;
        crucibleCooldown = 45;
        crucibleOn = true;
        crucibleActive = false;
    }
    public void aegis() {
        healthBarrier+= (fullHealth-health)*.25;
        isImmune = true;
        aegisCooldown = 45;
        aegisOn = true;
        aegisActive = false;
    }
    
    public void atlas(Hero target) {
        target.pauldron();
        atlasActive = false;
        atlasCooldown = 15;
    }
    public void pauldron() {
        if(isImmune = false) {
        atlasTimer=5;
        atkSpeed*=0.35;
        }
    }
    
    public void shiver() {
        shiverActive = false;
        shiverCooldown = 20;
        shiverActiveTimer = 6;
    }
    public void steelMelee() {
        if (isImmune = false){
            shiverTimer = 2;
            slow();
        }
    }
    public void steelRange() {
        if(isImmune = false) {
            shiverTimer = 1;
            slow();
        }
    }
    
    public void fountain() {
        double missingPercent;
        for(int x = 1; x<=3;x++) {
            health+=healthRegen;
            missingPercent = (fullHealth-health)/fullHealth;
            health+= 2*100*missingPercent;
            fountainActive = false;
        }
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
    
    public void passSecond() {
        if (hasTension == true && tensionProc == false) {
            tensionCounter+=1;
            if(tensionCounter>=6) {
                tensionProc = true;
                tensionCounter = 0;
            }
        }
        if (fountainCooldown>0)
            fountainCooldown-=1;
        if (reflexCooldown>0)
            reflexCooldown-=1;
        if (aegisCooldown>0)
            aegisCooldown-=1;
        if (crucibleCooldown>0)
            crucibleCooldown-=1;
        if (atlasCooldown>0)
            atlasCooldown-=1;
        if (shiverCooldown>0)
            shiverCooldown-=1;
        isImmune = false;
        reflexOn = false;
        crucibleOn = false;
        aegisOn = false;
        if (atlasTimer > 0){
            atlasTimer-=1;
            if (atlasTimer == 0)
                atkSpeed*=100/35;
        }
        if(shiverTimer>0){
            shiverTimer-=1;
            if(shiverTimer==0)
                deSlow();
        }
        if (shiverActiveTimer>0) {
            shiverActiveTimer-=1;
        }
        
    }
    
    public void basicAttack(Hero target) {
        double damage = calculateDamage(4,baseWeaponPower,weaponPower,armorPierce,critChance,critDamage,100,false,1,this,target);
        if (tensionProc = true) {
            damage+=calculateDamage(3,baseWeaponPower,180,armorPierce,critChance,critDamage,100,false,1,this,target);
        }
        wpCounter+=damage;
        if (shiverActiveTimer > 0) {
            if (isRanged = true)
            target.steelRange();
            else
            target.steelMelee();
        }
        if(hasBreaking = true) {
            breakingStack();
        }
        if (hasBonesaw = true) {
            target.addBonesawStack();
        }
        if (hasSerpent = true) {
            useSerpent(damage);
        }
        wpCounter+=damage;
        if (hasAfter = true) {
            if (afterProc = true) {
                double afterDamage = calculateDamage(2,baseWeaponPower,target.getHealth()*0.15,shieldPierce,critChance,critDamage,100,false,accuracy,this,target);
                damage+=afterDamage;
                cpCounter+=afterDamage;
                afterProc = false;
            }
        }
        if (hasAlt = true) {
            if (altProc = true) {
                double altDamage = calculateDamage(2,baseWeaponPower,0.7*crystalPower,shieldPierce,critChance,critDamage,100,false,accuracy,this,target);
                damage+=altDamage;
                cpCounter+=altDamage;
            }
            altProc = !altProc;
        }
    }
    /**
     * Type 1 = WP Ability Damage
     * Type 2 = CP Damage 
     * Type 3 = Crittable Weapon Damage 
     * Type 4 = Basic Attack Weapon Component 
     * Type 4+ = True Damage
     */
    public double calculateDamage(int type, double weaponPower, double power, double pierce, double critChance, double critDmg, double ratio, boolean isSkillshot, double accuracy, Hero sender, Hero reciever) {
        double damageTaken = 0;
        if(isSkillshot = true) {
         if((Math.random()*1.01)>accuracy)
             return damageTaken;
        }
        if (type == 1) {
            damageTaken = ((power * pierce * ratio) + ((power * ratio * (1 - pierce)) / (1 + (0.01 * (reciever.getArmor())))));
        } 
        if (type == 2) {
            damageTaken = ((power * pierce * ratio) + ((power * ratio * (1 - pierce)) / (1 + (0.01 * (reciever.getShield())))));
            if(sender.hasFrostburn() == true) {
                reciever.slow();
            }
        } else if (type == 3) {
            double critRng = (Math.random() * 1.01);
            if (critRng <= critChance) {
                damageTaken = ((power * critDmg * pierce * ratio) + ((power * critDmg * (1 - pierce)) / (1 + (0.01 * (reciever.getArmor())))));
            } else {
                damageTaken = ((power * pierce * ratio) + ((power * (1 - pierce)) / (1 + (0.01 * (reciever.getArmor())))));
            }
        } else if (type == 4) {
            double critRng = (Math.random() * 1.01);
            if (critRng <= critChance) {
                damageTaken = (((weaponPower + power) * critDmg * pierce) + (((weaponPower + power) * critDmg * (1 - pierce)) / (1 + (0.01 * (reciever.getArmor())))));
            } else {
                damageTaken = (((weaponPower + power) * pierce) + (((weaponPower + power) * (1 - pierce)) / (1 + (0.01 * (reciever.getArmor())))));
            }
        } else {
            damageTaken = power;
        }
        return damageTaken;
    }
}
