package DE;
import robocode.*;
import java.awt.Color;

/**
 * Randoom - a robot by (your name here)
 */
public class Randoom extends AdvancedRobot
{
	byte moveDir = 1;
	
	public void run() {
		while(true) {
			setAhead(400 * moveDir);
			changeColor();
			execute();
		}
	}

	public void onScannedRobot(ScannedRobotEvent e) {
	}

	public void onHitByBullet(HitByBulletEvent e) {
		
	}
	
	public void onHitWall(HitWallEvent e) {
		moveDir *= -1;
	}	
	public void changeColor()
	{
		Color[] colours = {Color.green, Color.black, Color.blue, Color.cyan, 
							Color.darkGray, Color.gray, Color.magenta,
							 Color.orange, Color.pink, Color.red, Color.white, Color.yellow}; 
		setColors(colours[(int) (Math.random() * colours.length)],
                  colours[(int) (Math.random() * colours.length)],
                  colours[(int) (Math.random() * colours.length)],
                  colours[(int) (Math.random() * colours.length)],
                  colours[(int) (Math.random() * colours.length)]);
	}
}
