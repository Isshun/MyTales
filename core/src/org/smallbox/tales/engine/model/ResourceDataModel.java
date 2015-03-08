package org.smallbox.tales.engine.model;

import org.smallbox.tales.model.GroundItemModel;
import org.smallbox.tales.model.ItemModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alex on 25/11/2014.
 */
public class ResourceDataModel {

    public final List<ItemModel> items;
    public final List<GroundItemModel> grounds;
    public final List<TilesetModel> tilesets;

    public ResourceDataModel() {
        this.items = new ArrayList<ItemModel>();
        this.tilesets = new ArrayList<TilesetModel>();
        this.grounds = new ArrayList<GroundItemModel>();
    }
}
