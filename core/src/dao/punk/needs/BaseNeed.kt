package dao.punk.needs

import com.badlogic.gdx.utils.TimeUtils
import com.badlogic.gdx.utils.Timer
import java.util.concurrent.TimeUnit

abstract class BaseNeed(var value: Int, private val intervalSec: Float, private val lastVisitTime: Long) {

    interface OnNeedChangeListener {
        fun onSatisfy()
        fun onRedundant()
        fun onNeedChanged(value: Int)
        fun onNeedEnded()
    }

    abstract val needChangeListener: OnNeedChangeListener

    private lateinit var task: Timer.Task

    private val timer by lazy { Timer().apply { scheduleTask(task, intervalSec, intervalSec) } }

    val max = 100

    init {
        initTask()
        initValue()
    }

    private fun initTask() {
        task = object : Timer.Task() {
            override fun run() {
                value--
                if (value <= 0) {
                    timer.stop()
                    needChangeListener.onNeedEnded()
                } else needChangeListener.onNeedChanged(value)
            }
        }
    }

    private fun initValue() {
        val lostValue = (TimeUnit.MILLISECONDS.toSeconds(TimeUtils.timeSinceMillis(lastVisitTime)) / intervalSec).toInt()
        value -= lostValue
    }

    fun satisfyBy(value: Int): Boolean {
        return if (this.value < max) {
            this.value += value
            needChangeListener.onNeedChanged(this.value)
            needChangeListener.onSatisfy()
            true
        } else {
            needChangeListener.onRedundant()
            false
        }
    }

    fun start() {
        timer.stop()
        timer.start()
    }

    fun restart() {
        value = max
        start()
    }
}