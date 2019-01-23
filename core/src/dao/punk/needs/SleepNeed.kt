package dao.punk.needs

class SleepNeed(value: Int, lastVisitTime: Long) : BaseNeed(value, 5f, lastVisitTime) {

    override val needChangeListener = object : OnNeedChangeListener {
        override fun onNeedChanged(value: Int) {
            println("onNeedChanged: Zzz..$value")
        }

        override fun onNeedEnded() {
            println("onNeedEnded: Fuck")
        }

        override fun onSatisfy() {
            println("onSatisfy: Yeah")
        }

        override fun onRedundant() {
            println("onRedundant: No!")
        }
    }
}