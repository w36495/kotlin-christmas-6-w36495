package christmas

import christmas.domain.Menu
import christmas.dto.MenuDTO
import christmas.view.InputView
import christmas.view.OutputView

class EventPlanner(private val inputView: InputView, private val outputView: OutputView) {
    fun run() {
        outputView.printIntro()

        val visitDate = getVisitDate()
        val newOrder = processOrder(visitDate)
        val payment = Payment(newOrder)

        val previousPrice = processPayment(payment)
        val present = hasPresent(previousPrice)

        issueDiscountDetail(visitDate, newOrder, present)
        val discount = Discount()
        val discountPrice = processDiscount(discount, visitDate, newOrder, present)
        val discountPriceWithoutPresent = processDiscountWithoutPresent(discount, visitDate, newOrder)

        processFinalPayment(payment, discountPriceWithoutPresent)
        processBadge(discountPrice)

        inputView.closeInput()
    }

    private fun processBadge(discountPrice: Int) {
        outputView.printBadge(Event.Badge.getBadge(discountPrice))
    }

    private fun processOrder(visitDate: Int): List<MenuDTO> {
        val order = Order(getOrderMenu())
        val newOrder = order.createOrder()

        outputView.printEventPreview(visitDate)
        outputView.printOrder(newOrder)

        return newOrder.toList()
    }

    private fun processPayment(payment: Payment): Int {
        val previousPrice = payment.getPreviousOrderPayment()

        outputView.printPreviousPrice(previousPrice.withComma())

        return previousPrice
    }

    private fun processFinalPayment(payment: Payment, discountPrice: Int): Int {
        val previousPrice = payment.getPreviousOrderPayment()
        val finalPrice = payment.getTotalPayment(previousPrice, discountPrice)

        outputView.printFinalPrice(finalPrice)

        return finalPrice
    }

    private fun hasPresent(previousPrice: Int): Boolean {
        val present: Boolean = Discount().canGetPresent(previousPrice)

        outputView.printPresent(present)

        return present
    }

    private fun issueDiscountDetail(visitDate: Int, order: List<MenuDTO>, hasPresent: Boolean) {
        val discount = Discount()

        outputView.printDiscountDetail(discount.issueDiscountDetail(visitDate, order, hasPresent))
    }


    private fun processDiscount(discount: Discount, visitDate: Int, order: List<MenuDTO>, hasPresent: Boolean): Int {
        outputView.printTotalDiscount(discount.getTotalDiscount(visitDate, order, hasPresent))

        return discount.getTotalDiscount(visitDate, order, hasPresent)
    }

    private fun processDiscountWithoutPresent(discount: Discount, visitDate: Int, order: List<MenuDTO>): Int {
        return discount.getTotalDiscountWithoutPresent(visitDate, order)
    }

    private fun getVisitDate(): Int {
        var visitDate = 0

        try {
            outputView.printQuestionVisitDate()
            visitDate = inputView.readVisitDate()
        } catch (exception: IllegalArgumentException) {
            exception.message?.let { outputView.printErrorMessage(it) }
            getVisitDate()
        }

        return visitDate
    }

    private fun getOrderMenu(): Map<String, Int> {
        var newOrder = emptyMap<String, Int>()

        try {
            outputView.printQuestionMenu()
            newOrder = checkMenuDetail(inputView.readOrderMenu())
        } catch (exception: IllegalArgumentException) {
            exception.message?.let { outputView.printErrorMessage(it) }
            getOrderMenu()
        }

        return newOrder
    }

    private fun checkMenuDetail(orderMenu: List<String>): Map<String, Int> {
        val newOrder = mutableMapOf<String, Int>()

        orderMenu.forEach {
            val (foodName, count) = it.trim().split(DELIMITER_DASH)

            require(isInMenu(foodName)) {
                ERROR_MENU_INVALID
            }

            require(count.toIntOrNull() != null) {
                ERROR_MENU_INVALID
            }

            require(newOrder.contains(foodName).not()) {
                ERROR_MENU_INVALID
            }

            newOrder[foodName] = count.toInt()
        }

        return newOrder
    }

    private fun isInMenu(foodName: String): Boolean {
        val menu = Menu.entries.filter { it.foodName == foodName }

        return menu.isNotEmpty()
    }

    companion object {
        private const val DELIMITER_DASH: String = "-"
        private const val ERROR_MENU_INVALID: String = "[ERROR] 유효하지 않은 주문입니다. 다시 입력해 주세요."
    }
}