package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;

public class BoxObject {
    Vector2 startCoords, endCoords;
    Sprite sprite;

    public BoxObject(Vector2 startCoords, Texture img) {
        this.startCoords = startCoords;
        sprite = new Sprite(img);
        this.endCoords = new Vector2(startCoords.x + img.getWidth(), startCoords.y + img.getHeight());
    }

    public void addDeltaX(float delta) {
        startCoords.x += delta;
        endCoords.x += delta;
        setSpritePosition();
    }

    public void addDeltaY(float delta) {
        startCoords.y += delta;
        endCoords.y += delta;
        setSpritePosition();
    }

    public void setSpriteSize(float width, float height) {
        sprite.setSize(width, height);
        endCoords = new Vector2(startCoords.x + sprite.getWidth(), startCoords.y + sprite.getHeight());
    }

    private void setSpritePosition() {
        sprite.setPosition(startCoords.x, startCoords.y);
    }

    public boolean isInside(Vector2 clickCoords) {
        return clickCoords.x > startCoords.x && clickCoords.x < endCoords.x && clickCoords.y > startCoords.y && clickCoords.y < endCoords.y;

    }
}
