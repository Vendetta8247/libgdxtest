package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.util.List;

public class BoxObject extends CollidableObject {
  Vector2 movement;
  Sprite sprite;
  boolean isTouched = false;

  public BoxObject(Vector2 startCoords, Texture img, Vector2 movement, int width, int height) {

    this.endCoords = new Vector2(startCoords.x + img.getWidth(), startCoords.y + img.getHeight());

    if(endCoords.y > height) endCoords.y = height - 1;
    if(startCoords.x < 0) startCoords.x = 1;
    if(endCoords.x > width) endCoords.x = width - 1;
    if(startCoords.y < 0) startCoords.y = 1;

    this.startCoords = startCoords;
    sprite = new Sprite(img);
    this.movement = movement;
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

  public void addVector(Vector2 movement, int width, int height, List<CollidableObject> objects) {
    startCoords.add(movement);
    endCoords.add(movement);
    calculateMovement(width, height, objects);
  }

  public void calculateMovement(int width, int height, List<CollidableObject> objects) {
    //if (endCoords.x >= width || startCoords.x <= 0) {
    //  movement = new Vector2(-movement.x, movement.y);
    //}
    //if (endCoords.y >= height || startCoords.y <= 0)
    //  movement = new Vector2(movement.x, -movement.y);

    for(CollidableObject collidableObject: objects)
    {
      if(this.equals(collidableObject))
      {
        continue;
      }
      else
      {
        if(startCoords.x<= collidableObject.endCoords.x && startCoords.x >= collidableObject.startCoords.x)
        {
          movement = new Vector2(-movement.x, movement.y);
          break;
        }
        if(endCoords.x >= collidableObject.startCoords.x && endCoords.x <=collidableObject.endCoords.x)
        {
          movement = new Vector2(-movement.x, movement.y);
          break;
        }
        if(startCoords.y <= collidableObject.endCoords.y  && startCoords.y >= collidableObject.startCoords.y)
        {
          movement = new Vector2(movement.x, -movement.y);
          break;
        }
        if(endCoords.y >= collidableObject.startCoords.y && endCoords.y <= collidableObject.endCoords.y)
        {
          movement = new Vector2(movement.x, -movement.y);
          break;
        }
      }
    }

    setSpritePosition();
    //if (startCoords.x <= 0) movement = new Vector2(movement.x, movement.y);
    //if (startCoords.y <= 0) movement = new Vector2(movement.x, -movement.y);

  }

  public void setSpriteSize(float width, float height) {
    sprite.setSize(width, height);
    endCoords = new Vector2(startCoords.x + sprite.getWidth(), startCoords.y + sprite.getHeight());
  }

  private void setSpritePosition() {
    sprite.setPosition(startCoords.x, startCoords.y);
  }

  public boolean isInside(Vector2 clickCoords) {
    return clickCoords.x > startCoords.x
        && clickCoords.x < endCoords.x
        && clickCoords.y > startCoords.y
        && clickCoords.y < endCoords.y;
  }

  public void render(SpriteBatch batch, int width, int height, List<CollidableObject> objects) {
    addVector(movement, width, height, objects);
    sprite.draw(batch);
    isTouched = false;
  }
}
