package DE;
import robocode.*;
import java.awt.Color;

/**
 * Randoom - a robot by (your name here)
 */
public class Randoom extends Robot
{
	/**
	 * run: Randoom's default behavior
	 */
	
			
	public void run() {
		turnGunRight(-90);
		while(true) {
			fire(1);
		}
	}

	public void onScannedRobot(ScannedRobotEvent e) {
	}

	public void onHitByBullet(HitByBulletEvent e) {
		back(10);
	}
	
	public void onHitWall(HitWallEvent e) {
		back(20);
	}	
}
