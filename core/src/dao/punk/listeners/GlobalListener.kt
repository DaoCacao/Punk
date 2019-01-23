package dao.punk.listeners

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Camera
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import dao.punk.Punk
import dao.punk.backgrounds.BaseBackground
import dao.punk.UiTable

class GlobalListener(private val background: BaseBackground,
                     private val punk: Punk,
                     private val camera: Camera,
                     private val ui: UiTable) : ClickListener() {

    private var startX: Float = 0f
    private var startY: Float = 0f

    override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
        startX = x
        startY = y
        return true
    }

    override fun touchDragged(event: InputEvent?, x: Float, y: Float, pointer: Int) {
        super.touchDragged(event, x, y, pointer)
        val boundLeft = camera.viewportWidth / 2
        val boundRight = background.sprite.boundingRectangle.width - camera.viewportWidth / 2

        val deltaX = (-Gdx.input.deltaX * camera.viewportWidth / Gdx.graphics.width)

        camera.translate(deltaX, 0f, 0f)
        ui.moveBy(deltaX, 0f)

        if (camera.position.x < boundLeft) {
            camera.position.set(boundLeft, camera.position.y, 0f)
            ui.setPosition(boundLeft - ui.width / 2, 0f)
        }
        if (camera.position.x > boundRight) {
            camera.position.set(boundRight, camera.position.y, 0f)
            ui.setPosition(boundRight - ui.width / 2, 0f)
        }
    }

    override fun touchUp(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int) {
        super.touchUp(event, x, y, pointer, button)
        if (startX == x && startY == y) punk.move(x)
    }
}