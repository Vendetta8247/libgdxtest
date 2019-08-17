package com.mygdx.game

import com.badlogic.gdx.math.Rectangle

open class CollidableObject {
  lateinit var bounds: Rectangle

  constructor(bounds: Rectangle) {
    this.bounds = bounds
  }

  constructor()
}
