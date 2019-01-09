package dao.punk

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Camera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener

class Background(path: String, punk: Punk, camera: Camera) : Actor() {

    private val sprite: Sprite = Sprite(Texture(path))

    private var startX: Float = 0f
    private var startY: Float = 0f

    init {
        setBounds(sprite.x, sprite.y, sprite.width, sprite.height)

        addListener(object : ClickListener() {
            override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                startX = x
                startY = y
                return true
            }

            override fun touchDragged(event: InputEvent?, x: Float, y: Float, pointer: Int) {
                super.touchDragged(event, x, y, pointer)
                val boundLeft = camera.viewportWidth / 2
                val boundRight = sprite.boundingRectangle.width - camera.viewportWidth / 2

                val deltaX = (-Gdx.input.deltaX * camera.viewportWidth / Gdx.graphics.width)
                println("$deltaX $width ${camera.viewportWidth} ${Gdx.graphics.width}")

                camera.translate(deltaX, 0f, 0f)
                if (camera.position.x < boundLeft) camera.position.set(boundLeft, camera.position.y, 0f)
                if (camera.position.x > boundRight) camera.position.set(boundRight, camera.position.y, 0f)
            }

            override fun touchUp(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int) {
                super.touchUp(event, x, y, pointer, button)
                if (startX == x && startY == y) punk.move(x)
            }
        })
    }

    override fun draw(batch: Batch?, parentAlpha: Float) {
        super.draw(batch, parentAlpha)
        sprite.draw(batch!!)
    }
}
