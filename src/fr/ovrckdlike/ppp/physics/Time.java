package fr.ovrckdlike.ppp.physics;

public class Time {
	private long currentFrame = System.nanoTime();
	private long lastFrame = currentFrame;
	private int fps = 0;
	private long dt; // en nano seconde
	
	private static Time time = null;
	
	
	/*public Time Time() {
		time = new Time();
		return time;
	}*/
	
	public static Time get() {
		if (time == null) {time = new Time();}
		return Time.time;
	}
	
	public long getCurrentTime() {
		return System.nanoTime();
	}
	public float timeSince(long time) {
		long currentTime = System.nanoTime();
		float timePast = (float)((currentTime - time)/1E9);
		return timePast;
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
	
	public float getDtS() {
		return dt/1E9f;
	}
	
}
