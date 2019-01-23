package dao.punk.backgrounds

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.scenes.scene2d.Actor

abstract class BaseBackground(path: String) : Actor() {

    val sprite: Sprite = Sprite(Texture(path))

    init {
        setBounds(sprite.x, sprite.y, sprite.width, sprite.height)
    }

    override fun draw(batch: Batch?, parentAlpha: Float) {
        super.draw(batch, parentAlpha)
        sprite.draw(batch!!)
    }
}

