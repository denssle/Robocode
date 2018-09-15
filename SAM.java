package aquila;

import robocode.*;

import java.awt.Color;

public class SAM extends AdvancedRobot {
    byte dirRadar = 1;
    byte dirDrive = 1;

    public void run() {
        while (true) {
            turnRadarRight(45 * dirRadar);
            setAhead(100 * dirDrive);

            execute();
        }
    }

    public void onScannedRobot(ScannedRobotEvent e) {
        dirRadar *= -1;
        double bearing = e.getBearing();
        double distance = e.getDistance();
        //double heading = e.getHeading();

        if (distance < 320) {
            fireControl(bearing, distance);
        }
    }

    public void fireControl(double bearing, double distance) {
        double heading = getHeading();
        double gunheading = getGunHeading();

        double x = heading + bearing + (gunheading * -1);

        if (getGunHeat() == 0 && getEnergy() > 5) {
            turnGunRight(x);
            openFire(distance);
        }
    }

    public void openFire(double distance) {
        //message("Open fire!");
        short max = 250;
        byte min = 35;

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

    public void onHitByBullet(HitByBulletEvent e) {
        fuckYou(e.getName());
    }

    public void onHitRobot(HitRobotEvent e) {
        dirDrive *= -1;
        fuckYou(e.getName());
    }

    public void onHitWall(HitWallEvent e) {
        dirDrive *= -1;
    }

    public void fuckYou(String name) {
        String[] fuckyou = {"Fuck you ",
                "I hate you ",
                "You´re a piece of shit, ",
                "You are worthless trash",
                "Candy Ass ",
                "Hit the road, ",
                "Eat my shorts, ",
                "You've got the brains of a doughnut, ",
                "You´re a randy old bugger, "
        };
        int rand = (int) (Math.random() * fuckyou.length);
        message(fuckyou[rand] + name + "!!!");
    }
}
