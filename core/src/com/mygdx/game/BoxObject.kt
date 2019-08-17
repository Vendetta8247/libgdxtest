package com.mygdx.game

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Intersector
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2

import java.awt.geom.Point2D
import java.awt.geom.Point2D.Double

class BoxObject(
  bounds: Rectangle,
  img: Texture,
  internal var movement: Vector2
) : CollidableObject() {
  internal var sprite: Sprite
  internal var isTouched = false

  init {

    this.bounds = bounds
    sprite = Sprite(img)
  }

  fun addVector(
    movement: Vector2,
    objects: List<CollidableObject>
  ) {
    bounds.setX(bounds.getX() + movement.x)
    bounds.setY(bounds.getY() + movement.y)
    calculateMovement(objects)
  }

  fun calculateMovement(objects: List<CollidableObject>) {
    //    if (endCoords.x >= width || startCoords.x <= 0) {
    //      movement = new Vector2(-movement.x, movement.y);
    //    }
    //    if (endCoords.y >= height || startCoords.y <= 0)
    //      movement = new Vector2(movement.x, -movement.y);

    for (collidableObject in objects) {
      if (this == collidableObject) {
        continue
      } else {
        val intersection = Rectangle()
        if (Intersector.intersectRectangles(bounds, collidableObject.bounds, intersection)) {
          //Intersects with right side
          if (intersection.x > bounds.x && intersection.height > 10) {
            movement = Vector2(-movement.x, movement.y)
            bounds.x = collidableObject.bounds.getX() - bounds.width
            break
          }
          //Intersects with top side
          if (intersection.y > bounds.y && intersection.width > 10) {
            movement = Vector2(movement.x, -movement.y)
            bounds.y = collidableObject.bounds.getY() - bounds.height
            break
          }
          //Intersects with left side
          if (intersection.x + intersection.width < bounds.x + bounds.width && intersection.height > 10) {
            movement = Vector2(-movement.x, movement.y)
            bounds.x = collidableObject.bounds.getX() + collidableObject.bounds.width
            break
          }
          //Intersects with bottom side
          if (intersection.y + intersection.height < bounds.y + bounds.height && intersection.width > 10) {
            movement = Vector2(movement.x, -movement.y)
            bounds.y = collidableObject.bounds.getY() + collidableObject.bounds.height
            break
          }

        }
        //Intersects with bottom side

      }
    }

    setSpritePosition()

  }

  fun setSpriteSize(
    width: Float,
    height: Float
  ) {
    sprite.setSize(width, height)
  }

  private fun setSpritePosition() {
    sprite.setPosition(bounds.getX(), bounds.getY())
  }

  fun isInside(clickCoords: Vector2): Boolean {
    return bounds.contains(clickCoords)
  }

  fun render(
    batch: SpriteBatch,
    objects: List<CollidableObject>
  ) {
    addVector(movement, objects)
    sprite.draw(batch)
    isTouched = false
  }
}
