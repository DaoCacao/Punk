package dao.punk

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.utils.Array


class Punk : Actor() {

    enum class State { Idle, Walking }

    private val atlas = Texture("character_atlas.png")
    private val allFrames = TextureRegion.split(atlas, atlas.width / 9, atlas.height / 4)
    private val idleAnimation = Animation<TextureRegion>(0.1f, getFrames(allFrames[0]))
    private val walkLeftAnimation = Animation<TextureRegion>(0.1f, getFrames(allFrames[1]))
    private val walkRightAnimation = Animation<TextureRegion>(0.1f, getFrames(allFrames[2]))

    private var currentState = State.Idle

    private var elapsedTime = 0f

    private var flippedRight = true

    init {
        setBounds(0f, 0f, (atlas.width / 9).toFloat(), (atlas.height / 4).toFloat())
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

    private fun flip(newX: Float) {
        when {
            flippedRight && newX < x -> flippedRight = false
            !flippedRight && newX > x -> flippedRight = true
        }
    }

    private fun getFrames(textures: kotlin.Array<TextureRegion>) = Array<TextureRegion>(textures.size).apply { textures.forEach { add(it) } }
}
