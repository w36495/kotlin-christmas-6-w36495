package event

import NewYearBadge
import christmas.Event
import christmas.Order
import christmas.Payment
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class BadgeEventTest {
    @Test
    fun `총 혜택 금액이 5천 원 미만일 경우, 이벤트 배지를 받지 못한다`() {
        val visitDate = 30
        val orderOfCustomer = mapOf(
            "타파스" to 2,
            "초코케이크" to 3,
            "제로콜라" to 1
        )

        val order = Order().createOrder(orderOfCustomer)
        val payment = Payment(order)
        val weekdayDiscount = Event.Weekday.discount(visitDate, order)
        val weekendDiscount = Event.Weekend.discount(visitDate, order)
        val christmasDiscount = Event.Christmas.getDiscount(visitDate)
        val specialDiscount = Event.Special.getDiscount(visitDate)
        var presentDiscount = 0
        if (Event.Present.canGetChampagne(payment.getPreviousOrderPayment()))
            presentDiscount = Event.Present.getPresentDiscount()
        val totalDiscount = weekdayDiscount + weekendDiscount + christmasDiscount + presentDiscount + specialDiscount

        val badge: String = when (totalDiscount) {
            in EVENT_BADGE_RANGE_0 until EVENT_BADGE_RANGE_5_000 -> NewYearBadge.NOTHING.badgeName
            in EVENT_BADGE_RANGE_5_000 until EVENT_BADGE_RANGE_10_000 -> NewYearBadge.STAR.badgeName
            in EVENT_BADGE_RANGE_10_000 until EVENT_BADGE_RANGE_20_000 -> NewYearBadge.TREE.badgeName
            else -> NewYearBadge.SANTA.badgeName
        }

        assertEquals(NewYearBadge.NOTHING.badgeName, badge)
    }

    @Test
    fun `총 혜택 금액이 5천 원 이상, 1만 원 미만일 경우, 별 이벤트 배지를 받는다`() {
        val visitDate = 31
        val orderOfCustomer = mapOf(
            "티본스테이크" to 1,
            "타파스" to 2,
            "초코케이크" to 2,
            "제로콜라" to 1
        )

        val order = Order().createOrder(orderOfCustomer)
        val payment = Payment(order)
        val weekdayDiscount = Event.Weekday.discount(visitDate, order)
        val weekendDiscount = Event.Weekend.discount(visitDate, order)
        val christmasDiscount = Event.Christmas.getDiscount(visitDate)
        val specialDiscount = Event.Special.getDiscount(visitDate)
        var presentDiscount = 0
        if (Event.Present.canGetChampagne(payment.getPreviousOrderPayment()))
            presentDiscount = Event.Present.getPresentDiscount()
        val totalDiscount = weekdayDiscount + weekendDiscount + christmasDiscount + presentDiscount + specialDiscount

        val badge: String = when (totalDiscount) {
            in EVENT_BADGE_RANGE_0 until EVENT_BADGE_RANGE_5_000 -> NewYearBadge.NOTHING.badgeName
            in EVENT_BADGE_RANGE_5_000 until EVENT_BADGE_RANGE_10_000 -> NewYearBadge.STAR.badgeName
            in EVENT_BADGE_RANGE_10_000 until EVENT_BADGE_RANGE_20_000 -> NewYearBadge.TREE.badgeName
            else -> NewYearBadge.SANTA.badgeName
        }

        assertEquals(NewYearBadge.STAR.badgeName, badge)
    }

    @Test
    fun `총 혜택 금액이 1만 원 이상, 2만 원 이하일 경우, 트리 이벤트 배지를 받는다`() {
        val visitDate = 3
        val orderOfCustomer = mapOf(
            "티본스테이크" to 1,
            "타파스" to 2,
            "초코케이크" to 3,
            "제로콜라" to 1
        )

        val order = Order().createOrder(orderOfCustomer)
        val payment = Payment(order)
        val weekdayDiscount = Event.Weekday.discount(visitDate, order)
        val weekendDiscount = Event.Weekend.discount(visitDate, order)
        val christmasDiscount = Event.Christmas.getDiscount(visitDate)
        val specialDiscount = Event.Special.getDiscount(visitDate)
        var presentDiscount = 0
        if (Event.Present.canGetChampagne(payment.getPreviousOrderPayment()))
            presentDiscount = Event.Present.getPresentDiscount()
        val totalDiscount = weekdayDiscount + weekendDiscount + christmasDiscount + presentDiscount + specialDiscount

        val badge: String = when (totalDiscount) {
            in EVENT_BADGE_RANGE_0 until EVENT_BADGE_RANGE_5_000 -> NewYearBadge.NOTHING.badgeName
            in EVENT_BADGE_RANGE_5_000 until EVENT_BADGE_RANGE_10_000 -> NewYearBadge.STAR.badgeName
            in EVENT_BADGE_RANGE_10_000 until EVENT_BADGE_RANGE_20_000 -> NewYearBadge.TREE.badgeName
            else -> NewYearBadge.SANTA.badgeName
        }

        assertEquals(NewYearBadge.TREE.badgeName, badge)
    }

    @Test
    fun `총 혜택 금액이 2만 원 이상일 경우, 산타 이벤트 배지를 받는다`() {
        val visitDate = 3
        val orderOfCustomer = mapOf(
            "티본스테이크" to 1,
            "타파스" to 2,
            "초코케이크" to 5,
            "제로콜라" to 1
        )

        val order = Order().createOrder(orderOfCustomer)
        val payment = Payment(order)
        val weekdayDiscount = Event.Weekday.discount(visitDate, order)
        val weekendDiscount = Event.Weekend.discount(visitDate, order)
        val christmasDiscount = Event.Christmas.getDiscount(visitDate)
        val specialDiscount = Event.Special.getDiscount(visitDate)
        var presentDiscount = 0
        if (Event.Present.canGetChampagne(payment.getPreviousOrderPayment()))
            presentDiscount = Event.Present.getPresentDiscount()
        val totalDiscount = weekdayDiscount + weekendDiscount + christmasDiscount + presentDiscount + specialDiscount

        val badge: String = when (totalDiscount) {
            in EVENT_BADGE_RANGE_0 until EVENT_BADGE_RANGE_5_000 -> NewYearBadge.NOTHING.badgeName
            in EVENT_BADGE_RANGE_5_000 until EVENT_BADGE_RANGE_10_000 -> NewYearBadge.STAR.badgeName
            in EVENT_BADGE_RANGE_10_000 until EVENT_BADGE_RANGE_20_000 -> NewYearBadge.TREE.badgeName
            else -> NewYearBadge.SANTA.badgeName
        }

        assertEquals(NewYearBadge.SANTA.badgeName, badge)
    }

    companion object {
        private const val EVENT_BADGE_RANGE_0: Int = 0
        private const val EVENT_BADGE_RANGE_5_000: Int = 5_000
        private const val EVENT_BADGE_RANGE_10_000: Int = 10_000
        private const val EVENT_BADGE_RANGE_20_000: Int = 20_000
    }
}