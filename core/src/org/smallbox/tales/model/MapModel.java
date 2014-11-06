package org.smallbox.tales.model;

import com.badlogic.gdx.Gdx;

import java.util.UUID;

/**
 * Created by Alex on 06/11/2014.
 */
public class MapModel {
    private MapObjectModel[][][] _areas;
    private String _name;
    private String _id;

    public MapModel() {
        _id = UUID.randomUUID().toString();
        _areas = new MapObjectModel[5][100][100];
    }

    public MapModel(String id) {
        _id = id;
        _areas = new MapObjectModel[5][100][100];
    }

    public void setArea(int z, int x, int y, AreaModel area) {
        Gdx.app.log("", "Set area: " + x + "x" + y + " with: " + area.getName());
        _areas[z][x][y] = area;
    }

    public AreaModel getArea(int z, int x, int y) {
        if (!(_areas[z][x][y] instanceof AreaModel)) {
            return null;
        }
        return (AreaModel)_areas[z][x][y];
    }

    public void setName(String name) {
        _name = name;
    }

    public String getName() {
        return _name;
    }

    public String getId() {
        return _id;
    }

    public void setItem(int z, int x, int y, ItemModel item) {
        Gdx.app.log("", "Set item to " + z + "x" + x + "x" + y);
        _areas[z][x][y] = item;
    }

    public ItemModel getItem(int z, int x, int y) {
        return (ItemModel)_areas[z][x][y];
    }

    public void addItem(int z, int x, int y, ItemModel item) {
        _areas[z][x][y] = item;
    }

    public boolean hasObject(int z, int x, int y) {
        return _areas[z][x][y] != null;
    }

    public MapObjectModel getObject(int z, int x, int y) {
        return _areas[z][x][y];
    }
}
