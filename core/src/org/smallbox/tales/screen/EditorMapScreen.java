package org.smallbox.tales.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.controllers.ControllerListener;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import org.smallbox.tales.Game;
import org.smallbox.tales.engine.model.TextureModel;
import org.smallbox.tales.engine.model.TilesetModel;
import org.smallbox.tales.model.GroundItemModel;
import org.smallbox.tales.Settings;
import org.smallbox.tales.model.*;
import org.smallbox.tales.model.factory.MapFactory;
import org.smallbox.tales.engine.ui.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alex on 06/11/2014.
 */
public class EditorMapScreen extends BaseScreen {
    private final UIPreview _preview;
    private final UICursor _previewCursor;
    private final UIImage _tileset;
    private final UICursor _tilesetSelector;

    private static class UIPreview extends UIImage {
        private GroundItemModel _previewGround;
        private ItemModel _previewItem;

        private enum Mode {NONE, GROUND, ITEM}

        private Mode _mode = Mode.NONE;

        public UIPreview(int x, int y) {
            super(x, y, 64, 96);
        }

        public void setGround(GroundItemModel ground) {
            _mode = Mode.GROUND;
            _previewGround = ground;
        }

        public void setGround(ItemModel item) {
            _mode = Mode.ITEM;
            _previewItem = item;
        }

        public void clear() {
            _mode = Mode.NONE;
        }

        @Override
        protected void onDraw(SpriteBatch batch) {
            switch (_mode) {
                case GROUND:
                    batch.draw(_previewGround.getTexture(), _x, Settings.SCREEN_HEIGHT - _height - _y);
                    break;
                case ITEM:
                    _previewItem.drawIcon(batch, _x, Settings.SCREEN_HEIGHT - _height - _y);
                    break;
            }
        }

    }

    private static class UICursor extends UIImage {
        public boolean _isVisible;
        private int _currentItemZ;
        private int _currentItemX;
        private int _currentItemY;
        private ItemModel _currentItem;
        private TilesetAreaModel _currentTilesetArea;

        public UICursor(Texture texture, int x, int y) {
            super(texture, x, y);
        }

        @Override
        public void setPosition(int x, int y) {
            _currentItem = null;
            _isVisible = true;
            super.setPosition(x, y);
        }

        public void clear() {
            _currentItem = null;
            _isVisible = false;
        }

        @Override
        protected void onDraw(SpriteBatch batch) {
            if (_isVisible) {
                super.onDraw(batch);
            }
        }

        public void setTilesetArea(TilesetModel tileset, int x, int y) {
            _currentTilesetArea = new TilesetAreaModel(tileset, x, y, Settings.TILE_SIZE, Settings.TILE_SIZE);
        }

        public void setMapItem(ItemModel item, int z, int x, int y) {
            _currentItem = item;
            _currentItemX = x;
            _currentItemY = y;
            _currentItemZ = z;
        }

        public ItemModel getMapItem() {
            return _currentItem;
        }

        public int getItemZ() {
            return _currentItemZ;
        }

        public int getItemY() {
            return _currentItemY;
        }

        public int getItemX() {
            return _currentItemX;
        }

        public TilesetAreaModel getTilesetArea() {
            return _currentTilesetArea;
        }
    }
    private UIEditorCanvas _canvas;

    private GroundItemModel _currentGround;
    private int _currentTextureIndex;
    public ItemModel _bufferItem;
    private MapModel _map;
    private List<GroundItemModel> _grounds;
    private int _currentLevel;
    private boolean _levels[] = new boolean[5];

    private List<UITouchModel> _touchItems;
    private List<UITouchModel> _touchGrounds;

    private UICursor _cursor;

    private UILabel[]  _levelLabel = new UILabel[6];
    private UILabel _mapName;
    public int _groundIndex;

