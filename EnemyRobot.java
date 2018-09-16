package aquila;

public class EnemyRobot {
	private double heading;
	private double energy;
	private double velocity;
	private boolean alive;
	private double distance;
	
	EnemyRobot() {
		this.alive = true;
	}
	
	public void setDead() {
		this.alive = false;
	}
	
	public void setHeading(double heading) {
		this.heading = heading;
	}
	
	public void setEnergy(double energy) {
		this.energy = energy;
	}
	
	public void setVelocity(double velocity) {
		this.velocity = velocity;
	}
	
	public void setDistance(double distance) {
		this.distance = distance;
	}
	
	public double getHeading() {
		return this.heading;
	}
	
	public double getEnergy() {
		return this.energy;
	}
	
	public double getVelocity() {
		return this.velocity;
	}
	
	public double getDistance() {
		return this.distance;
	}
}
