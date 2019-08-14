package com.mygdx.game;

import com.badlogic.gdx.math.Vector2;

public class CollidableObject {
  public Vector2 startCoords, endCoords;

  public CollidableObject(Vector2 startCoords, Vector2 endCoords)
  {
    this.startCoords = startCoords;
    this.endCoords = endCoords;
  }
  public CollidableObject()
  {

  }
}
