package DE;
import robocode.*;
import java.awt.Color;
import robocode.util.*
/**
 * Randoom - a robot by (your name here)
 */
public class Randoom extends Robot
{
	double bulletPower = Math.min(3.0,getEnergy());
	double myX = getX();
	double myY = getY();
	double absoluteBearing = getHeadingRadians() + e.getBearingRadians();
	double enemyX = getX() + e.getDistance() * Math.sin(absoluteBearing);
	double enemyY = getY() + e.getDistance() * Math.cos(absoluteBearing);
	double enemyHeading = e.getHeadingRadians();
	double enemyHeadingChange = enemyHeading - oldEnemyHeading;
	double enemyVelocity = e.getVelocity();
	oldEnemyHeading = enemyHeading;
	
			
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
