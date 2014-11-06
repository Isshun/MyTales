package org.smallbox.tales.model;

/**
 * Created by Alex on 02/11/2014.
 */
public abstract class CharacterModel {
    protected CharacterModel _target;

    protected double _health;
    protected double _resist;
    protected double _strength;
    protected String _name;

    protected double _maxHealth;

    protected double _maxMana;
    protected double _mana;

    protected int _posX;
    protected int _posY;

    protected double _velocityX;
    protected double _velocityY;

    protected int _maxVelocityX;
    protected int _maxVelocityY;

    public CharacterModel() {
        _maxVelocityX = 10;

        _health = 5;
        _maxHealth = 10;

        _mana = 8;
        _maxMana = 10;
    }

    public String getName() {
        return _name;
    }

    public void setName(String name) {
        _name = name;
    }

    public int getHealthPc() { return (int)(_health / _maxHealth * 100); }
    public double getHealthMax() { return _maxHealth; }
    public double getHealth() { return _health; }

    public int getManaPc() {
        return (int)(_mana / _maxMana * 100);
    }

    public void hit() {
        if (_target != null) {
            _target.hit(this, _strength);
        }
    }

    public void update() {
        onUpdate();
    }

    protected abstract void onUpdate();

    private void hit(CharacterModel foe, double strength) {
        _posX += foe.getPosX() > _posX ? -10 : 10;
        _health -= strength * _resist;
    }

    public void setTarget(CharacterModel target) {
        _target = target;
    }

    public int getPosX() {
        return _posX;
    }
}
