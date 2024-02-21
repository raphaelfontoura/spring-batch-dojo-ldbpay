package com.ldbpay.dojo.springbatch.controller

import com.ldbpay.dojo.springbatch.infra.PayoutRepository
import com.ldbpay.dojo.springbatch.model.Payout
import com.ldbpay.dojo.springbatch.service.BatchJobService
import org.springframework.batch.core.Job
import org.springframework.batch.core.launch.JobLauncher
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("payout")
class PayoutController(
    private val payoutRepository: PayoutRepository,
    private val batchJobService: BatchJobService
) {

    @GetMapping
    @Transactional(readOnly = true)
    fun getAll(): List<Payout> {
        return payoutRepository.findAll()
    }

    @GetMapping("count")
    fun getCount(): Long {
        return payoutRepository.count()
    }

    @GetMapping("execute/{name}")
    fun loadFile(@PathVariable name: String) {
        batchJobService.run(name)
    }
}