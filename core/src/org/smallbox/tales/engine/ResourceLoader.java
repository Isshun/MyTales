package org.smallbox.tales.engine;

import com.badlogic.gdx.Gdx;
import org.smallbox.tales.Game;
import org.smallbox.tales.engine.model.ResourceDataModel;
import org.smallbox.tales.engine.model.TilesetModel;
import org.smallbox.tales.model.GroundItemModel;
import org.smallbox.tales.model.ItemModel;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alex on 25/11/2014.
 */
public class ResourceLoader {
    private static class ItemYamlModel {
        public String   inherits;
        public String   filename;
        public String   name;
        public int[]    blend;
        public int[]    size;
        public int[]    offset;
        public String[] tag;
    }

    public static void init() {
        Game.resources = new ResourceDataModel();

        // Load items
        for (ItemYamlModel item : loadRepository("resources/items/outside/")) {
            Gdx.app.log("yaml", "read: " + item.name);
            Game.resources.items.add(new ItemModel("resources.items.outside." + item.filename, "items/outside/", item.name, item.filename + ".png", item.size[0], item.size[1]));
        }

        // Load tiles sets
        for (ItemYamlModel item : loadRepository("resources/tilesets/")) {
            Gdx.app.log("yaml", "read: " + item.name);
            Game.resources.tilesets.add(new TilesetModel("resources.tilesets." + item.filename, "tilesets/", item.name, item.filename + ".png", item.size[0], item.size[1], item.offset[0], item.offset[1]));
        }

        // Load grounds
        for (ItemYamlModel item : loadRepository("resources/grounds/")) {
            Gdx.app.log("yaml", "read: " + item.name);
            Game.resources.grounds.add(new GroundItemModel("resources.grounds." + item.filename, item.name, item.filename + ".png"));
        }
    }

    private static List<ItemYamlModel> loadRepository(String repositoryName) {
        List<ItemYamlModel> inherits = new ArrayList<ItemYamlModel>();
        List<ItemYamlModel> done = new ArrayList<ItemYamlModel>();
        File repo = new File(repositoryName);
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
                    inherits.add(item);
                } else {
                    done.add(item);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        resolveInherits(done, inherits);

        return done;
    }

    private static void resolveInherits(List<ItemYamlModel> done, List<ItemYamlModel> inherits) {
        List<ItemYamlModel> toRemove = new ArrayList<ItemYamlModel>();
        do {
            toRemove.clear();
            for (ItemYamlModel i: inherits) {
                ItemYamlModel b = getBaseItem(done, i);
                if (b != null) {
                    if (i.name == null) i.name = b.name;
                    if (i.size == null) i.size = b.size;
                    if (i.tag == null) i.tag = b.tag;

                    toRemove.add(i);
                    done.add(i);
                }
            }
            inherits.removeAll(toRemove);
        } while (!toRemove.isEmpty());
    }

    private static ItemYamlModel getBaseItem(List<ItemYamlModel> done, ItemYamlModel i) {
        for (ItemYamlModel d: done) {
            if (d.filename.equals(i.inherits)) {
                return d;
            }
        }
        return null;
    }

}
