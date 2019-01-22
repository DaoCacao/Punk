package dao.punk

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.utils.viewport.FitViewport
import dao.punk.backgrounds.RoomBackground
import dao.punk.objects.BedObject

class RoomScreen : BaseScreen() {

    private val punk = Punk()
    private val bed = BedObject(250f, 0f, punk)

    override val background = RoomBackground(punk, camera)

    init {
        addActors()
        Gdx.input.inputProcessor = this

        viewport = FitViewport(background.height * Gdx.graphics.width / Gdx.graphics.height, background.height, camera)
    }

    private fun addActors() {
        addActor(background)
        addActor(bed)
        addActor(punk)
    }
}
