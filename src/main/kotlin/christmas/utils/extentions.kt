package christmas.utils

import java.text.DecimalFormat

fun Int.withComma(): String = DecimalFormat("#,###").format(this)

fun Int.changeNegativeNumber(): Int = this * -1