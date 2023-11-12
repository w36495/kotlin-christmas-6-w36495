package christmas

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals

class EventTest {
    @Test
    fun `크리스마스 디데이 이벤트 기간 외 평일 방문한 경우, 디저트 메뉴 1개당 2_023원 할인을 받는다`() {
        val orders = mapOf(
            "티본스테이크" to 1,
            "초코케이크" to 1
        )

        val order = Order().createOrder(orders)
        val discountAmount = Event.Weekday.discount(order)
        val payment = Payment(order)
        val result = payment.getPreviousOrderPayment() - discountAmount

        assertEquals(67_977, result)
    }

    @Test
    fun `크리스마스 디데이 이벤트 기간 외 주말 방문한 경우, 메인 메뉴 1개당 2_023원 할인을 받는다`() {
        val orders = mapOf(
            "티본스테이크" to 1,
            "바비큐립" to 2,
            "아이스크림" to 1
        )

        val order = Order().createOrder(orders)
        val discount = Event.Weekend.discount(order)
        val result = Payment(order).getPreviousOrderPayment() - discount

        assertEquals(161_931, result)
    }

    @Test
    fun `할인 전 총 주문 금액이 12만 원 미만인 경우, 샴페인 1개 증정이 불가능하다`() {
        val orderOfCustomer = mapOf(
            "티본스테이크" to 1
        )

        val order = Order().createOrder(orderOfCustomer)
        val preOrderPayment = Payment(order).getPreviousOrderPayment()
        val result = Event.Present.canGetChampagne(preOrderPayment)

        assertEquals(false, result)
    }

    @Test
    fun `할인 전 총 주문 금액이 12만 원 이상인 경우, 샴페인 1개를 증정한다`() {
        val orderOfCustomer = mapOf(
            "티본스테이크" to 3
        )

        val order = Order().createOrder(orderOfCustomer)
        val preOrderPayment = Payment(order).getPreviousOrderPayment()
        val result = Event.Present.canGetChampagne(preOrderPayment)

        assertEquals(true, result)
    }

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
        val weekdayDiscount = Event.Weekday.discount(order)
        val result = payment.getPreviousOrderPayment() - (christmasDiscount + weekdayDiscount)

        assertEquals(91_854, result)
    }

    @Test
    fun `크리스마스 디데이 기간 내 주말에 방문한 경우, 메인 메뉴 1개당 2_023원의 할인과 크리스마스 할인을 받는다`() {
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
        val weekendDiscount = Event.Weekend.discount(order)
        val result = payment.getPreviousOrderPayment() - (christmasDiscount + weekendDiscount)

        assertEquals(93_877, result)
    }
}