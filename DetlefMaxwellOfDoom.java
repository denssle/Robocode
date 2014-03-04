package DE;
import robocode.*;
import java.awt.Color;

/**
 * DetlefMaxwellOfDoom - a robot by Dominik
 */

public class DetlefMaxwellOfDoom extends AdvancedRobot
{
	double target; //zur zielverfolgung
	int round;
	byte moveDirection = 1;
	byte scanDirection = 1;
		
	public void run() 
	{
		setBodyColor(Color.green);
		setGunColor(Color.black);
		setRadarColor(Color.green);
		setBulletColor(Color.yellow);
		setScanColor(Color.red);
		
		setAdjustRadarForGunTurn(true);
		setAdjustRadarForRobotTurn(true);
		
		round = 0;
		turnRadarRight(360);
		// Robot main loop
		while(true)
		{
			round++;
			out.println("Round: " + round);
				
			execute();
		}
	}

	public void onScannedRobot(ScannedRobotEvent e)
	{
		double bearing = e.getBearing();
		double distance = e.getDistance();
		double heading = e.getHeading();		
		
		radarControl();
		
		if(distance < 320)
		{
			fireControl(bearing, distance);
		}
		//out.println(heading);
		//out.println(bearing);
		setTurnRight(bearing);
		setAhead(400 * moveDirection);
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
		if(distance < 99)
		{
			fire(2.3);
		}
		else
		{
			fire(1.3);
		}
	}
	
	public void onHitByBullet(HitByBulletEvent e)
	{
		out.println("We are under fire!");
	}
	
	public void onHitWall(HitWallEvent e) 
	{
		moveDirection *= -1;
	}	
	
	public void onHitRobot(HitRobotEvent e)
	{
		double bearing = e.getBearing();
		fireControl(bearing, 15);
		back(59);
		turnRadarRight(360);
	}
	
	public void onWin(WinEvent e) {
		turnRadarRight(36000);
	}
}
