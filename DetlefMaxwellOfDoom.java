package DE;
import robocode.*;
import java.awt.Color;

/**
 * DetlefMaxwellOfDoom - a robot by Dominik
 */

public class DetlefMaxwellOfDoom extends Robot
{
	double target; //zur zielverfolgung
	int round;
	
	public void run() 
	{
		setBodyColor(Color.green);
		setGunColor(Color.black);
		setRadarColor(Color.green);
		setBulletColor(Color.yellow);
		setScanColor(Color.red);
		
		target = 360;
		round = 0;
		// Robot main loop
		while(true)
		{
			round++;
			out.println("Round: " + round);
			
			ahead(111);
			turnRadarRight(target);
		}
	}

	public void onScannedRobot(ScannedRobotEvent e)
	{
		double bearing = e.getBearing();
		double distance = e.getDistance();
		if(distance < 350)
		{
			fireControl(bearing, distance);
		}
	}
	
	
	public void fireControl(double bearing, double distance)
	{
		double heading = getHeading();
		double gunheading = getGunHeading();
		double x = heading + bearing + gunheading * -1;
		
		if(getGunHeat() == 0 && gunheading < 0)
		{
			turnGunLeft(x);
		}
		else
		{
			turnGunRight(x);	
		}
		openFire(distance);
	}
	public void openFire(double distance){
		out.println("Enemy under fire!");
		if(distance < 99)
		{
			fire(2);
		}
		else
		{
			fire(1);
		}
	}
	
	public void onHitByBullet(HitByBulletEvent e)
	{
		out.println("We are under fire!");
		ahead(50);
	}
	
	public void onHitWall(HitWallEvent e) 
	{
		double bearing = e.getBearing();
		if(bearing > 0)
		{
			turnRight(bearing);
		}
		else
		{
			turnRight(45);
		}
		ahead(75);
	}	
	
	public void onHitRobot(HitRobotEvent e)
	{
		double bearing = e.getBearing();
		fireControl(bearing, 15);
	}
}
