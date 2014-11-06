package org.smallbox.tales.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.controllers.ControllerListener;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import org.smallbox.tales.Game;
import org.smallbox.tales.Settings;
import org.smallbox.tales.model.GroundModel;
import org.smallbox.tales.model.ItemModel;
import org.smallbox.tales.model.MapModel;
import org.smallbox.tales.model.TextureModel;
import org.smallbox.tales.model.factory.MapFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alex on 06/11/2014.
 */
public class MapEditorScreen extends BaseScreen {

    private final UITouchModel _canvas;

    private TextureModel _currentTexture;
    private int _currentTextureIndex;
    public ItemModel _currentItem;
    private MapModel _map;
    private List<TextureModel> _textures;
    private int _currentLevel;
    private boolean _levels[] = new boolean[5];

    private List<UITouchModel> _touchItems;
    private List<UITouchModel> _touchGrounds;

    private UIImage _cursor;

    private UILabel[]  _levelLabel = new UILabel[5];
    private UIImage _currentTextureThumbnail;

    public MapEditorScreen() {
        super();

        _cursor = new UIImage(Game.textures.getTexture("cursor.png"), 32, 32);
        addUIItem(_cursor);

        _currentTextureThumbnail = new UIImage(10, 200);
        addUIItem(_currentTextureThumbnail);

        _touchItems = new ArrayList<UITouchModel>();
        _touchGrounds = new ArrayList<UITouchModel>();

        _levels[0] = true;
        _levels[1] = true;
        _levels[2] = true;
        _levels[3] = true;
        _levels[4] = true;

        _canvas = new UITouchModel(64, 64, 400, 400) {
            @Override
            protected void onDraw(SpriteBatch batch) {
                if (_map != null) {
                    for (int z = 0; z < 5; z++) {
                        for (int x = 0; x < 100; x++) {
                            for (int y = 0; y < 100; y++) {
                                if (_map.hasObject(z, x, y)) {
                                    _map.getObject(z, x, y).draw(batch, _x + x * Settings.TILE_SIZE, _y + y * Settings.TILE_SIZE);
                                }
                            }
                        }
                    }
                }
            }
        };
        _canvas.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(int x, int y) {
                _map.setItem(_currentLevel, x / Settings.TILE_SIZE, (_canvas.getHeight() - (y - _canvas.getY())) / Settings.TILE_SIZE, _currentItem);
            }
        });

        _canvas.setOnTouchListener(new OnTouchListener() {
            public int _touchX;
            public int _touchY;

            @Override
            public void onTouchDown(int x, int y, int pointer, int button) {
                _touchX = x;
                _touchY = 600 - y;
            }

            @Override
            public void onTouchUp(int x, int y, int pointer, int button) {
                y = 600 - y;

                for (int i = Math.min(_touchX / Settings.TILE_SIZE, x / Settings.TILE_SIZE); i <= Math.max(_touchX / Settings.TILE_SIZE, x / Settings.TILE_SIZE); i++) {
                    for (int j = Math.min(_touchY / Settings.TILE_SIZE, y / Settings.TILE_SIZE); j <= Math.max(_touchY / Settings.TILE_SIZE, y / Settings.TILE_SIZE); j++) {
                        Gdx.app.log("", "paint: " + i + "x" + j);
                        if (j == Math.max(_touchY / Settings.TILE_SIZE, y / Settings.TILE_SIZE) && i == Math.max(_touchX / Settings.TILE_SIZE, x / Settings.TILE_SIZE)) {
                            _map.setArea(0, i, j, new GroundModel(_currentTexture, GroundModel.TOP_RIGHT));
                        } else if (j == Math.min(_touchY / Settings.TILE_SIZE, y / Settings.TILE_SIZE) && i == Math.min(_touchX / Settings.TILE_SIZE, x / Settings.TILE_SIZE)) {
                            _map.setArea(0, i, j, new GroundModel(_currentTexture, GroundModel.BOTTOM_LEFT));
                        } else if (j == Math.max(_touchY / Settings.TILE_SIZE, y / Settings.TILE_SIZE) && i == Math.min(_touchX / Settings.TILE_SIZE, x / Settings.TILE_SIZE)) {
                            _map.setArea(0, i, j, new GroundModel(_currentTexture, GroundModel.TOP_LEFT));
                        } else if (j == Math.min(_touchY / Settings.TILE_SIZE, y / Settings.TILE_SIZE) && i == Math.max(_touchX / Settings.TILE_SIZE, x / Settings.TILE_SIZE)) {
                            _map.setArea(0, i, j, new GroundModel(_currentTexture, GroundModel.BOTTOM_RIGHT));
                        } else if (j == Math.max(_touchY / Settings.TILE_SIZE, y / Settings.TILE_SIZE)) {
                            _map.setArea(0, i, j, new GroundModel(_currentTexture, GroundModel.TOP));
                        } else if (j == Math.min(_touchY / Settings.TILE_SIZE, y / Settings.TILE_SIZE)) {
                            _map.setArea(0, i, j, new GroundModel(_currentTexture, GroundModel.BOTTOM));
                        } else if (i == Math.max(_touchX / Settings.TILE_SIZE, x / Settings.TILE_SIZE)) {
                            _map.setArea(0, i, j, new GroundModel(_currentTexture, GroundModel.RIGHT));
                        } else if (i == Math.min(_touchX / Settings.TILE_SIZE, x / Settings.TILE_SIZE)) {
                            _map.setArea(0, i, j, new GroundModel(_currentTexture, GroundModel.LEFT));
                        } else {
                            _map.setArea(0, i, j, new GroundModel(_currentTexture, GroundModel.CENTER));
                        }
                    }
                }
            }

            @Override
            public void onClick(UITouchModel item) {

            }
        });

        addUIItem(_canvas);

        _textures = new ArrayList<TextureModel>(Game.textures.getAll());

        setOnKeyListener(new OnKeyListener() {

            @Override
            public void onKeyUp(int keycode) {

                switch (keycode) {
                    case Input.Keys.DOWN:
                        _currentTextureIndex = (_currentTextureIndex + 1) % Game.textures.getAll().size();
                        break;
                    case Input.Keys.UP:
                        if (_currentTextureIndex == 0) {
                            _currentTextureIndex = Game.textures.getAll().size() - 1;
                        }
                        else {
                            _currentTextureIndex = _currentTextureIndex - 1;
                        }
                        break;
                    case Input.Keys.S:
                        MapFactory.toJSON(_map);
                        break;
                    case Input.Keys.N:
                        Gdx.input.getTextInput(new Input.TextInputListener() {
                            @Override
                            public void input (String text) {
                                _map.setName(text);
                            }

                            @Override
                            public void canceled () {
                            }
                        }, "Map name", "");

                        _map = new MapModel();
                        break;
                    case Input.Keys.L:
                        _map = MapFactory.fromJSON("df6a3669-c661-43ad-8316-6accc1b605ac");
                        break;
                }

            }

            @Override
            public void onKeyDown(int keycode) {

            }
        });
    }

    private void refreshItems() {
        removeUIItems(_touchItems);
        _touchItems.clear();
        removeUIItems(_touchGrounds);
        _touchGrounds.clear();

        int offset = 0;
        for (final ItemModel item: Game.items) {
            final UIImage button = new UIImage(item, 0, 60 + (offset * Settings.TILE_SIZE));
            _touchItems.add(button);
            button.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(int x, int y) {
                    _currentItem = item;
                    _cursor.setPosition(button.getX(), button.getY());
                }
            });
            addUIItem(button);
            if (item == _currentItem) {
                Game.font.draw(Game.batch, "x", 16, 600 - 100 - (offset * Settings.TILE_SIZE));
            }
            offset++;
        }
    }

    private void refreshGrounds() {
        removeUIItems(_touchItems);
        _touchItems.clear();
        removeUIItems(_touchGrounds);
        _touchGrounds.clear();

        int offset = 0;
        for (final TextureModel texture: Game.textures.getAll()) {
            final UILabel button = new UILabel(texture.getName(), 0, 60 + (offset * 20));
            _touchItems.add(button);
            button.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(int x, int y) {
                    _currentTexture = texture;
                    _cursor.setPosition(button.getX(), button.getY());
                    _currentTextureThumbnail.setTexture(_currentTexture);
                }
            });
            addUIItem(button);
            offset++;
        }
    }

    @Override
    protected void onUpdate() {
    }

    @Override
    protected void onCreate() {
        drawMaterialPanel();
    }

    @Override
    protected void onRefresh() {
    }

    private void drawMaterialPanel() {
        if (_map != null) {
            if (_map.getName() != null) {
                Game.font.draw(Game.batch, _map.getName(), 0, 600);
            }
        }

        // Levels
        _levelLabel[0] = new UILabel("Level 0 [ ]", 0, 20);
        addUIItem(_levelLabel[0]);

        _levelLabel[1] = new UILabel("Level 1 [ ]", 80, 20);
        _levelLabel[1].setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(int x, int y) {
                _levels[1] = !_levels[1];
                _levelLabel[1].setColor(1);
                _levelLabel[2].setColor(0);
                _levelLabel[3].setColor(0);
                _levelLabel[4].setColor(0);
                _currentLevel = 1;
                refreshGrounds();
            }
        });
        addUIItem(_levelLabel[1]);

        _levelLabel[2] = new UILabel("Level 2 [ ]", 160, 20);
        _levelLabel[2].setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(int x, int y) {
                _levels[2] = !_levels[2];
                _levelLabel[2].setColor(1);
                _levelLabel[3].setColor(0);
                _levelLabel[4].setColor(0);
                _currentLevel = 2;
                refreshItems();
            }
        });
        addUIItem(_levelLabel[2]);

        _levelLabel[3] = new UILabel("Level 3 [ ]", 240, 20);
        _levelLabel[3].setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(int x, int y) {
                _levels[3] = !_levels[3];
                _levelLabel[2].setColor(0);
                _levelLabel[3].setColor(1);
                _levelLabel[4].setColor(0);
                _currentLevel = 3;
                refreshItems();
            }
        });
        addUIItem(_levelLabel[3]);

        _levelLabel[4] = new UILabel("Level 4 [ ]", 320, 20);
        _levelLabel[4].setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(int x, int y) {
                _levels[4] = !_levels[4];
                _levelLabel[2].setColor(0);
                _levelLabel[3].setColor(0);
                _levelLabel[4].setColor(1);
                _currentLevel = 4;
                refreshItems();
            }
        });
        addUIItem(_levelLabel[4]);
    }

    @Override
    public ControllerListener getControllerListener() {
        return null;
    }

    @Override
    public InputProcessor getInputAdapter() {
        return _inputAdapter;
    }
}
