package christmas

import christmas.domain.Menu
import christmas.dto.MenuDTO

class Payment(private val orders: List<MenuDTO>) {
    fun getPreviousOrderPayment(): Int {
        val orderFoodNames = orders.map { order -> order.foodName }
        val orderMenuInfo = Menu.entries.filter { orderFoodNames.contains(it.foodName) }

        return orderMenuInfo.sumOf { it.price }
    }
}