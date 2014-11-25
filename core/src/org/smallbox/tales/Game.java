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
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Game extends ApplicationAdapter {
    private static class ItemYamlModel {
        public String   inherits;
        public String   filename;
        public String   name;
        public int[]    size;
        public String[] tag;
    }

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

        List<ItemYamlModel> inheriths = new ArrayList<ItemYamlModel>();
        List<ItemYamlModel> done = new ArrayList<ItemYamlModel>();
        File repo = new File("resources/items/tree/");
        for (File file: repo.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(".yml");
            }
        })) {
            try {
                InputStream input = new FileInputStream(file);
                Yaml yaml = new Yaml(new Constructor(ItemYamlModel.class));
                ItemYamlModel item = (ItemYamlModel)yaml.load(input);
                item.filename = file.getName().replace(".yml", "");
                if (item.inherits != null) {
                    inheriths.add(item);
                } else {
                    done.add(item);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        resolvInheriths(done, inheriths);

        for (ItemYamlModel item: done) {
            Gdx.app.log("yaml", "read: " + item.name);
            items.add(new ItemModel("resources.items.tree." + item.filename, item.name, item.filename + ".png", item.size[0], item.size[1]));
        }

//        items.add(new ItemModel("4b2b8290-6571-11e4-9803-0800200c9a66", "Barril", "container.png", 0, 0, 1, 1));
//        items.add(new ItemModel("4b2b8290-6571-11e4-9803-0800200c9a67", "Sac", "container.png", 0, 1, 1, 1));
//        items.add(new ItemModel("4b2b8290-6571-11e4-9803-0800200c9a68", "Pot", "container.png", 0, 2, 1, 1));
//        items.add(new ItemModel("4b2b8290-6571-11e4-9803-0800200c9a69", "Tree 1", "tree.png", 0, 0, 4, 5));
//        items.add(new ItemModel("4b2b8290-6571-11e4-9803-0800200c9a70", "Tree 2", "tree.png", 0, 5, 4, 5));
//        items.add(new ItemModel("4b2b8290-6571-11e4-9803-0800200c9a71", "Tree 3", "tree.png", 0, 10, 4, 5));
//        items.add(new ItemModel("4b2b8290-6571-11e4-9803-0800200c9a72", "Tree 4", "tree.png", 4, 0, 4, 5));
//        items.add(new ItemModel("4b2b8290-6571-11e4-9803-0800200c9a73", "Tree 5", "tree.png", 4, 8, 4, 5));
//        items.add(new ItemModel("4b2b8290-6571-11e4-9803-0800200c9a74", "Tree 6", "tree.png", 9, 8, 6, 5));
//
//        items.add(new ItemModel("4b2b8290-6571-11e4-9803-0800200c9a75", "Fleur", "tree.png", 4, 5, 1, 1));
//        items.add(new ItemModel("4b2b8290-6571-11e4-9803-0800200c9a76", "Fleur", "tree.png", 4, 6, 1, 1));
//        items.add(new ItemModel("4b2b8290-6571-11e4-9803-0800200c9a77", "Fleur", "tree.png", 6, 5, 1, 1));
//        items.add(new ItemModel("4b2b8290-6571-11e4-9803-0800200c9a78", "Fleur", "tree.png", 7, 6, 1, 1));
//        items.add(new ItemModel("4b2b8290-6571-11e4-9803-0800200c9a79", "Fleur", "tree.png", 8, 11, 1, 1));
//
//        items.add(new ItemModel("4b2b8290-6571-11e4-9803-0800200c9a80", "Champignon", "tree.png", 7, 5, 1, 1));
//        items.add(new ItemModel("4b2b8290-6571-11e4-9803-0800200c9a81", "Plante", "tree.png", 5, 6, 1, 1));
//        items.add(new ItemModel("4b2b8290-6571-11e4-9803-0800200c9a82", "Block", "tree.png", 6, 6, 1, 1));

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

    private void resolvInheriths(List<ItemYamlModel> done, List<ItemYamlModel> inheriths) {
        List<ItemYamlModel> toRemove = new ArrayList<ItemYamlModel>();
        do {
            toRemove.clear();
            for (ItemYamlModel i: inheriths) {
                ItemYamlModel b = getBaseItem(done, i);
                if (b != null) {
                    if (i.name == null) i.name = b.name;
                    if (i.size == null) i.size = b.size;
                    if (i.tag == null) i.tag = b.tag;

                    toRemove.add(i);
                    done.add(i);
                }
            }
            inheriths.removeAll(toRemove);
        } while (!toRemove.isEmpty());
    }

    private ItemYamlModel getBaseItem(List<ItemYamlModel> done, ItemYamlModel i) {
        for (ItemYamlModel d: done) {
            if (d.filename.equals(i.inherits)) {
                return d;
            }
        }
        return null;
    }

    private void update() {
        _screen.update();
    }

    @Override
	public void render () {
		Gdx.gl.glClearColor(0.5f, 0.8f, 0.8f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glEnable(GL20.GL_BLEND);
		//batch.draw(img, 0, 0);

        _screen.render(0);
	}
}
