package dao.punk.needs

import com.badlogic.gdx.utils.Timer

abstract class BaseNeed(private var value: Int, private val intervalSec: Float) : Timer.Task() {

    interface OnNeedChangeListener {
        fun onSatisfy()
        fun onRedundant()
        fun onNeedChanged(value: Int)
        fun onNeedEnded()
    }

    abstract val needChangeListener: OnNeedChangeListener

    private val timer by lazy { Timer().apply { scheduleTask(this@BaseNeed, intervalSec, intervalSec) } }
    private val maxValue = 100

    init {
        timer.start()
    }

    override fun run() {
        value--
        if (value <= 0) {
            timer.stop()
            needChangeListener.onNeedEnded()
        } else needChangeListener.onNeedChanged(value)
    }

    fun satisfyBy(value: Int): Boolean {
        return if (this.value < maxValue) {
            this.value += value
            needChangeListener.onNeedChanged(this.value)
            needChangeListener.onSatisfy()
            true
        } else {
            needChangeListener.onRedundant()
            false
        }
    }
}