package aquila;
import robocode.*;
import java.awt.Color;

/**
 * Borsten - a robot by Dome
 * Ideen: Bullets verfolgen; eigene und feindliche
 * Zirkeln
 */
public class Borsten2 extends AdvancedRobot
{
	//für Radar- und Fahrtrichtungswechsel
	byte dirRadar = 1;
	byte dirDrive = 1;
	
	public void run() 
	{
		//setAdjustRadarForGunTurn(true);
		while(true) 
		{
			turnRadarLeft(90 * dirRadar);
			//setTurnLeft(90);
			setAhead(45 * dirDrive);

			execute();
		}
	}
	
	public void onScannedRobot(ScannedRobotEvent e) 
	{
		dirRadar *= -1;
		//get bearing of scanned robot -180 to 180
		double bearing = e.getBearing();
		double distance = e.getDistance();
		//System.out.println("Distanz: "+distance);
		//get heading of scanned robot 0 -360
		//double heading = e.getHeading();
		//get energy of scanned robot		
		//double energy = e.getEnergy();
		//String name = e.getName();
		//double velocity = e.getVelocity();
		System.out.println("Entfernung: "+distance);
		if(distance < 440)
		{
			fireControl(bearing, distance);
		}
		setTurnRight(bearing + 10);
	}
	
	public void fireControl(double bearing, double distance)
	{
		double heading = getHeading();
		//Returns direction the gun is facing, in degrees. The value will be between 0 and 360
		double gunheading = getGunHeading();
		
		double x = heading + bearing + (gunheading * -1);
		System.out.println("Entfernung: "+distance + "  X: "+x+"\n");
		
		if(x >= 360)
		{
			x -= 360;
		}
		else if(x <= -360)
		{
			x += 360;
		}
		
		if(getGunHeat() == 0 && getEnergy() > 5)
		{
			turnGunRight(x);
			openFire(distance, x);
		}
	}

	public void openFire(double distance, double x)
	{
		System.out.println("Eröffne das Feuer!");
		short max = 290;
		byte min = 35;
		
		Bullet bullet;
		if(distance <= min)
		{
			bullet = fireBullet(Rules.MAX_BULLET_POWER);
		}
		if((distance > min) && (distance <= max))
		{
			bullet = fireBullet(Rules.MAX_BULLET_POWER/2);
		}
		bullet = fireBullet(Rules.MAX_BULLET_POWER/3);
	}
	
	public void onHitByBullet(HitByBulletEvent e) 
	{
		double bearing = e.getBearing();
		Bullet bulltet = e.getBullet();
		String name = e.getName();
		double heading = e.getHeading();
		
		
		fuckYou(e.getName());
	}
	
	public void onHitRobot(HitRobotEvent e)
	{
		double energy = e.getEnergy();
		boolean myfault = e.isMyFault();
		
		if(myfault == true && energy > getEnergy())
		{
			setAhead(102);
		}
		else
		{
			setBack(99);;
		}
		
		fireControl(e.getBearing(), 22.2);
		
		fuckYou(e.getName());
	}

	public void onHitWall(HitWallEvent e) 
	{
		dirDrive *= -1;
	}	
	public void onRobotDeath(RobotDeathEvent e)
	{
		// other robot dies...
		fuckYou(e.getName());
	}
	public void onWinEvent()
	{
		//party hard!
	}
	public void onRoundEnded(RoundEndedEvent event)
	{
	       System.out.println("Runde vorbei!");
	  }
	public void fuckYou(String name)
	{
		String[] fuckyou = {"Fuck you ", 
							"I hate you ", 
							"You´re a piece of shit, ",
							"You are worthless trash ",
							"Candy Ass ",
							"Hit the road, ",
							"Eat my shorts, ",
							"You've got the brains of a doughnut, ",
							"You´re a randy old bugger, "
							 };
		int rand = (int) (Math.random() * fuckyou.length);
		System.out.println(fuckyou[rand]+name+"!");
	}
}
