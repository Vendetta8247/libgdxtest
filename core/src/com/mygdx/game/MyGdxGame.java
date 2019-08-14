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
import javax.swing.Box;

public class MyGdxGame extends ApplicationAdapter {
  SpriteBatch batch;
  Texture img;
  BoxObject box;
  List<CollidableObject> objects;
  int width;
  int height;

  Vector2 touchCoords = new Vector2(0, 0);

  @Override public void create() {
    batch = new SpriteBatch();
    width = Gdx.graphics.getWidth();
    height = Gdx.graphics.getHeight();
    img = new Texture("badlogic.jpg");
    box = new BoxObject(new Vector2(0, 0), img, new Vector2(10, 10), width, height);
    box.setSpriteSize(img.getWidth(), img.getHeight());
    objects = new ArrayList<>();
    objects.add(box);
    objects.add(new CollidableObject(new Vector2(-50, -50), new Vector2(-1, height + 50)));
    objects.add(
        new CollidableObject(new Vector2(-50, height + 50), new Vector2(width + 50, height +1)));
    objects.add(new CollidableObject(new Vector2(width + 50, height + 50), new Vector2(width -1, -50)));
    objects.add(new CollidableObject(new Vector2(width+50, -50), new Vector2(-50, - 50)));
    Gdx.input.setInputProcessor(new InputAdapter() {
      @Override public boolean touchDown(int screenX, int screenY, int pointer, int button) {

        touchCoords = new Vector2(screenX, Gdx.graphics.getHeight() - screenY);

        if (touchCoords.x != 0 && touchCoords.y != 0) {
          for (int i = 0; i < objects.size(); i++) {
            BoxObject box;
            if (objects.get(i) instanceof BoxObject) {
              box = (BoxObject) objects.get(i);
            } else {
              continue;
            }
            if (box.isInside(touchCoords) && !box.isTouched) {
              BoxObject newBox =
                  new BoxObject(new Vector2(box.startCoords.x, box.startCoords.y), img,
                      new Vector2(-box.movement.x, -box.movement.y), width, height);
              newBox.setSpriteSize(box.sprite.getWidth() / 1.2f, box.sprite.getHeight() / 1.2f);
              objects.add(newBox);

              BoxObject newBox2 =
                  new BoxObject(new Vector2(box.startCoords.x, box.startCoords.y), img,
                      new Vector2(box.movement.x, box.movement.y), width, height);
              newBox2.setSpriteSize(box.sprite.getWidth() / 1.2f, box.sprite.getHeight() / 1.2f);
              objects.add(newBox2);

              newBox.isTouched = true;
              newBox2.isTouched = true;
              objects.remove(i);
            }
          }
        }
        return true;
      }
    });
  }

  @Override public void render() {

    Gdx.gl.glClearColor(1, 0, 0, 1);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    batch.begin();

    for (int i = 0; i < objects.size(); i++) {
      if (objects.get(i) instanceof BoxObject) {
        ((BoxObject) objects.get(i)).render(batch, width, height, objects);
      }
    }
    batch.end();
    touchCoords.x = 0;
    touchCoords.y = 0;
  }

  @Override public void dispose() {
    batch.dispose();
    img.dispose();
  }
}
