package com.mygdx.game

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputAdapter
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2

import java.util.ArrayList

class MyGdxGame : ApplicationAdapter() {
  lateinit var batch: SpriteBatch
  lateinit var img: Texture
  lateinit var player: BoxObject
  internal var objects: MutableList<CollidableObject> = mutableListOf()
  val MOVEMENT_SPEED = 5
  internal var width: Int = 0
  internal var height: Int = 0
  var keysPressed = mutableMapOf(19 to false, 20 to false, 21 to false, 22 to false)

  internal var touchCoords = Vector2(0f, 0f)

  override fun create() {
    batch = SpriteBatch()
    img = Texture("pengu.png")
    width = Gdx.graphics.width
    height = Gdx.graphics.height

    player = BoxObject(
        Rectangle(0f,0f, img.width.toFloat(), img.height.toFloat()), img, Vector2(10f, 10f)
    )
    player.setSpriteSize(img.width.toFloat(), img.height.toFloat())

    objects.add(player)

    objects.add(CollidableObject(Rectangle(-500f, -500f, 500f, (height + 500).toFloat())))
    objects.add(CollidableObject(Rectangle(-500f, height.toFloat(), (width + 500).toFloat(), 500f)))
    objects.add(CollidableObject(Rectangle(width.toFloat(), 0f, 500f, (height + 500).toFloat())))
    objects.add(CollidableObject(Rectangle(-500f, -500f, (width + 500).toFloat(), 500f)))

    Gdx.input.inputProcessor = object : InputAdapter() {
      override fun keyDown(keycode: Int): Boolean {
        keysPressed[keycode] = true
        println(keycode)

        return super.keyDown(keycode)
      }

      override fun keyUp(keycode: Int): Boolean {
        keysPressed[keycode] = false
        return super.keyUp(keycode)
      }

      override fun touchDown(
        screenX: Int,
        screenY: Int,
        pointer: Int,
        button: Int
      ): Boolean {

        touchCoords = Vector2(screenX.toFloat(), (Gdx.graphics.height - screenY).toFloat())

        if (touchCoords.x != 0f && touchCoords.y != 0f) {
          for (i in objects.indices) {
            val box: BoxObject
            if (objects[i] is BoxObject) {
              box = objects[i] as BoxObject
            } else {
              continue
            }
            if (box.isInside(touchCoords) && !box.isTouched) {
              val newBounds1 = Rectangle(
                  box.bounds.x +box.movement.x,
                  box.bounds.y +box.movement.y, box.bounds.getWidth() / 1.2f,
                  box.bounds.getHeight() / 1.2f
              )
              val newBox = BoxObject(
                  Rectangle(newBounds1), img,
                  Vector2(-box.movement.x, -box.movement.y)
              )
              newBox.setSpriteSize(box.sprite.width / 1.2f, box.sprite.height / 1.2f)
              objects.add(newBox)

              val newBounds2 = Rectangle(
                  box.bounds.x +box.movement.x,
                  box.bounds.y + +box.movement.y, box.bounds.getWidth() / 1.2f,
                  box.bounds.getHeight() / 1.2f
              )
              val newBox2 = BoxObject(
                  Rectangle(newBounds2), img,
                  Vector2(box.movement.x, box.movement.y)
              )
              newBox2.setSpriteSize(box.sprite.width / 1.2f, box.sprite.height / 1.2f)
              objects.add(newBox2)

              newBox.isTouched = true
              newBox2.isTouched = true
              objects.removeAt(i)
            }
          }
        }
        return true
      }
    }
  }

  override fun render() {

    Gdx.gl.glClearColor(0.17f, 0.85f, 0.68f, 1f)
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
    batch.begin()

    for (pair in keysPressed)
    {
      if(pair.value)
      {
        when(pair.key)
        {
          19, 51 -> player.bounds.y+=MOVEMENT_SPEED
          20, 47 -> player.bounds.y-=MOVEMENT_SPEED
          21, 29 ->player.bounds.x-=MOVEMENT_SPEED
          22, 32 -> player.bounds.x+=MOVEMENT_SPEED
        }

      }
    }

    for (i in objects.indices) {
      if (objects[i] is BoxObject) {
        (objects[i] as BoxObject).render(batch, objects)
      }
    }
    batch.end()
    touchCoords.x = 0f
    touchCoords.y = 0f
  }

  override fun dispose() {
    batch.dispose()
    img.dispose()
  }
}
