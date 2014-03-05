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
	byte scanDirection = 2;
	
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
		
		round = 0;
		// Robot main loop
		while(true)
		{
			radarControl();
			setAhead(4500* moveDirection);
			execute();
		}
	}

	public void onScannedRobot(ScannedRobotEvent e)
	{
		changeColor();
		double bearing = e.getBearing();
		double distance = e.getDistance();
		double heading = e.getHeading();		
		
		radarControl();
		
		if(distance < 320)
		{
			fireControl(bearing, distance);
		}

		setTurnRight(bearing);
		setAhead(4500* moveDirection);
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
		byte min = 35;
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
	
	public void onHitByBullet(HitByBulletEvent e)
	{
		fuckYou(e.getName());
		double bearing = e.getBearing();
		fireControl(bearing, 251);
	}
	
	public void onHitWall(HitWallEvent e) 
	{
		moveDirection *= -1;
	}	
	
	public void onHitRobot(HitRobotEvent e)
	{
		double bearing = e.getBearing();
		fireControl(bearing, 15);
		//back(20 * moveDirection);
		setTurnRadarRight(360);
		fireControl(bearing, 15);
		setTurnRight(bearing);
	}
	public void onWin(WinEvent e)
	{
		for (int i = 0; i < 50; i++)
		{
			setTurnRadarLeft(3600);
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
