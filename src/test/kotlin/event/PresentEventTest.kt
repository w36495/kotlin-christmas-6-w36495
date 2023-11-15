package event

import christmas.data.domain.Discount
import christmas.data.domain.Order
import christmas.data.domain.Payment
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class PresentEventTest {
    @Test
    fun `할인 전 총 주문 금액이 12만 원 미만인 경우, 샴페인 1개 증정이 불가능하다`() {
        val orderOfCustomer = mapOf(
            "티본스테이크" to 1
        )

        val order = Order(orderOfCustomer).createOrder()
        val preOrderPayment = Payment(order).getPreviousOrderPayment()
        val result = Discount().canGetPresent(preOrderPayment)

        Assertions.assertEquals(false, result)
    }

    @Test
    fun `할인 전 총 주문 금액이 12만 원 이상인 경우, 샴페인 1개를 증정한다`() {
        val orderOfCustomer = mapOf(
            "티본스테이크" to 3
        )

        val order = Order(orderOfCustomer).createOrder()
        val preOrderPayment = Payment(order).getPreviousOrderPayment()
        val result = Discount().canGetPresent(preOrderPayment)

        Assertions.assertEquals(true, result)
    }
}