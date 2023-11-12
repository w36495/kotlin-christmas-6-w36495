package event

import christmas.Event
import christmas.Order
import christmas.Payment
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class SpecialEventTest {
    @Test
    fun `특별 할인 기간 내 평일에 방문한 경우, 디저트 메뉴 1개당 2_023원의 할인과 1_000원의 특별 할인을 받는다`() {
        val visitDate = 31
        val orderOfCustomer = mapOf(
            "티본스테이크" to 1,
            "타파스" to 2,
            "초코케이크" to 2,
            "제로콜라" to 1
        )

        val order = Order().createOrder(orderOfCustomer)
        val payment = Payment(order)
        val specialDiscount = Event.Special.getDiscount(visitDate)
        val weekdayDiscount = Event.Weekday.discount(visitDate, order)
        val result = payment.getPreviousOrderPayment() - (specialDiscount + weekdayDiscount)

        Assertions.assertEquals(93_954, result)
    }
}