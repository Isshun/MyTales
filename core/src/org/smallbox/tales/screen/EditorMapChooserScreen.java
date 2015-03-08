package org.smallbox.tales.screen;

import com.badlogic.gdx.controllers.ControllerListener;
import org.smallbox.tales.Game;
import org.smallbox.tales.model.MapModel;
import org.smallbox.tales.model.factory.MapFactory;
import org.smallbox.tales.engine.ui.*;

import java.io.File;

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
            UILabel label = new UILabel(map.getName(), 100, 22);
            label.setPosition(0, offset++ * 20);
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
