package dao.punk.needs

class JoyNeed(value: Int) : BaseNeed(value, 1f) {

    override val needChangeListener = object : OnNeedChangeListener {
        override fun onNeedChanged(value: Int) {
            println("onNeedChanged: ...$value")
        }

        override fun onNeedEnded() {
            println("onNeedEnded: Im so fucked")
        }

        override fun onSatisfy() {
            println("onSatisfy: HAPPY!")
        }

        override fun onRedundant() {
            println("onRedundant: I want not!")
        }
    }
}