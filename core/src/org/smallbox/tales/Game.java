package org.smallbox.tales;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import org.smallbox.tales.model.ItemModel;
import org.smallbox.tales.screen.BaseScreen;
import org.smallbox.tales.screen.MapEditorScreen;
import org.smallbox.tales.screen.MapScreen;

import java.util.ArrayList;

public class Game extends ApplicationAdapter {
    BaseScreen _screen;

	public static SpriteBatch batch;
    public static BitmapFont font;
    public static BitmapFont fontYellow;
	Texture img;
    public static TextureManager textures;
    public static ArrayList<ItemModel> items;

    public static class GroundGdx {
        public static Texture elf = new Texture("resources/grounds/elf.png");
        public static Texture out1 = new Texture("resources/grounds/Outside_A1_real.png");
        public static Texture out2 = new Texture("resources/grounds/Outside_A2_real.png");
    }

    public static GroundGdx ground;

    @Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("resources/badlogic.jpg");

        items = new ArrayList<ItemModel>();
        items.add(new ItemModel("4b2b8290-6571-11e4-9803-0800200c9a66", "Barrel", "container.png", 0, 0, 1, 1));
        items.add(new ItemModel("4b2b8290-6571-11e4-9803-0800200c9a67", "Bag", "container.png", 0, 1, 1, 1));
        items.add(new ItemModel("4b2b8290-6571-11e4-9803-0800200c9a68", "Pot", "container.png", 0, 2, 1, 1));

        ground = new GroundGdx();
        font = new BitmapFont();
        fontYellow = new BitmapFont();
        fontYellow.setColor(1, 1, 0, 1);

        textures = new TextureManager();

        Gdx.graphics.setContinuousRendering(true);
        Gdx.graphics.requestRendering();

        _screen = new MapEditorScreen();

        for (Controller controller : Controllers.getControllers()) {
            Gdx.app.log("mytales", controller.getName());

            controller.addListener(_screen.getControllerListener());
        }

        Gdx.input.setInputProcessor(_screen.getInputAdapter());

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    Game.this.update();
                    try { Thread.sleep(10); }
                    catch (InterruptedException e) { e.printStackTrace(); }
                }
            }
        }).start();
	}

    private void update() {
        _screen.update();
    }

    @Override
	public void render () {
		Gdx.gl.glClearColor(0.5f, 0.8f, 0.8f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		//batch.draw(img, 0, 0);

        _screen.render(0);

		batch.end();
	}
}
