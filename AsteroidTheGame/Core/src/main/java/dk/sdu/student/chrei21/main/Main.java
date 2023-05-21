package dk.sdu.student.chrei21.main;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Main {


	public static void main(String[] args) {

		LwjglApplicationConfiguration cfg =
				new LwjglApplicationConfiguration();
		cfg.title = "Asteroids";
		cfg.width = 1500;
		cfg.height = 1000;
		cfg.useGL30 = false;
		cfg.resizable = true;

		new LwjglApplication(new Game(), cfg);

	}
	
}
