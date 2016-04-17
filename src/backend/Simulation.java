package backend;

import heroes.*;
import java.lang.Thread;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import java.awt.*;
import java.awt.event.*;
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
    int width;
    int height;
    
    class Output extends TimerTask{        

        public void run() {
            if(hero1.getHealth() <= 0 || hero2.getHealth() <= 0) System.exit(0);
            hero1.manageBuffs();
            hero2.manageBuffs();
            if(hero1.onCd(1)) {System.out.println("Damage: " + hero1.firstAbility()); hero2.setHealth(hero2.getHealth()-hero1.firstAbility());}
            if(hero1.onCd(2)) hero2.setHealth(hero2.getHealth()-hero1.secondAbility());
            if(hero1.onCd(3)) {System.out.println("Damage: " + hero1.ultimateAbility()); hero2.setHealth(hero2.getHealth()-hero1.ultimateAbility());}
            System.out.println(hero1.getName() + "'s health: " + hero1.getHealth());
        }
    }
    
    class Display extends TimerTask{
        private JFrame frame;
        private DrawPanel healthbars;

        public Display(){
            healthbars = new DrawPanel();
            frame = new JFrame(hero1.getName() + " v.s " + hero2.getName());
            frame.setSize(500, 300);
            frame.setLayout(new BorderLayout());
            width = frame.getWidth();
            height = frame.getHeight();
            frame.setResizable(false);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLocationRelativeTo(null);
            frame.add(healthbars);
            frame.setVisible(true);
        }
        public void run(){ frame.repaint(); }
    }
    
    class DrawPanel extends JPanel {
        
        int strokeWidth = 5;
        public void paintComponent(Graphics g){
           
            Graphics2D g2d = (Graphics2D) g;            
            double percentHealth1 = hero1.getHealth()/hero1.getFullHealth();
            double percentHealth2 = hero2.getHealth()/hero2.getFullHealth();
            g2d.setColor(Color.RED);
            g2d.fillRect(0, strokeWidth, (int)(percentHealth1 * (width/2))+1, 50-strokeWidth); //Player 1 Healthbar
            g2d.setColor(Color.GREEN);
            g2d.fillRect(width, strokeWidth, -(int)(percentHealth2 * (width/2))+1, 50-strokeWidth); // Player 2 Healthbar
            
            g2d.setColor(Color.BLACK);
            g2d.setStroke(new BasicStroke(strokeWidth));
            g2d.drawRect(0+strokeWidth/2, 0+strokeWidth/2, width-2*strokeWidth, 50);
            g2d.drawLine(width/2, 0, 250, 50);
            
            g2d.setFont(new Font("Calibri", Font.BOLD, 16));
            g2d.drawString("" + (int)hero1.getHealth() + "/" + (int)hero1.getFullHealth(), 45, 40);
            g2d.drawString("" + (int)hero2.getHealth() + "/" + (int)hero2.getFullHealth(), width-130, 40);
        }
    }
    class Hero1BasicAttack extends TimerTask{
        public void run(){
            hero2.setHealth( hero2.getHealth() - hero1.calculateDamage(1, hero1.getWeaponDmg(), 0) );
        }
    }
    class Hero2BasicAttack extends TimerTask{
        public void run(){
            hero1.setHealth( hero1.getHealth() - hero2.calculateDamage(1, hero2.getWeaponDmg(), 0) );
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
            Timer output = new Timer();
            Timer display = new Timer();
            Timer hero1BA = new Timer();
            Timer hero2BA = new Timer();
            output.schedule(new Output(), 0, 1000);
            display.schedule(new Display(), 0, (long)(1000/60));
            hero1BA.schedule(new Hero1BasicAttack(), 0, (long)(1000/hero1.getAtkSpeed()));
            hero2BA.schedule(new Hero2BasicAttack(), 0, (long)(1000/hero2.getAtkSpeed()));
        }
    }
    
    public Hero createHero(String name, int level){
        switch (name.toLowerCase()){
            case "ringo": return new Ringo(level);
            case "ardan": return new Ardan(level);
            case "krul": return new Krul(level);
            default: return null;
        }
    }
    
    
    
    
}
