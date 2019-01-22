package dao.punk.objects

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.Touchable
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import dao.punk.Punk

abstract class BaseObject(texture: String, x: Float, y: Float,
                          protected val punk: Punk,
                          private val onInteract: () -> Unit) : Actor() {

    private val sprite = Sprite(Texture(texture))

    init {
        sprite.setPosition(x, y)
        setBounds(sprite.x, sprite.y, sprite.width, sprite.height)
        touchable = Touchable.enabled

        addListener(object : ClickListener() {
            override fun touchUp(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int) {
                super.touchUp(event, x, y, pointer, button)
                punk.interact(getX() + width / 2, onInteract)
            }
        })
    }

    override fun draw(batch: Batch?, parentAlpha: Float) {
        super.draw(batch, parentAlpha)
        sprite.draw(batch!!)
    }
}

