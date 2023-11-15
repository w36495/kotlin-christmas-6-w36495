package christmas.view

import camp.nextstep.edu.missionutils.Console

class InputView {
    fun readVisitDate(): Int {
        val date = readCustomerInput()
        checkVisitDate(date)

        return date.toInt()
    }

    fun readOrderMenu(): List<String> {
        val order = readCustomerInput()
        checkMenu(order)

        return order.split(DELIMITER_COMMA)
    }

    fun closeInput() = Console.close()

    private fun checkVisitDate(date: String) {
        require(date.trim().toIntOrNull() != null) {
            ERROR_DATE_INVALID
        }
        require(date.toInt() in 1..31) {
            ERROR_DATE_LIMIT_RANGE
        }
    }

    private fun checkMenu(orderMenu: String) {
        require(orderMenu.contains(DELIMITER_COMMA)) {
            ERROR_MENU_INVALID
        }
    }

    private fun readCustomerInput(): String = Console.readLine()

    companion object {
        private const val DELIMITER_COMMA: String = ","

        private const val ERROR_DATE_INVALID: String = "[ERROR] 유효하지 않은 날짜입니다. 다시 입력해 주세요."
        private const val ERROR_DATE_LIMIT_RANGE: String = "[ERROR] 1 이상 31 이하의 숫자만 입력 가능합니다."

        private const val ERROR_MENU_INVALID: String = "[ERROR] 유효하지 않은 주문입니다. 다시 입력해 주세요."
    }
}