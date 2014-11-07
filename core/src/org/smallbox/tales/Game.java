package org.smallbox.tales;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerListener;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import org.smallbox.tales.model.ItemModel;
import org.smallbox.tales.model.factory.MapFactory;
import org.smallbox.tales.screen.BaseScreen;
import org.smallbox.tales.screen.EditorMapChooserScreen;
import org.smallbox.tales.screen.EditorMapScreen;
import org.smallbox.tales.screen.GameMapScreen;

import java.util.ArrayList;

public class Game extends ApplicationAdapter {
    private BaseScreen _screen;

	public static SpriteBatch batch;
    public static BitmapFont font;
    public static BitmapFont fontYellow;
	Texture img;
    public static TextureManager textures;
    public static ArrayList<ItemModel> items;
    private static Game self;
    private ControllerListener _currentControllerListener;

    public void loadScreen(BaseScreen screen) {
        _screen = screen;
        if (screen != null) {
            screen.create();
            setController(screen.getControllerListener());
            Gdx.input.setInputProcessor(screen.getInputAdapter());
        }
    }

    public static Game getInstance() {
        return self;
    }

    public void setController(ControllerListener controllerListener) {
        for (Controller controller : Controllers.getControllers()) {
            Gdx.app.log(Settings.TAG, controller.getName());
            if (controllerListener != null) {
                if (_currentControllerListener != null) {
                    controller.removeListener(_currentControllerListener);
                }
                _currentControllerListener = controllerListener;
                controller.addListener(controllerListener);
            }
        }
    }

    public ItemModel getItem(String id) {
        for (ItemModel item: items) {
            if (item.getId().equals(id)) {
                return item;
            }
        }
        return null;
    }

    public static class GroundGdx {
        public static Texture elf = new Texture("resources/grounds/elf.png");
        public static Texture out1 = new Texture("resources/grounds/Outside_A1_real.png");
        public static Texture out2 = new Texture("resources/grounds/Outside_A2_real.png");
    }

    public static GroundGdx ground;

    @Override
	public void create () {
        self = this;
		batch = new SpriteBatch();
        batch.getProjectionMatrix().setToOrtho2D(0, 0, 800, 600, 0, 1);
		img = new Texture("resources/badlogic.jpg");

        items = new ArrayList<ItemModel>();
        items.add(new ItemModel("4b2b8290-6571-11e4-9803-0800200c9a66", "Barril", "container.png", 0, 0, 1, 1));
        items.add(new ItemModel("4b2b8290-6571-11e4-9803-0800200c9a67", "Sac", "container.png", 0, 1, 1, 1));
        items.add(new ItemModel("4b2b8290-6571-11e4-9803-0800200c9a68", "Pot", "container.png", 0, 2, 1, 1));
        items.add(new ItemModel("4b2b8290-6571-11e4-9803-0800200c9a69", "Tree 1", "tree.png", 0, 0, 4, 5));
        items.add(new ItemModel("4b2b8290-6571-11e4-9803-0800200c9a70", "Tree 2", "tree.png", 0, 5, 4, 5));
        items.add(new ItemModel("4b2b8290-6571-11e4-9803-0800200c9a71", "Tree 3", "tree.png", 0, 10, 4, 5));
        items.add(new ItemModel("4b2b8290-6571-11e4-9803-0800200c9a72", "Tree 4", "tree.png", 4, 0, 4, 5));
        items.add(new ItemModel("4b2b8290-6571-11e4-9803-0800200c9a73", "Tree 5", "tree.png", 4, 8, 4, 5));
        items.add(new ItemModel("4b2b8290-6571-11e4-9803-0800200c9a74", "Tree 6", "tree.png", 9, 8, 6, 5));

        items.add(new ItemModel("4b2b8290-6571-11e4-9803-0800200c9a75", "Fleur", "tree.png", 4, 5, 1, 1));
        items.add(new ItemModel("4b2b8290-6571-11e4-9803-0800200c9a76", "Fleur", "tree.png", 4, 6, 1, 1));
        items.add(new ItemModel("4b2b8290-6571-11e4-9803-0800200c9a77", "Fleur", "tree.png", 6, 5, 1, 1));
        items.add(new ItemModel("4b2b8290-6571-11e4-9803-0800200c9a78", "Fleur", "tree.png", 7, 6, 1, 1));
        items.add(new ItemModel("4b2b8290-6571-11e4-9803-0800200c9a79", "Fleur", "tree.png", 8, 11, 1, 1));

        items.add(new ItemModel("4b2b8290-6571-11e4-9803-0800200c9a80", "Champignon", "tree.png", 7, 5, 1, 1));
        items.add(new ItemModel("4b2b8290-6571-11e4-9803-0800200c9a81", "Plante", "tree.png", 5, 6, 1, 1));
        items.add(new ItemModel("4b2b8290-6571-11e4-9803-0800200c9a82", "Block", "tree.png", 6, 6, 1, 1));

        ground = new GroundGdx();
        font = new BitmapFont();
        fontYellow = new BitmapFont();
        fontYellow.setColor(1, 1, 0, 1);

        textures = new TextureManager();

        Gdx.graphics.setContinuousRendering(true);
        Gdx.graphics.requestRendering();

        loadScreen(new EditorMapChooserScreen());
        //loadScreen(new GameMapScreen(MapFactory.fromJSON("d30b499f-a94e-444b-897b-74b03cc84d4d.xml")));

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
