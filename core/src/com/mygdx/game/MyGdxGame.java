package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.List;

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
    img = new Texture("pengu.png");
    box = new BoxObject(new Rectangle(0,0,img.getWidth(), img.getHeight()), img, new Vector2(10, 10));
    box.setSpriteSize(img.getWidth(), img.getHeight());
    objects = new ArrayList<>();
    objects.add(box);
    objects.add(new CollidableObject(new Rectangle(-50,-50,50,height+100)));
    objects.add(new CollidableObject(new Rectangle(-50,height,width + 50,50)));
    objects.add(new CollidableObject(new Rectangle(width ,0,50,height+50)));
    objects.add(new CollidableObject(new Rectangle(-50,-50,width + 100,50)));

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
              Rectangle newBounds1 = new Rectangle(box.bounds.x - box.bounds.getWidth()/1.2f - 1, box.bounds.y - box.bounds.getHeight()/1.2f - 1, box.bounds.getWidth()/1.2f, box.bounds.getHeight()/1.2f);
              BoxObject newBox =
                  new BoxObject(new Rectangle(newBounds1), img,
                      new Vector2(-box.movement.x, -box.movement.y));
              newBox.setSpriteSize(box.sprite.getWidth() / 1.2f, box.sprite.getHeight() / 1.2f);
              objects.add(newBox);

              Rectangle newBounds2 = new Rectangle(box.bounds.x + box.bounds.getWidth()/1.2f + 1, box.bounds.y + box.bounds.getHeight()/1.2f + 1, box.bounds.getWidth()/1.2f, box.bounds.getHeight()/1.2f);
              BoxObject newBox2 =
                  new BoxObject(new Rectangle(newBounds2), img,
                      new Vector2(box.movement.x, box.movement.y));
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

    Gdx.gl.glClearColor(0.17f, 0.85f, 0.68f, 1);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    batch.begin();

    for (int i = 0; i < objects.size(); i++) {
      if (objects.get(i) instanceof BoxObject) {
        ((BoxObject) objects.get(i)).render(batch, objects);
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
