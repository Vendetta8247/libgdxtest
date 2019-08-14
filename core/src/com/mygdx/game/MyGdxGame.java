package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

public class MyGdxGame extends ApplicationAdapter {
    SpriteBatch batch;
    Texture img;
    BoxObject box;
    List<BoxObject> objects;
    float delta = 10;
    Operation opX = Operation.plus, opY = Operation.plus;

    Vector2 touchCoords = new Vector2(0, 0);

    enum Operation {
        plus, minus
    }

    @Override
    public void create() {
        batch = new SpriteBatch();
        img = new Texture("badlogic.jpg");
        box = new BoxObject(new Vector2(0, 0), img);
        box.setSpriteSize(img.getWidth(), img.getHeight());
        objects = new ArrayList<>();
        objects.add(box);
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                System.out.println("Touch " + screenX + ":" + (Gdx.graphics.getHeight() - screenY) + " " + pointer + " " + button);
                touchCoords = new Vector2(screenX, Gdx.graphics.getHeight() - screenY);
                return true;
            }
        });
    }

    @Override
    public void render() {
        int width = Gdx.graphics.getWidth();
        int height = Gdx.graphics.getHeight();

        if (touchCoords.x != 0 && touchCoords.y != 0) {
         for(BoxObject box : objects)
         {
             if(box.isInside(touchCoords))
             {
                 System.out.println("INSIDE");
//                 objects.add(new BoxObject(new Vector2(box.startCoords.x-img.getWidth()/2,box.startCoords.y-img.getHeight()/2)));
//                 objects.remove(box);
             }
         }
        }

        if (box.endCoords.x >= width) opX = Operation.minus;
        else if (box.startCoords.x <= 0) opX = Operation.plus;

        if (box.startCoords.y <= 0) opY = Operation.plus;
        else if (box.endCoords.y >= height) opY = Operation.minus;

        if (opX == Operation.plus) {
            box.addDeltaX(delta);
        } else
            box.addDeltaX(-delta);

        if (opY == Operation.plus)
            box.addDeltaY(delta);
        else
            box.addDeltaY(-delta);

        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();

        for(BoxObject box : objects) {
            box.sprite.draw(batch);
//            batch.draw(box.sprite, box.startCoords.x, box.startCoords.y);
        }
        batch.end();
        touchCoords.x = 0; touchCoords.y = 0;
    }

    @Override
    public void dispose() {
        batch.dispose();
        img.dispose();
    }
}
