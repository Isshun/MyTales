package org.smallbox.tales.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import org.smallbox.tales.Settings;
import org.smallbox.tales.engine.ui.OnClickListener;
import org.smallbox.tales.engine.ui.UITouchModel;
import org.smallbox.tales.model.GroundItemModel;
import org.smallbox.tales.model.GroundModel;
import org.smallbox.tales.model.ItemModel;
import org.smallbox.tales.model.MapModel;

/**
 * Created by Alex on 26/11/2014.
 */
public class UIEditorCanvas extends UITouchModel {

    private final MapModel _map;

    public UIEditorCanvas(MapModel map) {
        super(384, 384);
        _map = map;
    }

    @Override
    protected void onDraw(SpriteBatch batch) {
        if (_map != null) {
            for (int z = 0; z < 5; z++) {
                for (int x = 0; x < 384/32; x++) {
                    for (int y = 0; y < 384/32; y++) {
                        if (_map.hasObject(z, x, y)) {
                            _map.getObject(z, x, y).draw(batch, _x + x * Settings.TILE_SIZE, (Settings.SCREEN_HEIGHT - _height - _y) + y * Settings.TILE_SIZE);
                            //_map.getObject(z, x, y).draw(batch, _x + x * Settings.TILE_SIZE, _y + y * Settings.TILE_SIZE);
                        }
                    }
                }
            }
        }
    }

    public void paintGround(GroundItemModel currentGround, int groundIndex, int minX, int maxX, int minY, int maxY) {
        for (int i = minX; i <= maxX; i++) {
            for (int j = minY; j <= maxY; j++) {

                // Paint
                if (groundIndex == 0) {
                    Gdx.app.log("", "paint: " + i + "x" + j);
                    if (j == maxY && i == maxX) {
                        _map.setArea(0, i, j, new GroundModel(currentGround, GroundModel.TOP_RIGHT));
                    } else if (j == minY && i == minX) {
                        _map.setArea(0, i, j, new GroundModel(currentGround, GroundModel.BOTTOM_LEFT));
                    } else if (j == maxY && i == minX) {
                        _map.setArea(0, i, j, new GroundModel(currentGround, GroundModel.TOP_LEFT));
                    } else if (j == minY && i == maxX) {
                        _map.setArea(0, i, j, new GroundModel(currentGround, GroundModel.BOTTOM_RIGHT));
                    } else if (j == maxY) {
                        _map.setArea(0, i, j, new GroundModel(currentGround, GroundModel.TOP));
                    } else if (j == minY) {
                        _map.setArea(0, i, j, new GroundModel(currentGround, GroundModel.BOTTOM));
                    } else if (i == maxX) {
                        _map.setArea(0, i, j, new GroundModel(currentGround, GroundModel.RIGHT));
                    } else if (i == minX) {
                        _map.setArea(0, i, j, new GroundModel(currentGround, GroundModel.LEFT));
                    } else {
                        _map.setArea(0, i, j, new GroundModel(currentGround, GroundModel.CENTER));
                    }
                }

                if (groundIndex == 1) {
                    _map.setArea(0, i, j, new GroundModel(currentGround, GroundModel.CORNER));
                }

                if (groundIndex == 2) {
                    _map.setArea(0, i, j, new GroundModel(currentGround, GroundModel.TOP_LEFT));
                }

                if (groundIndex == 3) {
                    _map.setArea(0, i, j, new GroundModel(currentGround, GroundModel.TOP_RIGHT));
                }

                if (groundIndex == 4) {
                    _map.setArea(0, i, j, new GroundModel(currentGround, GroundModel.BOTTOM_LEFT));
                }

                if (groundIndex == 5) {
                    _map.setArea(0, i, j, new GroundModel(currentGround, GroundModel.BOTTOM_RIGHT));
                }

                if (groundIndex == 6) {
                    _map.setArea(0, i, j, new GroundModel(currentGround, GroundModel.CENTER));
                }

                if (groundIndex == 7) {
                    _map.setArea(0, i, j, new GroundModel(currentGround, GroundModel.LEFT));
                }

                if (groundIndex == 8) {
                    _map.setArea(0, i, j, new GroundModel(currentGround, GroundModel.RIGHT));
                }

                if (groundIndex == 9) {
                    _map.setArea(0, i, j, new GroundModel(currentGround, GroundModel.TOP));
                }

                if (groundIndex == 10) {
                    _map.setArea(0, i, j, new GroundModel(currentGround, GroundModel.BOTTOM));
                }
            }
        }
    }
}
