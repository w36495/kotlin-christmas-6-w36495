package christmas

import christmas.domain.Menu
import christmas.dto.MenuDTO

class Order(private val orders: Map<String, Int>) {
    fun createOrder(): List<MenuDTO> {
        val newOrder = mutableListOf<MenuDTO>()
        val commonFoods = Menu.entries.filter { orders.contains(it.foodName) }

        commonFoods.forEach { menu ->
            val orderedCount = orders.getValue(menu.foodName)
            newOrder.add(MenuDTO(menu.category, menu.foodName, menu.price, orderedCount))
        }

        return newOrder.toList()
    }
}