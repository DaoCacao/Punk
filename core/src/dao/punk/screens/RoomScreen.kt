package dao.punk.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.utils.viewport.FitViewport
import dao.punk.UiTable
import dao.punk.backgrounds.RoomBackground
import dao.punk.listeners.GlobalListener

class RoomScreen : BaseScreen() {

    override val background = RoomBackground()

    private val ui = UiTable(punk)
    private val globalListener: GlobalListener = GlobalListener(background, punk, camera, ui)

    init {
        addActors()
        addListener(globalListener)
        Gdx.input.inputProcessor = this

        viewport = FitViewport(background.height * Gdx.graphics.width / Gdx.graphics.height, background.height, camera)
        punk.init()
    }

    private fun addActors() {
        addActor(background)
        addActor(punk)
        addActor(ui)
    }
}

