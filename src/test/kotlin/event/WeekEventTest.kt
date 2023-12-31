package event

import christmas.data.domain.Event
import christmas.data.domain.Order
import christmas.data.domain.Payment
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals

class WeekEventTest {
    @Test
    fun `크리스마스 디데이 이벤트 기간 외 평일 방문한 경우, 디저트 메뉴 1개당 2_023원 할인을 받는다`() {
        val visitDate = 31
        val orders = mapOf(
            "티본스테이크" to 1,
            "초코케이크" to 1
        )

        val order = Order(orders).createOrder()
        val discountAmount = Event.Weekday.discount(visitDate, order)
        val payment = Payment(order)
        val result = payment.getPreviousOrderPayment() - discountAmount

        assertEquals(67_977, result)
    }

    @Test
    fun `크리스마스 디데이 이벤트 기간 외 주말 방문한 경우, 메인 메뉴 1개당 2_023원 할인을 받는다`() {
        val visitDate = 29
        val orders = mapOf(
            "티본스테이크" to 1,
            "바비큐립" to 2,
            "아이스크림" to 1
        )

        val order = Order(orders).createOrder()
        val discount = Event.Weekend.discount(visitDate, order)
        val result = Payment(order).getPreviousOrderPayment() - discount

        assertEquals(161_931, result)
    }
}