package org.smallbox.tales.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alex on 02/11/2014.
 */
public class BattleModel {
    private List<FoeModel> _foes;
    private List<PlayableCharacterModel> _characters;

    public static BattleModel newInstance() {
        return new BattleModel();
    }

    public BattleModel() {
        _foes = new ArrayList<FoeModel>();
        _foes.add(FoeModel.newInstance(FoeModel.Type.FOE_1));
        _foes.add(FoeModel.newInstance(FoeModel.Type.FOE_1));
        _foes.add(FoeModel.newInstance(FoeModel.Type.FOE_1));

        _characters = new ArrayList<PlayableCharacterModel>();
        _characters.add(new PlayableCharacterModel("Cless"));
        _characters.add(new PlayableCharacterModel("Mint"));
    }

    public List<FoeModel> getFoes() {
        return _foes;
    }

    public List<PlayableCharacterModel> getCharacters() {
        return _characters;
    }

    public void update() {
        for (FoeModel foe: _foes) {
            foe.update();
        }

        for (PlayableCharacterModel player: _characters) {
            player.update();
        }
    }
}
