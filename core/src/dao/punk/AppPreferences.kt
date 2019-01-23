package dao.punk

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.utils.TimeUtils
import dao.punk.Punk

class AppPreferences {

    private val preferences by lazy { Gdx.app.getPreferences("prefs") }

    private val lastVisitTimeKey = "lastVisitTime"
    private val posXKey = "posX"
    private val posYKey = "posY"
    private val sleepNeedKey = "sleepNeed"
    private val foodNeedKey = "foodNeed"
    private val joyNeedKey = "joyNeed"

    fun savePunk(punk: Punk) {
        preferences
            .putLong(lastVisitTimeKey, TimeUtils.millis())
            .putFloat(posXKey, punk.x)
            .putFloat(posYKey, punk.y)
            .putInteger(sleepNeedKey, punk.sleep.value)
            .putInteger(foodNeedKey, punk.hunger.value)
            .putInteger(joyNeedKey, punk.happiness.value)
            .flush()
    }

    fun getPunk(): Punk = Punk(
        preferences.getLong(lastVisitTimeKey, TimeUtils.millis()),
        preferences.getFloat(posXKey, 0f),
        preferences.getFloat(posYKey, 0f),
        preferences.getInteger(sleepNeedKey, 100),
        preferences.getInteger(foodNeedKey, 100),
        preferences.getInteger(joyNeedKey, 100))
}