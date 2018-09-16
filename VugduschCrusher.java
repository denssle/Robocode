package aquila;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import robocode.*;

public class VugduschCrusher extends AdvancedRobot {
	HashMap<String, EnemyRobot> enemiesOfTheFreeWorld = new HashMap();
	byte dirDrive = 1;

	public void run() {
		setAdjustRadarForGunTurn(true);
		setAdjustRadarForRobotTurn(true);

		setGunColor(Color.BLACK);
		setScanColor(Color.WHITE);
		setRadarColor(Color.WHITE);
		setBodyColor(Color.BLACK);
		setBulletColor(Color.YELLOW);

		int others = getOthers();

		while (true) {
			// message("loop" + others);
			setAhead(100 * dirDrive);
			// turnRadarRight(Double.POSITIVE_INFINITY);
			turnRadarRight(360);
		}
	}

	public void onScannedRobot(ScannedRobotEvent event) {
		updateEnemyRobot(event);
		double bearing = Math.round(event.getBearing());
		double heading = Math.round(event.getHeading());

		// message(event.getName() + " his heading " + heading + " my gun heading " +
		// Math.round(getGunHeading()) + " velo " + event.getVelocity());

		fireControl(bearing, event.getDistance(), heading, event.getVelocity());

	}

	public void fireControl(double bearing, double distance, double heading, double velocity) {
		double direction = Math.round(getHeading() + bearing + (getGunHeading() * -1));
		if (direction >= 360 || direction <= -360) {
			direction = 0;
		}
		// message("fire dir: " + direction);
		if (direction < -180) {
			direction = 360 + direction;
		}
		if (direction > 180) {
			direction = -360 + direction;
		}
		message("final fire dir: " + direction);
		if (getGunHeat() == 0 && getEnergy() > 3) {
			turnGunRight(direction);
			openFire(distance);
		}
	}

	public void openFire(double distance) {
		// message("Enemy under fire!");
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
		enemy.setHeading(Math.round(event.getHeading()));
		enemy.setEnergy(Math.round(event.getEnergy()));
		enemy.setVelocity(Math.round(event.getVelocity()));
		enemy.setDistance(event.getDistance());
		enemiesOfTheFreeWorld.put(event.getName(), enemy);
	}

	public EnemyRobot getEnemyByName(String name) {
		if (enemiesOfTheFreeWorld.containsKey(name)) {
			return enemiesOfTheFreeWorld.get(name);
		}
		return null;
	}

	// i hit someone
	public void onBulletHit(BulletHitEvent event) {
		// message("onBulletHit");
	}

	// i am the victim
	public void onHitByBullet(HitByBulletEvent event) {
		message("onHitByBullet");
		double bearing = Math.round(event.getBearing());
		double heading = Math.round(event.getHeading());
		double distance = 300;
		double velocity = 0;
		message("was hit heading: " + heading + " my heading " + Math.round(getHeading()));
		EnemyRobot enemy = getEnemyByName(event.getName());
		if (enemy != null) {
			heading = enemy.getHeading();
			distance = enemy.getDistance();
			velocity = enemy.getVelocity();

		}
		fireControl(bearing, distance, heading, velocity);
		// fuckYou(event.getName());
	}

	public void onBulletMissed(BulletMissedEvent event) {
		// message("onBulletMissed");
	}

	public void onHitRobot(HitRobotEvent event) {
		// message("onHitRobot");
		if (event.isMyFault() == false) {
			// fuckYou(event.getName());
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
		EnemyRobot enemy = getEnemyByName(event.getName());
		if (enemy != null) {
			enemy.setDead();
		}
	}

	public void onStatus(StatusEvent event) {
		// message("onStatus");
	}

	public void fuckYou(String name) {
		String[] fuckyou = { "Fuck you ", "I hate you ", "You'r a piece of shit, ", "You are worthless trash",
				"Candy Ass ", "Hit the road, ", "Eat my shorts, ", "You've got the brains of a doughnut, ",
				"You'r a randy old bugger, " };
		int rand = (int) (Math.random() * fuckyou.length);
		message(fuckyou[rand] + name + "!!!");
	}

	public void message(String content) {
		out.println(getName() + ": " + content);
	}
}
