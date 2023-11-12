package christmas

import christmas.dto.MenuDTO

sealed class Event {
    object Weekday {
        fun discount(visitDate: Int, orders: List<MenuDTO>): Int {
            var discount = 0

            if (isVisitWeekday(visitDate)) discount = DISCOUNT_BASIC * countDessert(orders)

            return discount
        }

        private fun countDessert(orders: List<MenuDTO>): Int {
            val countDessert = findDessert(orders)
            return countDessert.sumOf { it.count }
        }

        private fun findDessert(orders: List<MenuDTO>): List<MenuDTO> {
            return orders.filter { it.category == "디저트" }
        }

        private fun isVisitWeekday(visitDate: Int): Boolean = !(visitDate % 7 == 1 || visitDate % 7 == 2)
    }

    object Weekend {
        fun discount(visitDate: Int, orders: List<MenuDTO>): Int {
            var discount = 0

            if (isVisitWeekend(visitDate)) discount = DISCOUNT_BASIC * countMainFood(orders)

            return discount
        }

        private fun countMainFood(orders: List<MenuDTO>): Int {
            val countMainFood = findMainFood(orders)
            return countMainFood.sumOf { it.count }
        }

        private fun findMainFood(orders: List<MenuDTO>): List<MenuDTO> {
            return orders.filter { it.category == "메인" }
        }

        private fun isVisitWeekend(visitDate: Int): Boolean = visitDate % 7 == 1 || visitDate % 7 == 2
    }

    object Present {
        fun canGetChampagne(preOrderPayment: Int): Boolean = preOrderPayment >= 120_000
    }

    object Christmas {
        private const val DISCOUNT_FIRST_DAY: Int = 1_000
        private const val CHRISTMAS_DAY: Int = 25

        fun getDiscount(visitDate: Int): Int = DISCOUNT_FIRST_DAY + ((CHRISTMAS_DAY - visitDate) * 100)
    }

    companion object {
        private const val DISCOUNT_BASIC: Int = 2_023
    }
}