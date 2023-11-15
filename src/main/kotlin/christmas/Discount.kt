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
        val discounts = mutableMapOf<String, Int>()

        applyWeekdayDiscount(discounts, visitDate, order)
        applyWeekendDiscount(discounts, visitDate, order)
        applyChristmasDiscount(discounts, visitDate)
        applyPresentDiscount(discounts, hasPresent)
        applySpecialDiscount(discounts, visitDate)

        return discounts.toMap()
    }

    fun canGetPresent(preOrderPayment: Int): Boolean = preOrderPayment >= DISCOUNT_PRICE_CONDITION

    private fun applyWeekdayDiscount(discounts: MutableMap<String, Int>, visitDate: Int, order: List<MenuDTO>) {
        if (getWeekdayDiscount(visitDate, order) != 0) {
            discounts[DISCOUNT_TITLE_WEEKDAY] = getWeekdayDiscount(visitDate, order)
        }
    }

    private fun applyWeekendDiscount(discounts: MutableMap<String, Int>, visitDate: Int, order: List<MenuDTO>) {
        if (getWeekendDiscount(visitDate, order) != 0) {
            discounts[DISCOUNT_TITLE_WEEKEND] = getWeekendDiscount(visitDate, order)
        }
    }

    private fun applyChristmasDiscount(discounts: MutableMap<String, Int>, visitDate: Int) {
        if (getChristmasDiscount(visitDate) != 0) {
            discounts[DISCOUNT_TITLE_CHRISTMAS] = getChristmasDiscount(visitDate)
        }
    }

    private fun applyPresentDiscount(discounts: MutableMap<String, Int>, hasPresent: Boolean) {
        if (getPresentDiscount(hasPresent) != 0) {
            discounts[DISCOUNT_TITLE_PRESENT] = getPresentDiscount(hasPresent)
        }
    }

    private fun applySpecialDiscount(discounts: MutableMap<String, Int>, visitDate: Int) {
        if (getSpecialDiscount(visitDate) != 0) {
            discounts[DISCOUNT_TITLE_SPECIAL] = getSpecialDiscount(visitDate)
        }
    }

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

        private const val DISCOUNT_TITLE_WEEKDAY: String = "평일 할인"
        private const val DISCOUNT_TITLE_WEEKEND: String = "주말 할인"
        private const val DISCOUNT_TITLE_CHRISTMAS: String = "크리스마스 디데이 할인"
        private const val DISCOUNT_TITLE_PRESENT: String = "증정 이벤트"
        private const val DISCOUNT_TITLE_SPECIAL: String = "특별 할인"

    }
}