package com.ldbpay.dojo.springbatch.dto

import org.apache.logging.log4j.util.Strings

data class PayoutInput(
    val value: Double,
    val type: String,
    val psp: String = Strings.EMPTY
)