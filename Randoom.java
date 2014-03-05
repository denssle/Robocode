package DE;
import robocode.*;
import java.awt.Color;

/**
 * Randoom - a robot by (your name here)
 */
public class Randoom extends AdvancedRobot
{
	byte moveDir = 1;
	byte scanDir = 3;
	
	public void run() {
		
	setAdjustGunForRobotTurn(true);
	
		while(true) {
			turnRadarRight(380);
			setAhead(350 * moveDir);
			setTurnRight(100);
			changeColor();
			execute();
		}
	}

	public void onScannedRobot(ScannedRobotEvent e)
	{
		changeColor();
		double bearing = e.getBearing();
		double distance = e.getDistance();
		double heading = e.getHeading();		
		
		if(distance < 600)
		{
			fireControl(bearing, distance);
		}
	}
	
	
	public void fireControl(double bearing, double distance)
	{
		double heading = getHeading();
		double gunheading = getGunHeading();
		double x = heading + bearing + gunheading * -1;
		
		if(getGunHeat() == 0 && getEnergy() > 5)
		{
			turnGunRight(x);
			fire(2);
		}
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
