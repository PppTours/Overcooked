package fr.ovrckdlike.ppp.graphics;


import fr.ovrckdlike.ppp.internal.Sound;

public class SoundHandler {
	private static SoundHandler instance;

	public static Sound walking, dashing;
	public static Sound cutting, cooking, baking, putting, taking, washing;
	public static Sound music;
	public static Sound win, lose;
	public static Sound menu;
	public static Sound click;

	
	private SoundHandler() {
		try {
			loadSounds();
		}
		catch (Exception e) {
			System.out.println("unable to load sounds");
			e.printStackTrace();
		}
	}
	
	public static SoundHandler get() {
		if (instance == null) instance = new SoundHandler();
		return instance;
	}

	public static void play(Sound sound) {
		System.out.println("playing sound" + sound.getName());
		sound.play();
	}

	public static void stop(Sound sound) {
		sound.stop();
	}

	public void loadSounds() {
		walking = new Sound("Walking", "res/sounds/walking.ogg", false);
		/*
		dashing = new Sound("Dashing", "res/sounds/dashing.ogg", false);
		cutting = new Sound("Cutting", "res/sounds/cutting.ogg", false);
		cooking = new Sound("res/sounds/cooking.ogg", false);
		putting = new Sound("res/sounds/putting.ogg", false);
		taking = new Sound("res/sounds/taking.ogg", false);
		washing = new Sound("res/sounds/washing.ogg", false);
		music = new Sound("res/sounds/music.ogg", false);
		win = new Sound("res/sounds/win.ogg", false);
		lose = new Sound("res/sounds/lose.ogg", false);
		menu = new Sound("res/sounds/menu.ogg", false);
		click = new Sound("res/sounds/click.ogg", false);

		 */
	}
}
