package DE;
import robocode.*;
import java.awt.Color;

/**
 * DetlefMaxwellOfDoom - a robot by Dominik
 */

public class DMoD extends AdvancedRobot
{
	double target;
	int round;
	byte scanDirection = 2;
	
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
			execute();
		}
	}

	public void onScannedRobot(ScannedRobotEvent e)
	{
		double bearing = e.getBearing();
		double distance = e.getDistance();
		radarControl();
		if(getEnergy() < 20)
		{
			turnRight(bearing);
			ahead(100);
		}
		if(distance < 450)
		{
			fireControl(bearing, distance);
		}
		ahead(52);
	}
	public void radarControl()
	{
		scanDirection *= -1;
		setTurnRadarRight(360 * scanDirection);
	}

	public void fireControl(double bearing, double distance)
	{
		double heading = getHeading();
		double gunheading = getGunHeading();
		double x = heading + bearing + gunheading * -1;
		
		if(getGunHeat() == 0 && getEnergy() > 5)
		{
			turnGunRight(x);
			openFire(distance);
		}
	}
	
	public void openFire(double distance){
		out.println("Enemy under fire!");
		short max = 250;
		byte min = 20;
		if(distance > max)
		{
			fire(1.5);
		}
		if((distance >= min) && (distance <= max))
		{
			fire(2.5);
		}
		if(distance < min)
		{
			fire(Rules.MAX_BULLET_POWER);
		}
	}
	
	public void abstauber()
	{
		
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