    public EditorMapScreen(MapModel map) {
        super();

        _map = map;

        _touchItems = new ArrayList<UITouchModel>();
        _touchGrounds = new ArrayList<UITouchModel>();

        _levels[0] = true;
        _levels[1] = true;
        _levels[2] = true;
        _levels[3] = true;
        _levels[4] = true;

        addCanvas();

        _grounds = new ArrayList<GroundItemModel>(Game.resources.grounds);

        setOnKeyListener(new OnKeyListener() {

            @Override
            public void onKeyUp(int keycode) {

                switch (keycode) {
                    case Input.Keys.DOWN:
                        _currentTextureIndex = (_currentTextureIndex + 1) % Game.resources.grounds.size();
                        break;
                    case Input.Keys.UP:
                        if (_currentTextureIndex == 0) {
                            _currentTextureIndex = Game.resources.grounds.size() - 1;
                        } else {
                            _currentTextureIndex = _currentTextureIndex - 1;
                        }
                        break;
                    case Input.Keys.ESCAPE:
                        Game.getInstance().loadScreen(new EditorMapChooserScreen());
                        break;
                    case Input.Keys.FORWARD_DEL:
                        if (_cursor.getMapItem() != null) {
                            _map.setItem(_cursor.getItemZ(), _cursor.getItemX(), _cursor.getItemY(), null);
                            _cursor.clear();
                        }
                        break;
                    case Input.Keys.S:
                        MapFactory.toJSON(_map);
                        break;
                    case Input.Keys.P:
                        Game.getInstance().loadScreen(new GameMapScreen(_map));
                        break;
                    case Input.Keys.N:
                        Gdx.input.getTextInput(new Input.TextInputListener() {
                            @Override
                            public void input(String text) {
                                _map.setName(text);
                            }

                            @Override
                            public void canceled() {
                            }
                        }, "Map name", "");

                        _map = new MapModel();

                        for (int x = 0; x < 100; x++) {
                            for (int y = 0; y < 100; y++) {
                                if (x == 0 || x == 99 || y == 0 || y == 99) {
                                    _map.setItem(2, x, y, Game.getInstance().getItem("4b2b8290-6571-11e4-9803-0800200c9a82"));
                                }
                            }
                        }
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

        _previewCursor = new UICursor(Game.textures.getTexture("cursor.png"), 32, 32);
        _preview = new UIPreview(0, Settings.SCREEN_HEIGHT - 150);
        _preview.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(int x, int y) {
                y = 96-y;
                Gdx.app.log("click", "click " + x + "x" + y);
                if (y > 32 && ((y > 48 && y < 80) || (x > 16 && x < 48))) {

                    // Center
                    if (y > 48 && y < 80 && x > 16 && x < 48) {
                        _groundIndex = 6;
                        _previewCursor.setPosition(_preview.getX() + 16, _preview.getY() + _preview.getHeight() - 48);
                    }

                    // Center left
                    else if (y > 48 && y < 80 && x <= 32) {
                        _groundIndex = 7;
                        _previewCursor.setPosition(_preview.getX(), _preview.getY() + _preview.getHeight() - 48);
                    }

                    // Center right
                    else if (y > 48 && y < 80 && x > 32) {
                        _groundIndex = 8;
                        _previewCursor.setPosition(_preview.getX() + 32, _preview.getY() + _preview.getHeight() - 48);
                    }

                    // Center top
                    else if (x > 16 && x < 48 && y > 32 && y <= 64) {
                        _groundIndex = 9;
                        _previewCursor.setPosition(_preview.getX() + 16, _preview.getY() + _preview.getHeight() - 64);
                    }

                    // Center bottom
                    else if (x > 16 && x < 48 && y > 64) {
                        _groundIndex = 10;
                        _previewCursor.setPosition(_preview.getX() + 16, _preview.getY() + _preview.getHeight() - 32);
                    }
                }

                else {
                    _groundIndex = (y / Settings.TILE_SIZE) * 2 + (x / Settings.TILE_SIZE);
                    Gdx.app.log("click", "_groundIndex " + _groundIndex);
                    x = x / Settings.TILE_SIZE * Settings.TILE_SIZE;
                    y = y / Settings.TILE_SIZE * Settings.TILE_SIZE + Settings.TILE_SIZE;
                    _previewCursor.setPosition(_preview.getX() + x, _preview.getY() + y - Settings.TILE_SIZE);
                }
            }
        });
        addUIItem(_preview);
        addUIItem(_previewCursor);

        final TilesetModel tileset = Game.resources.tilesets.get(0);
        _tilesetSelector = new UICursor(Game.textures.getTexture("cursor.png"), 32, 32);
        _tileset = new UIImage(tileset.getOffsetX(), tileset.getOffsetY(), tileset.getWidth() * Settings.TILE_SIZE, tileset.getHeight() * Settings.TILE_SIZE);
        _tileset.setTexture(tileset.getTexture());
        _tileset.setPosition(0, Settings.SCREEN_HEIGHT - tileset.getHeight() * Settings.TILE_SIZE);
        _tileset.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(int x, int y) {
                x = x / Settings.TILE_SIZE * Settings.TILE_SIZE + tileset.getOffsetX();
                y = ((y / Settings.TILE_SIZE) + 1 ) * Settings.TILE_SIZE;
                _tilesetSelector.setTilesetArea(tileset, x, (_tileset.getHeight() - y) + tileset.getOffsetY());
                _tilesetSelector.setPosition(_tileset.getX() + x, _tileset.getY() + (_tileset.getHeight() - y) - tileset.getOffsetY());
            }
        });
        addUIItem(_tileset);
        addUIItem(_tilesetSelector);

        _cursor = new UICursor(Game.textures.getTexture("cursor.png"), 32, 32);
        addUIItem(_cursor);

    }

