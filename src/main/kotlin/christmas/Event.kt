package christmas

import christmas.dto.MenuDTO

sealed class Event {
    object Weekday {
        private const val DISCOUNT: Int = 2_023

        fun discount(orders: List<MenuDTO>): Int {
            return DISCOUNT * countDessert(orders)
        }

        private fun countDessert(orders: List<MenuDTO>): Int {
            var count = 0

            if (hasDessert(orders)) count = orders.count { it.foodName == "초코케이크" || it.foodName == "아이스크림" }

            return count
        }

        private fun hasDessert(orders: List<MenuDTO>): Boolean = orders.any { it.foodName == "초코케이크" || it.foodName == "아이스크림" }
    }

    companion object {
        private const val DISCOUNT_BASIC: Int = 2_023
    }
}