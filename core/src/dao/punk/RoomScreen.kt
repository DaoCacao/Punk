package dao.punk

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.utils.viewport.FitViewport

class RoomScreen : BaseScreen() {

    private val punk = Punk()

    override val background = Background("room.png", punk, camera)

    init {
        addActors()
        Gdx.input.inputProcessor = this

        viewport = FitViewport(background.height * Gdx.graphics.width / Gdx.graphics.height, background.height, camera)
    }

    private fun addActors() {
        addActor(background)
        addActor(punk)
    }
}
