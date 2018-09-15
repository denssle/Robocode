package aquila;

public class EnemyRobot {
	private double heading;
	private double energy;
	private double velocity;
	private boolean alive;
	
	EnemyRobot() {
		this.alive = true;
	}
	
	public void setDead() {
		this.alive = false;
	}
}
