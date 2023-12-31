package christmas.data.domain

import christmas.data.dto.MenuDTO

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

        private fun isVisitWeekday(visitDate: Int): Boolean =
            !(visitDate % DAY_WEEK == DAY_FRIDAY || visitDate % DAY_WEEK == DAY_SATURDAY)
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

        private fun isVisitWeekend(visitDate: Int): Boolean =
            visitDate % DAY_WEEK == DAY_FRIDAY || visitDate % DAY_WEEK == DAY_SATURDAY
    }

    object Present {
        private const val DISCOUNT_CHAMPAGNE: Int = 25_000

        fun getPresentDiscount(): Int = DISCOUNT_CHAMPAGNE
    }

    object Christmas {
        private const val DISCOUNT_FIRST_DAY: Int = 1_000

        fun getDiscount(visitDate: Int): Int {
            var discount = 0

            if (isVisitChristmasDay(visitDate))
                discount = DISCOUNT_FIRST_DAY + ((visitDate - 1) * 100)

            return discount
        }

        private fun isVisitChristmasDay(visitDate: Int): Boolean = visitDate <= DAY_CHRISTMAS
    }

    object Special {
        private const val DISCOUNT_SPECIAL_DAY: Int = 1_000

        fun getDiscount(visitDate: Int): Int {
            var discount = 0

            if (isVisitSpecialDay(visitDate)) discount = DISCOUNT_SPECIAL_DAY

            return discount
        }

        private fun isVisitSpecialDay(visitDate: Int): Boolean =
            visitDate % DAY_WEEK == DAY_SUNDAY || visitDate == DAY_CHRISTMAS
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

        private const val DAY_FRIDAY: Int = 1
        private const val DAY_SATURDAY: Int = 2
        private const val DAY_SUNDAY: Int = 3
        private const val DAY_CHRISTMAS: Int = 25
        private const val DAY_WEEK: Int = 7
    }
}