package christmas

import christmas.dto.MenuDTO

class Discount {
    fun getTotalDiscount(visitDate: Int, newOrder: List<MenuDTO>, hasPresent: Boolean): Int {
        return getWeekdayDiscount(visitDate, newOrder) +
                getWeekendDiscount(visitDate, newOrder) +
                getChristmasDiscount(visitDate) +
                getSpecialDiscount(visitDate) +
                getPresentDiscount(hasPresent)
    }

    fun getTotalDiscountWithoutPresent(visitDate: Int, newOrder: List<MenuDTO>): Int {
        return getWeekdayDiscount(visitDate, newOrder) +
                getWeekendDiscount(visitDate, newOrder) +
                getChristmasDiscount(visitDate) +
                getSpecialDiscount(visitDate)
    }

    fun issueDiscountDetail(visitDate: Int, order: List<MenuDTO>, hasPresent: Boolean): Map<String, Int> {
        val discount = Discount()
        val discounts = mutableMapOf<String, Int>()

        if (discount.getWeekdayDiscount(visitDate, order) != 0) {
            discounts["평일 할인"] = getWeekdayDiscount(visitDate, order)
        }
        if (discount.getWeekendDiscount(visitDate, order) != 0) {
            discounts["주말 할인"] = getWeekendDiscount(visitDate, order)
        }
        if (discount.getChristmasDiscount(visitDate) != 0) {
            discounts["크리스마스 디데이 할인"] = getChristmasDiscount(visitDate)
        }
        if (discount.getPresentDiscount(hasPresent) != 0) {
            discounts["증정 이벤트"] = getPresentDiscount(hasPresent)
        }
        if (discount.getSpecialDiscount(visitDate) != 0) {
            discounts["특별 할인"] = getSpecialDiscount(visitDate)
        }

        return discounts.toMap()
    }

    fun canGetPresent(preOrderPayment: Int): Boolean = preOrderPayment >= DISCOUNT_PRICE_CONDITION

    private fun getSpecialDiscount(visitDate: Int): Int = Event.Special.getDiscount(visitDate)

    private fun getChristmasDiscount(visitDate: Int): Int = Event.Christmas.getDiscount(visitDate)

    private fun getWeekendDiscount(visitDate: Int, newOrder: List<MenuDTO>): Int =
        Event.Weekend.discount(visitDate, newOrder)

    private fun getWeekdayDiscount(visitDate: Int, newOrder: List<MenuDTO>): Int =
        Event.Weekday.discount(visitDate, newOrder)

    private fun getPresentDiscount(hasPresent: Boolean): Int {
        var discount = 0

        if (hasPresent) {
            discount = Event.Present.getPresentDiscount()
        }

        return discount
    }

    companion object {
        private const val DISCOUNT_PRICE_CONDITION: Int = 120_000
    }
}