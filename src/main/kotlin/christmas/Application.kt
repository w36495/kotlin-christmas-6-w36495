package christmas

import christmas.view.InputView
import christmas.view.OutputView

fun main() {
    val inputView = InputView()
    val outputView = OutputView()

    EventPlanner(inputView, outputView).run()
}
