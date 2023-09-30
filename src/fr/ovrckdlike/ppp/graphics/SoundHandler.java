package fr.ovrckdlike.ppp.graphics;


public class SoundHandler {
	private static SoundHandler instance;
	
	private SoundHandler() {
		try {
			// TODO create openal context
		}
		catch (Exception e) {
			
		}
	}
	
	public static SoundHandler get() {
		if (instance == null) instance = new SoundHandler();
		return instance;
	}
	
}
