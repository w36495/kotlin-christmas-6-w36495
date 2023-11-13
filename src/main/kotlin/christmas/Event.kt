package christmas

import christmas.dto.MenuDTO

sealed class Event {
    object Weekday {
        private const val DISCOUNT_CATEGORY: String = "디저트"

        fun discount(visitDate: Int, orders: List<MenuDTO>): Int {
            var discount = 0

            if (isVisitWeekday(visitDate)) discount = DISCOUNT_BASIC * countDessert(orders)

            return discount
        }

        private fun countDessert(orders: List<MenuDTO>): Int {
            val countDessert = findDessert(orders)

            return countDessert.sumOf { it.count }
        }

        private fun findDessert(orders: List<MenuDTO>): List<MenuDTO> =
            orders.filter { it.category == DISCOUNT_CATEGORY }

        private fun isVisitWeekday(visitDate: Int): Boolean = !(visitDate % 7 == 1 || visitDate % 7 == 2)
    }

    object Weekend {
        private const val DISCOUNT_CATEGORY: String = "메인"

        fun discount(visitDate: Int, orders: List<MenuDTO>): Int {
            var discount = 0

            if (isVisitWeekend(visitDate)) discount = DISCOUNT_BASIC * countMainFood(orders)

            return discount
        }

        private fun countMainFood(orders: List<MenuDTO>): Int {
            val countMainFood = findMainFood(orders)

            return countMainFood.sumOf { it.count }
        }

        private fun findMainFood(orders: List<MenuDTO>): List<MenuDTO> =
            orders.filter { it.category == DISCOUNT_CATEGORY }

        private fun isVisitWeekend(visitDate: Int): Boolean = visitDate % 7 == 1 || visitDate % 7 == 2
    }

    object Present {
        private const val DISCOUNT_CHAMPAGNE: Int = 25_000
        private const val DISCOUNT_PRICE_CONDITION: Int = 120_000

        fun canGetChampagne(preOrderPayment: Int): Boolean = preOrderPayment >= DISCOUNT_PRICE_CONDITION

        fun getPresentDiscount(): Int = DISCOUNT_CHAMPAGNE
    }

    object Christmas {
        private const val DISCOUNT_FIRST_DAY: Int = 1_000
        private const val CHRISTMAS_DAY: Int = 25

        fun getDiscount(visitDate: Int): Int {
            var discount = 0

            if (isVisitChristmasDay(visitDate))
                discount = DISCOUNT_FIRST_DAY + ((CHRISTMAS_DAY - visitDate) * 100)

            return discount
        }

        private fun isVisitChristmasDay(visitDate: Int): Boolean = visitDate <= CHRISTMAS_DAY
    }

    object Special {
        private const val DISCOUNT_SPECIAL_DAY: Int = 1_000

        fun getDiscount(visitDate: Int): Int {
            var discount = 0

            if (isVisitSpecialDay(visitDate)) discount = DISCOUNT_SPECIAL_DAY

            return discount
        }

        private fun isVisitSpecialDay(visitDate: Int): Boolean = visitDate % 7 == 3 || visitDate == 25
    }

    object Badge {
        private const val EVENT_BADGE_RANGE_0: Int = 0
        private const val EVENT_BADGE_RANGE_5_000: Int = 5_000
        private const val EVENT_BADGE_RANGE_10_000: Int = 10_000
        private const val EVENT_BADGE_RANGE_20_000: Int = 20_000

        fun getBadge(totalDiscount: Int): String = when (totalDiscount) {
            in EVENT_BADGE_RANGE_0 until EVENT_BADGE_RANGE_5_000 -> NewYearBadge.NOTHING.badgeName
            in EVENT_BADGE_RANGE_5_000 until EVENT_BADGE_RANGE_10_000 -> NewYearBadge.STAR.badgeName
            in EVENT_BADGE_RANGE_10_000 until EVENT_BADGE_RANGE_20_000 -> NewYearBadge.TREE.badgeName
            else -> NewYearBadge.SANTA.badgeName
        }
    }

    companion object {
        private const val DISCOUNT_BASIC: Int = 2_023
    }
}