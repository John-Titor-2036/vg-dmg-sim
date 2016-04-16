package backend;

import heroes.*;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.*;

/**
 * Write a description of class Simulation here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Simulation
{

    Hero hero1;
    Hero hero2;

    class Display extends TimerTask{
        private JFrame frame;
        public Display(){
            frame = new JFrame(hero1.getName() + " v.s " + hero2.getName());
            frame.setSize(500, 300);
            frame.setResizable(false);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        }
        public void run() {
            if(hero1.getHealth() <= 0 || hero2.getHealth() <= 0) System.exit(0);
            hero1.manageBuffs();
            hero2.manageBuffs();
            int pastHealth = hero2.getHealth();
            if(hero1.onCd(1)) {
                hero2.setHealth(hero2.getHealth()-hero1.firstAbility());
                System.out.println("Damage: " + (pastHealth - hero2.getHealth()));
                System.out.println(hero2.getName() + "'s health: " + hero2.getHealth() + "\n");
            }
            if(hero1.onCd(2)) {
                hero2.setHealth(hero2.getHealth()-hero1.secondAbility());
                System.out.println("Damage: " + (pastHealth - hero2.getHealth()));
                System.out.println(hero2.getName() + "'s health: " + hero2.getHealth() + "\n");
            }
            if(hero1.onCd(3)) {
                hero2.setHealth(hero2.getHealth()-hero1.ultimateAbility());
                System.out.println("Damage: " + (pastHealth - hero2.getHealth()));
                System.out.println(hero2.getName() + "'s health: " + hero2.getHealth() + "\n");
            }


        }
    }

    public Simulation(){
        Scanner sc = new Scanner(System.in);

        System.out.println("Hero 1: ");
        String firstHeroName = sc.nextLine();
        System.out.println("Enter level: ");
        int firstHeroLevel = sc.nextInt();
        sc.nextLine();
        hero1 = createHero(firstHeroName, firstHeroLevel);
        System.out.println("Hero 2: ");
        String secondHeroName = sc.nextLine();
        System.out.println("Enter level: ");
        int secondHeroLevel = sc.nextInt();
        sc.nextLine();
        hero2 = createHero(secondHeroName, secondHeroLevel);

        if(hero1 != null && hero2 != null){
            Timer timer = new Timer();
            timer.schedule(new Display(), 0, 1000);
        }
    }

    public Hero createHero(String name, int level){
        switch (name.toLowerCase()){
            case "ringo": return new Ringo(level);
            case "ardan": return new Ardan(level);
            case "krul" : return new Krul (level);
            default: return null;
        }
    }




}
