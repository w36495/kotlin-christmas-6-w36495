package christmas

import christmas.dto.MenuDTO
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals

class EventTest {
    @Test
    fun `크리스마스 디데이 이벤트 기간 외 평일 방문한 경우, 디저트 메뉴 1개당 2_023원 할인을 받는다`() {
        val orders = mutableListOf(
            MenuDTO("티본스테이크", 1),
            MenuDTO("초코케이크", 1)
        )
        val discountAmount = Event.Weekday.discount(orders)
        val payment = Payment(orders)

        val result = payment.getPreviousOrderPayment() - discountAmount

        assertEquals(67_977, result)
    }
}