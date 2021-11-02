package fr.ovrckdlike.ppp.physics;

public class Time {
	private long currentFrame = System.nanoTime();
	private long lastFrame = currentFrame;
	private int fps = 0;
	private long dt; // en nano seconde
	
	private static Time time = null;
	
	
	public Time() {
		
	}
	
	public static Time get() {
		if (time == null) {time = new Time();}
		return Time.time;
	}
	
	
	public void updateTime() {
		lastFrame = currentFrame;
		currentFrame = System.nanoTime();
		dt = currentFrame - lastFrame;
		fps = (int) ( 1 / (dt / 1E9));
	}
	
	public int getFps() {
		return fps;
	}
	
	public long getDt() {
		return dt;
	}
	

}
