package org.smallbox.tales.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.controllers.ControllerListener;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import org.smallbox.tales.Game;
import org.smallbox.tales.Settings;
import org.smallbox.tales.model.GroundModel;
import org.smallbox.tales.model.ItemModel;
import org.smallbox.tales.model.MapModel;
import org.smallbox.tales.model.TextureModel;
import org.smallbox.tales.model.factory.MapFactory;
import org.smallbox.tales.screen.ui.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alex on 06/11/2014.
 */
public class EditorMapChooserScreen extends BaseScreen {

    @Override
    protected void onUpdate() {

    }

    @Override
    protected void onCreate() {
        int offset = 0;
        for (File mapFile: new File("maps").listFiles()) {
            final MapModel map = MapFactory.fromJSON(mapFile.getName());
            UILabel label = new UILabel(map.getName(), 0, offset++ * 20);
            label.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(int x, int y) {
                    Game.getInstance().loadScreen(new EditorMapScreen(map));
                }
            });
            addUIItem(label);
        }
    }

    @Override
    protected void onRefresh() {

    }

    @Override
    public ControllerListener getControllerListener() {
        return null;
    }
}
