package dao.punk

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.utils.viewport.FitViewport

class RoomScreen : BaseScreen() {

    private val punk = Punk()
    override val background = Background("room_bg.gif", punk, camera)

    private val objects = Group()
    private val object1 = SomeObject(0f, 0f, punk)
    private val object2 = SomeObject(512f, 0f, punk)

    init {
        addActors()
        Gdx.input.inputProcessor = this

        viewport = FitViewport(background.height * Gdx.graphics.width / Gdx.graphics.height, background.height, camera)
    }

    private fun addActors() {
        addActor(background)
        objects.addActor(object1)
        objects.addActor(object2)
        addActor(objects)
        addActor(punk)
    }
}
