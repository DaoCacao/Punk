package dao.punk.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.scenes.scene2d.Stage
import dao.punk.AppPreferences
import dao.punk.backgrounds.BaseBackground

abstract class BaseScreen : Stage(), Screen {

    abstract val background: BaseBackground

    private val prefs = AppPreferences()

    protected var punk = prefs.getPunk()

    override fun show() {
    }

    override fun render(delta: Float) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        camera.update()
        batch.projectionMatrix = camera.combined

        act(delta)
        draw()
    }

    override fun resize(width: Int, height: Int) {
        viewport.update(width, height, true)
    }

    override fun pause() {
        prefs.savePunk(punk)
    }

    override fun resume() {
        punk.restore(prefs.getPunk())
    }

    override fun hide() {
    }
}

