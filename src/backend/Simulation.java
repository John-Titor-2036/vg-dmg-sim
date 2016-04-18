package backend;

import heroes.*;
import java.lang.Thread;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * Write a description of class Simulation here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Simulation
{
    int secondsPassed = 0;
    Hero hero1;
    Hero hero2;
    int width;    
    int height;
    boolean running = false;
    int fp;
    int sp;
    int tp;
    class Output extends TimerTask{        

        public void run() {
            if(running) {
            if(hero1.getHealth() <= 0 || hero2.getHealth() <= 0){ System.out.println("\n\n" + secondsPassed); running = false; }
            hero1.manageBuffs();
            hero2.manageBuffs();
            if(hero1.hasEnergy(1) && hero1.firstAbility()) {             
                hero1.setEnergy(hero1.getEnergy()-hero1.getEnergyCost()[1]);
            }
            if(hero2.hasEnergy(1) && hero2.firstAbility()) {
                hero2.setEnergy(hero2.getEnergy()-hero2.getEnergyCost()[1]);
            }
            if(hero1.hasEnergy(2) && hero1.secondAbility()){
                hero1.setEnergy(hero1.getEnergy()-hero1.getEnergyCost()[2]);
            }            
            if(hero2.hasEnergy(2) && hero2.secondAbility()){                
                hero2.setEnergy(hero2.getEnergy()-hero2.getEnergyCost()[2]);
            }
            if(hero1.hasEnergy(3) && hero1.thirdAbility()) {                 
                hero1.setEnergy(hero1.getEnergy()-hero1.getEnergyCost()[3]);
            }
            if(hero2.hasEnergy(3) && hero2.thirdAbility()) {                
                hero2.setEnergy(hero2.getEnergy()-hero2.getEnergyCost()[3]);
            }
            secondsPassed++;
            }
        }
    }
    
    class Display extends TimerTask{
        private JFrame frame;
        private DrawPanel healthbars;

        public Display(){
            healthbars = new DrawPanel();
            frame = new JFrame(hero1.getName() + " vs " + hero2.getName());
            frame.setSize(500, 281+55);
            frame.setLayout(new BorderLayout());
            width = frame.getWidth();
            height = frame.getHeight();
            frame.setResizable(false);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLocationRelativeTo(null);
            frame.add(healthbars);
            frame.setVisible(true);
            running = true;
        }
        public void run(){ if(running) frame.repaint(); }
    }
    
    class DrawPanel extends JPanel{
        
        int strokeWidth = 3;
        BufferedImage img;        
        public DrawPanel(){
            ClassLoader cl = this.getClass().getClassLoader();
            try {
                img = ImageIO.read(cl.getResource("images/catherine-kestrel.jpg"));
            } catch (IOException ex) {
                Logger.getLogger(Simulation.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        @Override
        public void paintComponent(Graphics g){
             
            Graphics2D g2d = (Graphics2D) g; 
            g2d.drawImage(img, 0, 55, width, height-55, null);
            
            // Health bars
            g2d.setColor(new Color(235, 211, 231));
            g2d.fillRect(0, 0, width, 60);
            
            double percentHealth1 = hero1.getHealth()/hero1.getFullHealth();
            double percentHealth2 = hero2.getHealth()/hero2.getFullHealth();
            g2d.setColor(Color.RED);
            g2d.fillRect(0, strokeWidth, (int)(percentHealth1 * (width/2))+1, 50-strokeWidth); //Player 1 Healthbar
            g2d.setColor(Color.GREEN);
            g2d.fillRect(width, strokeWidth, -(int)(percentHealth2 * (width/2))+1, 50-strokeWidth); // Player 2 Healthbar
            
            // Health numbers
            g2d.setColor(Color.BLACK);
            g2d.setFont(new Font("Calibri", Font.BOLD, 16));
            g2d.drawString("" + (int)hero1.getHealth() + "/" + (int)hero1.getFullHealth(), 45, 40);
            g2d.drawString("" + (int)hero2.getHealth() + "/" + (int)hero2.getFullHealth(), width-130, 40);
            
            //Energy bars
            double percentEnergy1 = hero1.getEnergy()/hero1.getFullEnergy();
            double percentEnergy2 = hero2.getEnergy()/hero2.getFullEnergy();
            g2d.setColor(new Color(0, 247, 255));
            g2d.fillRect(0, 50+strokeWidth, (int)(percentEnergy1 * (width/2))+1, 5); //Player 1 Energybar
            g2d.fillRect(width, 50+strokeWidth, -(int)(percentEnergy2 * (width/2))+1, 5); // Player 2 Energybar
            
            // Outer box & line
            g2d.setColor(Color.BLACK);
            g2d.setStroke(new BasicStroke(strokeWidth));
            g2d.drawRect(0+strokeWidth/2, 0+strokeWidth/2, width-2*strokeWidth, 50);
            g2d.drawRect(0, 50, width-2*strokeWidth, 11-strokeWidth);
            g2d.drawLine(width/2, 0, 250, 55);
        }
    }
    class Hero1BasicAttack extends TimerTask{
        public void run(){
            if(!running) cancel();
            if(hero1.getFirstCd() <= 0) hero1.setFirstCd(hero1.getDefaultCd()[1]);
            if(hero1.getSecondCd() <= 0) hero1.setSecondCd(hero1.getDefaultCd()[2]);
            if(hero1.getThirdCd() <= 0) hero1.setThirdCd(hero1.getDefaultCd()[3]);     
            hero2.setHealth( hero2.getHealth() - hero1.basicAttack(hero1.getWeaponDmg()));
        }
    }
    class Hero2BasicAttack extends TimerTask{
        public void run(){
            if(!running) cancel();
            if(hero2.getFirstCd() <= 0) hero2.setFirstCd(hero2.getDefaultCd()[1]);
            if(hero2.getSecondCd() <= 0) hero2.setSecondCd(hero2.getDefaultCd()[2]);
            if(hero2.getThirdCd() <= 0) hero2.setThirdCd(hero2.getDefaultCd()[3]);            
            hero1.setHealth( hero1.getHealth() - hero2.basicAttack(hero2.getWeaponDmg()));
        }
    }
    
    public Simulation(){
        Scanner sc = new Scanner(System.in);
        int totalPoints;
        System.out.println("Hero 1: ");
        String firstHeroName = sc.nextLine();
        System.out.println("Enter level: ");
        int firstHeroLevel = sc.nextInt();
        totalPoints = firstHeroLevel;
        sc.nextLine();
        
        System.out.println("You now have " + totalPoints + " points to spend. " + "\n A? ");
        fp = sc.nextInt();
        sc.nextLine();
        System.out.println("B? ");
        sp = sc.nextInt();
        sc.nextLine();
        System.out.println("C? ");
        tp = sc.nextInt();
        sc.nextLine();
        hero1 = createHero(firstHeroName, firstHeroLevel, fp, sp, tp);
        
        System.out.println("Hero 2: ");
        String secondHeroName = sc.nextLine();
        System.out.println("Enter level: ");
        int secondHeroLevel = sc.nextInt();
        totalPoints = secondHeroLevel;
        sc.nextLine();
        System.out.println("You now have " + totalPoints + " points to spend. " + "\n A? ");
        fp = sc.nextInt();
        sc.nextLine();
        System.out.println("B? ");
        sp = sc.nextInt();
        sc.nextLine();
        System.out.println("C? ");
        tp = sc.nextInt();
        sc.nextLine();
        hero2 = createHero(secondHeroName, secondHeroLevel, fp, sp, tp);
        
        if(hero1 != null && hero2 != null){
            hero1.setTarget(hero2);
            hero2.setTarget(hero1);
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
    
    public Hero createHero(String name, int level, int first, int second, int third){
        switch (name.toLowerCase()){
            case "ringo": return new Ringo(level, first, second, third);
            case "ardan": return new Ardan(level, first, second, third);
            case "krul": return new Krul(level, first, second, third);
            default: return null;
        }
    }
    
    
    
    
}
