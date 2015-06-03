package bouncingball;
import java.awt.*;
import java.util.Formatter;


public class Ball {
   float x, y;           // Ball's center x and y 
   float speedX, speedY; // Ball's speed per step in x and y 
   float radius;         // Ball's radius
   private Color color;  // Ball's color
   private static final Color DEFAULT_COLOR = Color.BLACK;
   private double gravity=9.8;
   private double angle;
   /**
    * Constructor: For user friendliness, user specifies velocity in speed and
    * moveAngle in usual Cartesian coordinates. Need to convert to speedX and
    * speedY in Java graphics coordinates for ease of operation.
    */
   public Ball(float x, float y, float radius, float speed, float angleInDegree,
         Color color) {
      this.x = x;
      this.y = y;
      // Convert (speed, angle) to (x, y), with y-axis inverted
      this.speedX = (float)(speed * Math.cos(Math.toRadians(angleInDegree)));
      this.speedY = (float)(-speed * (float)Math.sin(Math.toRadians(angleInDegree))-gravity*0.001);
      this.radius = radius;
      this.color = color;
      this.angle=angleInDegree;
   }
   /** Constructor with the default color */
   public Ball(float x, float y, float radius, float speed, float angleInDegree) {
      this(x, y, radius, speed, angleInDegree, DEFAULT_COLOR);
   }
   
   /** Draw itself using the given graphics context. */
   public void draw(Graphics g) {
      g.setColor(color);
      g.fillOval((int)(x - radius), (int)(y - radius), (int)(2 * radius), (int)(2 * radius));
   }
   
   /** 
    * Make one move, check for collision and react accordingly if collision occurs.
    * 
    * @param box: the container (obstacle) for this ball. 
    */
   public void moveOneStepWithCollisionDetection(ContainerBox box,int UPDATE_RATE) {
      // Get the ball's bounds, offset by the radius of the ball
      float ballMinX = box.minX + radius;
      float ballMinY = box.minY + radius;
      float ballMaxX = box.maxX - radius;
      float ballMaxY = box.maxY - radius;
   
      // Calculate the ball's new position
      x += speedX;
      y += speedY;
      // Check if the ball moves over the bounds. If so, adjust the position and speed.
      if (x < ballMinX) {
         speedX = (float) (-speedX*Math.cos(Math.toRadians(angle))); // Reflect along normal
         x = ballMinX;     // Re-position the ball at the edge
      } else if (x > ballMaxX) {
         speedX = (float) -(speedX*Math.cos(Math.toRadians(angle)));
         x = ballMaxX;
      }
      // May cross both x and y bounds
      if (y < ballMinY) {
         speedY = (float) -(speedY*(float)Math.sin(Math.toRadians(angle))-gravity*0.001);
         y = (float) (ballMinY-(0.5*gravity*0.001*0.001));
      } else if (y > ballMaxY) {
         speedY = (float) -(speedY*(float)Math.sin(Math.toRadians(angle))-gravity*0.001);;
         y = (float) (ballMaxY-(0.5*gravity*0.001*0.001));
      }
      //angle=getMoveAngle();
      if(speedX<1 && speedY<1)
    	  speedX=speedY=0;
   }
   
   /** Return the magnitude of speed. */
   public float getSpeed() {
      return (float)Math.sqrt(speedX * speedX + speedY * speedY);
   }
   
   /** Return the direction of movement in degrees (counter-clockwise). */
   public float getMoveAngle() {
      return (float)Math.toDegrees(Math.atan2(-speedY, speedX));
   }
   
   /** Return mass */
   public float getMass() {
      return radius*radius*radius / 100f;  // Normalize by a factor
   }
   
   /** Return the kinetic energy (0.5mv^2) */
   public float getKineticEnergy() {
      return 0.5f * getMass() * (speedX * speedX + speedY * speedY);
   }
  
   /** Describe itself. */
   public String toString() {
      sb.delete(0, sb.length());
      formatter.format("@(%3.0f,%3.0f) r=%3.0f V=(%2.0f,%2.0f) " +
            "S=%4.1f \u0398=%4.0f KE=%3.0f", 
            x, y, radius, speedX, speedY, getSpeed(), getMoveAngle(),
            getKineticEnergy());  // \u0398 is theta
      return sb.toString();
   }

   // Re-use to build the formatted string for toString()
   private StringBuilder sb = new StringBuilder();
   private Formatter formatter = new Formatter(sb);
}