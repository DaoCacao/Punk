package dao.punk

import com.badlogic.gdx.Game

class Main : Game() {

    override fun create() {
        setScreen(RoomScreen())
    }
}
