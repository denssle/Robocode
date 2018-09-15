package aquila;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import robocode.*;

public class VugduschCrusher extends AdvancedRobot {
	// HashMap<String, EnemyRobot> enemiesOfTheFreeWorld = new HashMap();
	byte dirDrive = 1;

	public void run() {
		setAdjustRadarForGunTurn(true);

		setGunColor(Color.ORANGE);
		setScanColor(Color.CYAN);
		setRadarColor(Color.CYAN);
		setBodyColor(Color.BLACK);
		setBulletColor(Color.RED);

		int others = getOthers();

		while (true) {
			message("loop" + others);
			setAhead(100 * dirDrive);
			// turnRadarRight(Double.POSITIVE_INFINITY);
			turnRadarRight(360);
		}
	}

	public void onScannedRobot(ScannedRobotEvent event) {
		// updateEnemyRobot(event);
		double bearing = Math.floor(event.getBearing());
		double heading = Math.floor(event.getHeading());
		message(event.getName() + " his heading " + heading + " my gun heading " + Math.floor(getGunHeading())
				+ " velo " + event.getVelocity());

		fireControl(bearing, event.getDistance());
	}

	public void fireControl(double bearing, double distance) {
		double direction = Math.floor(getHeading() + bearing + (getGunHeading() * -1));
		if (direction >= 360 || direction <= -360) {
			direction = 0;
		}
		message("fire dir: " + direction);
		if (getGunHeat() == 0 && getEnergy() > 3) {
			turnGunRight(direction);
			openFire(distance);
		}
	}

	public void openFire(double distance) {
		message("Enemy under fire!");
		short max = 250;
		byte min = 20;
		if (distance > max) {
			fire(1.5);
		}
		if ((distance >= min) && (distance <= max)) {
			fire(2.5);
		}
		if (distance <= min) {
			fire(Rules.MAX_BULLET_POWER);
		}
	}

	public void updateEnemyRobot(ScannedRobotEvent event) {
		EnemyRobot enemy = new EnemyRobot();
		enemiesOfTheFreeWorld.put(event.getName(), enemy);
	}

	// i hit someone
	public void onBulletHit(BulletHitEvent event) {
		// message("onBulletHit");
	}

	// i am the victim
	public void onHitByBullet(HitByBulletEvent event) {
		// message("onHitByBullet");
		// double bearing = event.getBearing();
		// fireControl(bearing, 300);
		fuckYou(event.getName());
	}

	public void onBulletMissed(BulletMissedEvent event) {
		// message("onBulletMissed");
	}

	public void onHitRobot(HitRobotEvent event) {
		// message("onHitRobot");
		if (event.isMyFault() == false) {
			fuckYou(event.getName());
		}
	}

	public void onHitWall(HitWallEvent event) {
		// message("onHitWall");
		dirDrive *= -1;
	}

	public void onWin(WinEvent event) {
		// message("onWin");
	}

	public void onDeath(DeathEvent event) {
		// message("onDeath");
	}

	// This method is called when another robot dies.
	public void onRobotDeath(RobotDeathEvent event) {
		message("onRobotDeath: " + event.getName());
		// enemiesOfTheFreeWorld.get(event.getName()).setDead();
	}

	public void onStatus(StatusEvent event) {
		// message("onStatus");
	}

	public void fuckYou(String name) {
		String[] fuckyou = { "Fuck you ", "I hate you ", "You´r a piece of shit, ", "You are worthless trash",
				"Candy Ass ", "Hit the road, ", "Eat my shorts, ", "You've got the brains of a doughnut, ",
				"You´r a randy old bugger, " };
		int rand = (int) (Math.random() * fuckyou.length);
		message(fuckyou[rand] + name + "!!!");
	}

	public void message(String content) {
		out.println(getName() + ": " + content);
	}
}
