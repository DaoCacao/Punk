package dao.punk

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.ui.Value
import dao.punk.Punk

class UiTable(private val punk: Punk) : Table(Skin(Gdx.files.internal("ui/uiskin.json"))) {

    val sleepProgress by lazy { ProgressBar(0f, punk.sleep.max.toFloat(), 1f, false, skin) }
    val hungerProgress by lazy { ProgressBar(0f, punk.hunger.max.toFloat(), 1f, false, skin) }
    val happinessProgress by lazy { ProgressBar(0f, punk.happiness.max.toFloat(), 1f, false, skin) }

    init {
        setFillParent(true)

        top()

        defaults().width(Value.percentWidth(0.3333f, this))

        add(sleepProgress)
        add(hungerProgress)
        add(happinessProgress)

        debug = true
    }
}