package christmas

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals

class EventTest {
    @Test
    fun `크리스마스 디데이 이벤트 기간 외 평일 방문 한 경우, 디저트 메뉴 1개당 할인을 받는다`() {
        val orders = mutableListOf(
            MenuDTO("티몬스테이크", 1),
            MenuDTO("초코케이크", 1)
        )

        val discountAmount = Event.Weekday.discount(orders)
        val result = 55000 + 15000 - discountAmount

        assertEquals(67_977, result)
    }
}