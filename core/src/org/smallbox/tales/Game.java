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
import org.smallbox.tales.engine.ResourceLoader;
import org.smallbox.tales.engine.TextureManager;
import org.smallbox.tales.model.ItemModel;
import org.smallbox.tales.engine.model.ResourceDataModel;
import org.smallbox.tales.screen.BaseScreen;
import org.smallbox.tales.screen.EditorMapChooserScreen;

public class Game extends ApplicationAdapter {
    private BaseScreen _screen;

	public static SpriteBatch batch;
    public static BitmapFont font;
    public static BitmapFont fontYellow;
	Texture img;
    public static TextureManager textures;
    private static Game self;
    private static int _frame;
    private ControllerListener _currentControllerListener;
    public static ResourceDataModel resources;

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
        for (ItemModel item: this.resources.items) {
            if (item.getId().equals(id)) {
                return item;
            }
        }
        return null;
    }

    @Override
	public void create () {
        self = this;
		batch = new SpriteBatch();
        batch.getProjectionMatrix().setToOrtho2D(0, 0, 800, 600, 0, 1);
		img = new Texture("resources/badlogic.jpg");

        Game.textures = new TextureManager();

        ResourceLoader.init();

        font = new BitmapFont();
        fontYellow = new BitmapFont();
        fontYellow.setColor(1, 1, 0, 1);

        Gdx.graphics.setVSync(true);
        Gdx.graphics.setContinuousRendering(true);
        Gdx.graphics.requestRendering();

        loadScreen(new EditorMapChooserScreen());
        //loadScreen(new GameMapScreen(MapFactory.fromJSON("d30b499f-a94e-444b-897b-74b03cc84d4d.xml")));

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                while (true) {
//                    Game.this.update();
//                    try { Thread.sleep(10); }
//                    catch (InterruptedException e) { e.printStackTrace(); }
//                }
//            }
//        }).start();
	}

    private void update() {
        _screen.update();
    }

    @Override
	public void render () {
		//Gdx.gl.glClearColor(0.5f, 0.8f, 0.8f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glEnable(GL20.GL_BLEND);
		//batch.draw(img, 0, 0);

        _screen.render(0);

        Game.this.update();
    }
}
