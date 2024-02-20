package com.ldbpay.dojo.springbatch

import com.ldbpay.dojo.springbatch.infra.PayoutRepository
import com.ldbpay.dojo.springbatch.model.Payout
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("payout")
class PayoutController(
    private val payoutRepository: PayoutRepository
) {

    @GetMapping
    @Transactional(readOnly = true)
    fun getAll(): List<Payout> {
        return payoutRepository.findAll()
    }
}