package christmas.view

import christmas.utils.changeNegativeNumber
import christmas.data.dto.MenuDTO
import christmas.utils.withComma

class OutputView {
    fun printIntro() = println("안녕하세요! 우테코 식당 12월 이벤트 플래너입니다.")

    fun printQuestionVisitDate() = println("12월 중 식당 예상 방문 날짜는 언제인가요? (숫자만 입력해 주세요!)")

    fun printQuestionMenu() = println("주문하실 메뉴를 메뉴와 개수를 알려 주세요. (e.g. 해산물파스타-2,레드와인-1,초코케이크-1)")

    fun printEventPreview(visitDate: Int) = println("12월 ${visitDate}일에 우테코 식당에서 받을 이벤트 혜택 미리 보기!")

    fun printOrder(order: List<MenuDTO>) {
        println()
        println("<주문 메뉴>")

        order.forEach { menu ->
            println("${menu.foodName} ${menu.count}개")
        }
    }

    fun printPreviousPrice(previousPrice: String) {
        println()
        println("<할인 전 총주문 금액>")

        println("${previousPrice}원")
    }

    fun printPresent(hasPresent: Boolean) {
        println()
        println("<증정 메뉴>")

        if (hasPresent) {
            println("샴페인 1개")
        } else {
            println("없음")
        }
    }

    fun printDiscountDetail(discounts: Map<String, Int>) {
        println()
        println("<혜택 내역>")

        if (discounts.isEmpty()) {
            println("없음")
        } else {
            discounts.entries
                .sortedByDescending { it.value.changeNegativeNumber() }
                .forEach { (discountName, discountPrice) ->
                    println("${discountName}: ${discountPrice.changeNegativeNumber().withComma()}원")
                }
        }
    }

    fun printTotalDiscount(discount: Int) {
        println()
        println("<총혜택 금액>")

        println("${discount.changeNegativeNumber().withComma()}원")
    }

    fun printFinalPrice(finalPrice: Int) {
        println()
        println("<할인 후 예상 결제 금액>")

        println("${finalPrice.withComma()}원")
    }

    fun printBadge(badge: String) {
        println()
        println("<12월 이벤트 배지>")

        println(badge)
    }

    fun printErrorMessage(message: String) = println(message)
}