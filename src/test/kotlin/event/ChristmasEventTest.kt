package event

import christmas.Event
import christmas.Order
import christmas.Payment
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class ChristmasEventTest {
    @Test
    fun `크리스마스 디데이 기간 내 평일에 방문한 경우, 디저트 메뉴 1개당 2_023원의 할인과 크리스마스 할인을 받는다`() {
        val visitDate = 4
        val orderOfCustomer = mapOf(
            "티본스테이크" to 1,
            "타파스" to 2,
            "초코케이크" to 2,
            "제로콜라" to 1
        )

        val order = Order().createOrder(orderOfCustomer)
        val payment = Payment(order)
        val christmasDiscount = Event.Christmas.getDiscount(visitDate)
        val weekdayDiscount = Event.Weekday.discount(visitDate, order)
        val result = payment.getPreviousOrderPayment() - (christmasDiscount + weekdayDiscount)

        Assertions.assertEquals(91_854, result)
    }

    @Test
    fun `크리스마스 디데이 기간 내 주말에 방문한 경우, 메인 메뉴 1개당 2_023원의 할인과 크리스마스 할인을 받는다`() {
        val visitDate = 1
        val orderOfCustomer = mapOf(
            "티본스테이크" to 1,
            "타파스" to 2,
            "초코케이크" to 2,
            "제로콜라" to 1
        )

        val order = Order().createOrder(orderOfCustomer)
        val payment = Payment(order)
        val christmasDiscount = Event.Christmas.getDiscount(visitDate)
        val weekendDiscount = Event.Weekend.discount(visitDate, order)
        val result = payment.getPreviousOrderPayment() - (christmasDiscount + weekendDiscount)

        Assertions.assertEquals(93_577, result)
    }
}