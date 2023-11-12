package christmas

import christmas.dto.MenuDTO

sealed class Event {
    object Weekday {
        fun discount(orders: List<MenuDTO>): Int {
            return DISCOUNT_BASIC * countDessert(orders)
        }

        private fun countDessert(orders: List<MenuDTO>): Int {
            var count = 0

            if (hasDessert(orders)) count = orders.count { it.foodName == "초코케이크" || it.foodName == "아이스크림" }

            return count
        }

        private fun hasDessert(orders: List<MenuDTO>): Boolean = orders.any { it.foodName == "초코케이크" || it.foodName == "아이스크림" }
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

    companion object {
        private const val DISCOUNT_BASIC: Int = 2_023
    }
}