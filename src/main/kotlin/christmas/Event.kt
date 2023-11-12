package christmas

import christmas.dto.MenuDTO

sealed class Event {
    object Weekday {
        fun discount(orders: List<MenuDTO>): Int {
            return DISCOUNT_BASIC * countDessert(orders)
        }

        private fun countDessert(orders: List<MenuDTO>): Int {
            val countDessert = findDessert(orders)
            return countDessert.sumOf { it.count }
        }

        private fun findDessert(orders: List<MenuDTO>): List<MenuDTO> {
            return orders.filter { it.category == "디저트" }
        }
    }

    object Weekend {
        fun discount(orders: List<MenuDTO>): Int {
            return DISCOUNT_BASIC * countMainFood(orders)
        }

        private fun countMainFood(orders: List<MenuDTO>): Int {
            val countMainFood = findMainFood(orders)
            return countMainFood.sumOf { it.count }
        }

        private fun findMainFood(orders: List<MenuDTO>): List<MenuDTO> {
            return orders.filter { it.category == "메인" }
        }
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