package dao.punk

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.utils.Array
import dao.punk.needs.FoodNeed
import dao.punk.needs.JoyNeed
import dao.punk.needs.SleepNeed


class Punk(lastVisitTime: Long, posX: Float, posY: Float, sleepValue: Int, hungerValue: Int, happinessValue: Int) : Actor() {

    enum class State { Idle, Walking }

    val sleep = SleepNeed(sleepValue, lastVisitTime)
    val hunger = FoodNeed(hungerValue, lastVisitTime)
    val happiness = JoyNeed(happinessValue, lastVisitTime)

    private val idleAtlas = Texture("punk/idle.png")
    private val idleFrames = TextureRegion.split(idleAtlas, idleAtlas.width, idleAtlas.height)[0]
    private val idleAnimation = Animation<TextureRegion>(0.1f, getFrames(idleFrames))

    private val walkLeftAnimation = Animation<TextureRegion>(0.1f, TextureAtlas("punk/walk_left.atlas").regions)
    private val walkRightAnimation = Animation<TextureRegion>(0.1f, TextureAtlas("punk/walk_right.atlas").regions)

    private var currentState = State.Idle

    private var elapsedTime = 0f

    private var flippedRight = true

    private val speed = 100f

    init {
        setBounds(posX, posY, idleAtlas.width.toFloat(), idleAtlas.height.toFloat())

        addListener(object : ClickListener() {
            override fun touchUp(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int) {
                super.touchUp(event, x, y, pointer, button)
                sleep.restart()
                hunger.restart()
                happiness.restart()
            }
        })
    }

    override fun draw(batch: Batch, parentAlpha: Float) {
        super.draw(batch, parentAlpha)

        elapsedTime += Gdx.graphics.deltaTime

        val frame = when (currentState) {
            Punk.State.Idle ->
                idleAnimation.getKeyFrame(elapsedTime, true)
            Punk.State.Walking ->
                if (flippedRight) walkRightAnimation.getKeyFrame(elapsedTime, true)
                else walkLeftAnimation.getKeyFrame(elapsedTime, true)
        }

        batch.draw(frame, x, y)
    }

    fun init() {
        sleep.start()
        hunger.start()
        happiness.start()
    }

    fun move(x: Float, action: () -> Unit = {}) {
        currentState = State.Walking
        val newX = x - width / 2
        val duration = Math.abs(this.x - newX) / speed
        flip(newX)
        clearActions()
        addAction(Actions.sequence(Actions.moveTo(newX, y, duration), Actions.run { currentState = State.Idle; action.invoke() }))
    }

    fun satisfySleep(value: Int) {
        sleep.satisfyBy(value)
    }

    fun satisfyHunger(value: Int) {
        hunger.satisfyBy(value)
    }

    fun satisfyHappiness(value: Int) {
        happiness.satisfyBy(value)
    }

    fun restore(punk: Punk) {
        setPosition(punk.x, punk.y)
        sleep.value = punk.sleep.value
        hunger.value = punk.hunger.value
        happiness.value = punk.happiness.value
    }

    private fun flip(newX: Float) {
        when {
            flippedRight && newX < x -> {
                flippedRight = false
            }
            !flippedRight && newX > x -> {
                flippedRight = true
            }
        }
    }

    private fun getFrames(textures: kotlin.Array<TextureRegion>) = Array<TextureRegion>(textures.size).apply { textures.forEach { add(it) } }
}

