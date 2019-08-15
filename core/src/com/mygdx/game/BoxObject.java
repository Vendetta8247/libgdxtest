package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.util.List;

public class BoxObject extends CollidableObject {
    Vector2 movement;
    Sprite sprite;
    boolean isTouched = false;


    public BoxObject(Rectangle bounds, Texture img, Vector2 movement) {

        this.bounds = bounds;
        sprite = new Sprite(img);
        this.movement = movement;
    }

    public void addVector(Vector2 movement, List<CollidableObject> objects) {
        bounds.setX(bounds.getX() + movement.x);
        bounds.setY(bounds.getY() + movement.y);
        calculateMovement(objects);
    }

    public void calculateMovement(List<CollidableObject> objects) {
//    if (endCoords.x >= width || startCoords.x <= 0) {
//      movement = new Vector2(-movement.x, movement.y);
//    }
//    if (endCoords.y >= height || startCoords.y <= 0)
//      movement = new Vector2(movement.x, -movement.y);

        for (CollidableObject collidableObject : objects) {
            if (this.equals(collidableObject)) {
                continue;
            } else {
                Rectangle intersection = new Rectangle();
                if(Intersector.intersectRectangles(bounds, collidableObject.bounds, intersection)) {
                    if (intersection.x > bounds.x) {
                        System.out.println("CYKA 1");
                        movement = new Vector2(-movement.x, movement.y);
                    }
                    //Intersects with right side
                    if (intersection.y > bounds.y) {
                        System.out.println("CYKA 2");
                        movement = new Vector2(movement.x, -movement.y);
                    }
                    //Intersects with top side
                    if (intersection.x + intersection.width < bounds.x + bounds.width) {
                        System.out.println("CYKA 3");
                        movement = new Vector2(-movement.x, movement.y);
                    }
                    //Intersects with left side
                    if (intersection.y + intersection.height < bounds.y + bounds.height) {
                        System.out.println("CYKA 4");
                        movement = new Vector2(movement.x, -movement.y);
                    }
                }
                //Intersects with bottom side

            }
        }

        setSpritePosition();
        //if (startCoords.x <= 0) movement = new Vector2(movement.x, movement.y);
        //if (startCoords.y <= 0) movement = new Vector2(movement.x, -movement.y);

    }

    public void setSpriteSize(float width, float height) {
        sprite.setSize(width, height);
    }

    private void setSpritePosition() {
        sprite.setPosition(bounds.getX(), bounds.getY());
    }

    public boolean isInside(Vector2 clickCoords) {
        return bounds.contains(clickCoords);
    }

    public void render(SpriteBatch batch, List<CollidableObject> objects) {
        addVector(movement, objects);
        sprite.draw(batch);
        isTouched = false;
    }
}