    private void addCanvas() {
        _canvas = new UIEditorCanvas(_map);
        _canvas.setPosition(128, 64);

        _canvas.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(int x, int y) {
                int itemX = x / Settings.TILE_SIZE;
                int itemY = y / Settings.TILE_SIZE;
                MapObjectModel item = _map.getObject(_currentLevel, itemX, itemY);
                if (item != null && item instanceof ItemModel) {
                    _cursor.setPosition(
                            _canvas.getX() + (itemX * Settings.TILE_SIZE),
                            _canvas.getY() + (_canvas.getHeight() - (itemY * Settings.TILE_SIZE)) - Settings.TILE_SIZE);
                    _cursor.setMapItem((ItemModel)item, _currentLevel, itemX, itemY);
                    _bufferItem = null;
                } else {
                    //_map.setItem(_currentLevel, x / Settings.TILE_SIZE, y / Settings.TILE_SIZE, _bufferItem);

                    _map.setArea(_currentLevel, x / Settings.TILE_SIZE, y / Settings.TILE_SIZE, _tilesetSelector.getTilesetArea());
                }
            }
        });

        _canvas.setOnRightClickListener(new OnClickListener() {
            @Override
            public void onClick(int x, int y) {
                _bufferItem = null;
                _currentGround = null;
                _cursor.clear();
            }
        });

        _canvas.setOnTouchListener(new OnTouchListener() {
            public int _touchX;
            public int _touchY;

            @Override
            public void onTouchDown(int x, int y, int pointer, int button) {
                _touchX = x;
                _touchY = y;
            }

            @Override
            public void onTouchUp(int x, int y, int pointer, int button) {
                // Ground level
                if (_currentLevel == 1) {
                    _canvas.paintGround(_currentGround, _groundIndex,
                            Math.min(_touchX / Settings.TILE_SIZE, x / Settings.TILE_SIZE), Math.max(_touchX / Settings.TILE_SIZE, x / Settings.TILE_SIZE),
                            Math.min(_touchY / Settings.TILE_SIZE, y / Settings.TILE_SIZE), Math.max(_touchY / Settings.TILE_SIZE, y / Settings.TILE_SIZE));
                }

//                // Items level
//                else if (_currentLevel == 1 || _currentLevel == 2) {
//                    _map.setItem(0, x / Settings.TILE_SIZE, y / Settings.TILE_SIZE, _bufferItem);
//                }
            }

            @Override
            public void onClick(UITouchModel item) {

            }
        });

        addUIItem(_canvas);
    }

    private void refreshItems() {
        removeUIItems(_touchItems);
        _touchItems.clear();
        removeUIItems(_touchGrounds);
        _touchGrounds.clear();

        int offset = 0;
        for (final ItemModel item: Game.resources.items) {
            final UIImage button = new UIImage(item, offset / 15 * Settings.TILE_SIZE, 60 + (offset % 15 * Settings.TILE_SIZE));
            _touchItems.add(button);
            button.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(int x, int y) {
                    _bufferItem = item;
                    _cursor.setPosition(button.getX(), button.getY());
                }
            });
            addUIItem(button);
            if (item == _bufferItem) {
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
        for (final GroundItemModel ground: Game.resources.grounds) {
            final UILabel button = new UILabel(ground.getName(), offset / 15 * 80, 60 + (offset % 15 * 20));
            _touchItems.add(button);
            button.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(int x, int y) {
                    _groundIndex = 0;
                    _currentGround = ground;
                    _cursor.setPosition(button.getX(), button.getY());
                    _preview.setGround(_currentGround);
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
        _mapName = new UILabel("", 0, 0);
        addUIItem(_mapName);
        if (_map != null && _map.getName() != null) {
            _mapName.setText(_map.getName());
        }

        // Levels
        _levelLabel[0] = new UILabel("Physic [ ]", 80, 20);
        _levelLabel[0].setPosition(0, 20);
        addUIItem(_levelLabel[0]);

        _levelLabel[1] = new UILabel("Ground [ ]", 80, 20);
        _levelLabel[1].setPosition(80, 20);
        _levelLabel[1].setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(int x, int y) {
                _levels[1] = !_levels[1];
                _levelLabel[1].setColor(1);
                _levelLabel[2].setColor(0);
                _levelLabel[3].setColor(0);
                _levelLabel[4].setColor(0);
                _levelLabel[5].setColor(0);
                _currentLevel = 1;
                refreshGrounds();
            }
        });
        addUIItem(_levelLabel[1]);

        _levelLabel[2] = new UILabel("Item 1 [ ]", 80, 20);
        _levelLabel[2].setPosition(160, 20);
        _levelLabel[2].setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(int x, int y) {
                _levels[2] = !_levels[2];
                _levelLabel[2].setColor(1);
                _levelLabel[3].setColor(0);
                _levelLabel[4].setColor(0);
                _levelLabel[5].setColor(0);
                _currentLevel = 2;
//                refreshItems();
            }
        });
        addUIItem(_levelLabel[2]);

        _levelLabel[3] = new UILabel("Item 2 [ ]", 80, 20);
        _levelLabel[3].setPosition(240, 20);
        _levelLabel[3].setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(int x, int y) {
                _levels[3] = !_levels[3];
                _levelLabel[2].setColor(0);
                _levelLabel[3].setColor(1);
                _levelLabel[4].setColor(0);
                _levelLabel[5].setColor(0);
                _currentLevel = 3;
//                refreshItems();
            }
        });
        addUIItem(_levelLabel[3]);

        _levelLabel[4] = new UILabel("Top [ ]", 80, 20);
        _levelLabel[4].setPosition(320, 20);
        _levelLabel[4].setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(int x, int y) {
                _levels[4] = !_levels[4];
                _levelLabel[2].setColor(0);
                _levelLabel[3].setColor(0);
                _levelLabel[4].setColor(1);
                _levelLabel[5].setColor(0);
                _currentLevel = 4;
                refreshItems();
            }
        });
        addUIItem(_levelLabel[4]);

        _levelLabel[5] = new UILabel("Game [ ]", 80, 20);
        _levelLabel[5].setPosition(400, 20);
        _levelLabel[5].setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(int x, int y) {
                _levels[5] = !_levels[4];
                _levelLabel[2].setColor(0);
                _levelLabel[3].setColor(0);
                _levelLabel[4].setColor(0);
                _levelLabel[5].setColor(1);
                _currentLevel = 5;
                refreshItems();
            }
        });
        addUIItem(_levelLabel[5]);
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
