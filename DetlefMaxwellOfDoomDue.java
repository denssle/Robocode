package DE;
import robocode.*;
import java.awt.Color;

/**
 * DetlefMaxwellOfDoom - a robot by Dominik
 */

public class DetlefMaxwellOfDoomDue extends AdvancedRobot
{
	int round;
	byte moveDir;
	byte scanDir;
	
	
	public void run() 
	{
		/**setBodyColor(Color.green);
		setGunColor(Color.black);
		setRadarColor(Color.green);
		setBulletColor(Color.yellow);
		setScanColor(Color.red);
		*/
		
		setAdjustRadarForGunTurn(true);
		setAdjustRadarForRobotTurn(true);
		//setAdjustGunForRobotTurn(true);
		
		moveDir = 1;
		scanDir = 1;
		round = 0;
		// Robot main loop
		
		while(true)
		{
			borderControl();
			
			if (getRadarTurnRemaining() == 0)
			{
            	setTurnRadarRight(360 * scanDir);
       		}
			
			setAhead(5000 * moveDir); 
			execute();
			message("Round: " + round++);
		}
	}

	public void onScannedRobot(ScannedRobotEvent e)
	{
		changeColor();
		double bearing = e.getBearing();
		double distance = e.getDistance();
		double heading = e.getHeading();		//Wozu?!

		if(distance < 320)
		{
			fireControl(bearing, distance);
		}
		
		radarControl(bearing);
		scanDir *= -1;
		
		goBroadside(bearing, distance);
	}
	
	public void goBroadside(double bearing, double distance)
	{
		setTurnRight(bearing + 70);
	}
	public void borderControl()
	{
		double min = 80;
		double maxX = getBattleFieldWidth() - min;
		double maxY = getBattleFieldHeight() - min;
		if((getX() <= min || getY() <= min) ||(getX() >= maxX || getY() >= maxY))
		{
			moveDir *= -1;
			ahead(60*moveDir);
		}
	}
	
	public void fireControl(double bearing, double distance)
	{
		double heading = getHeading();
		double gunheading = getGunHeading();
		
		double x = heading + bearing + (gunheading * -1);
		
		if(getGunHeat() == 0 && getEnergy() > 5)
		{
			turnGunRight(x);
			openFire(distance);
		}
	}
	
	public void openFire(double distance){
		//message("Enemy under fire!");
		short max = 250;
		byte min = 45;
		if(distance > max)
		{
			fire(1.5);
		}
		if((distance >= min) && (distance <= max))
		{
			fire(2.5);
		}
		if(distance <= min)
		{
			fire(Rules.MAX_BULLET_POWER);
		}
	}
	public void radarControl(double bearing)
	{
		double heading = getHeading();
		double radarheading = getRadarHeading();		
		double x = heading + bearing + (radarheading * -1);

		setTurnRadarRight(x);
	}
	public void onHitByBullet(HitByBulletEvent e)
	{
		double bearing = e.getBearing();
		fireControl(bearing, 300);
		fuckYou(e.getName());
	}
	/*
	public void onHitWall(HitWallEvent e) 
	{
		moveDir *= -1;
	}	
	*/
	public void onHitRobot(HitRobotEvent e)
	{
		double bearing = e.getBearing();
		fireControl(bearing, 15);
	}
	public void onWin(WinEvent e)
	{
		for (int i = 0; i < 50; i++)
		{
			setTurnRadarLeft(3222);
			setTurnGunRight(3689);
			setTurnLeft(9222);
			changeColor();
			execute();
		}
	}
	public void fuckYou(String name)
	{
		String[] fuckyou = {"Fuck you ", 
							"I hate you ", 
							"You´r a piece of shit, ",
							"You are worthless trash",
							"Candy Ass ",
							"Hit the road, ",
							"Eat my shorts, ",
							"You've got the brains of a doughnut, ",
							"You´r a randy old bugger, "
							 };
		int rand = (int) (Math.random() * fuckyou.length);
		message(fuckyou[rand]+name+"!!!");
	}
	public void message(String content)
	{
		out.println(getName()+": "+content);
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
