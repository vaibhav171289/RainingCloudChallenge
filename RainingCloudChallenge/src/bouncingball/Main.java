package bouncingball;

import java.util.Scanner;

import javax.swing.JFrame;
/**
 * Main Program for running the bouncing ball as a standalone application.
 */
public class Main {
	private static Scanner scan;
   // Entry main program
   public static void main(String[] args) {
	   scan= new Scanner(System.in);
      // Run UI in the Event Dispatcher Thread (EDT), instead of Main thread
      javax.swing.SwingUtilities.invokeLater(new Runnable() {
         public void run() {
            JFrame frame = new JFrame("A World of Balls");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            System.out.println("Enter frame Width");
            
            int frameWidth=scan.nextInt();
            
            System.out.println("Enter Frame Height");
            int frameHeight=scan.nextInt();
            System.out.println("Enter Throwing speed");
            int throwingSpeed=scan.nextInt();
            System.out.println("Enter Throwing angle in degree");
            int throwingAngle=scan.nextInt();
            System.out.println("Enter Radius of ball in units");
            int radius=scan.nextInt();
            frame.setContentPane(new BallWorld(frameWidth, frameHeight,throwingSpeed,throwingAngle,radius)); // BallWorld is a JPanel
            frame.pack();            // Preferred size of BallWorld
            frame.setVisible(true);  // Show it
         }
      });
   }
}