package christmas.data.domain

import christmas.data.dto.MenuDTO

class Payment(private val orders: List<MenuDTO>) {
    fun getPreviousOrderPayment(): Int {
        return orders.sumOf { it.count * it.price }
    }

    fun getTotalPayment(previousPrice: Int, discountPrice: Int): Int = previousPrice - discountPrice
}