package dao.punk.needs

class FoodNeed(value: Int) : BaseNeed(value, 3f) {

    override val needChangeListener = object : OnNeedChangeListener {
        override fun onNeedChanged(value: Int) {
            println("onNeedChanged: uurgh..$value")
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