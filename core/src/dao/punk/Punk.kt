package dao.punk

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.utils.Array
import dao.punk.needs.FoodNeed
import dao.punk.needs.JoyNeed
import dao.punk.needs.SleepNeed


class Punk : Actor() {

    enum class State { Idle, Walking }

    private val sleep = SleepNeed(100)
    private val hunger = FoodNeed(100)
    private val happyness = JoyNeed(100)

    private val idleAtlas = Texture("idle.png")
    private val idleFrames = TextureRegion.split(idleAtlas, idleAtlas.width, idleAtlas.height)[0]
    private val idleAnimation = Animation<TextureRegion>(0.1f, getFrames(idleFrames))

    private val walkLeftAnimation = Animation<TextureRegion>(0.1f, TextureAtlas("walk_left.atlas").regions)
    private val walkRightAnimation = Animation<TextureRegion>(0.1f, TextureAtlas("walk_right.atlas").regions)

    private var currentState = State.Idle

    private var elapsedTime = 0f

    private var flippedRight = true

    init {
        setBounds(0f, 0f, idleAtlas.width.toFloat(), idleAtlas.height.toFloat())
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

    fun move(x: Float) {
        currentState = State.Walking
        val newX = x - width / 2
        flip(newX)
        clearActions()
        addAction(Actions.sequence(Actions.moveTo(newX, y, 1f), Actions.run { currentState = State.Idle }))
    }

    fun interact(x: Float, action: () -> Unit) {
        currentState = State.Walking
        val newX = x - width / 2
        flip(newX)
        clearActions()
        addAction(Actions.sequence(Actions.moveTo(newX, y, 1f), Actions.run { currentState = State.Idle; action.invoke() }))
    }

    fun satisfySleep(value: Int) {
        sleep.satisfyBy(value)
    }
    fun satisfyHunger(value: Int) {
        hunger.satisfyBy(value)
    }
    fun satisfyHappyness(value: Int) {
        happyness.satisfyBy(value)
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

