package christmas

import christmas.domain.Menu
import christmas.dto.MenuDTO

class Payment(private val orders: List<MenuDTO>) {
    fun getPreviousOrderPayment(): Int {
        return orders.sumOf { it.count * it.price }
    }
}