package dao.punk

import com.badlogic.gdx.Game
import dao.punk.screens.RoomScreen

class Main : Game() {

    override fun create() {
        setScreen(RoomScreen())
    }
}
