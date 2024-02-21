package com.ldbpay.dojo.springbatch.controller

import com.ldbpay.dojo.springbatch.infra.PayoutRepository
import com.ldbpay.dojo.springbatch.model.Payout
import com.ldbpay.dojo.springbatch.service.BatchJobService
import org.springframework.http.ResponseEntity
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile


@RestController
@RequestMapping("payout")
class PayoutController(
    private val payoutRepository: PayoutRepository,
    private val batchJobService: BatchJobService
) {

    @GetMapping
    @Transactional(readOnly = true)
    fun getAll(): ResponseEntity<List<Payout>> {
        return ResponseEntity.ok(payoutRepository.findAll())
    }

    @GetMapping("count")
    fun getCount(): ResponseEntity<Long> {
        return ResponseEntity.ok(payoutRepository.count())
    }

    @GetMapping("execute/{name}")
    fun loadFileName(@PathVariable name: String): ResponseEntity<Unit> {
        batchJobService.run(name)
        return ResponseEntity.ok().build()
    }

    @PostMapping
    fun handleFileUpload(
        @RequestParam("file") file: MultipartFile
    ): ResponseEntity<String> {
        try {
            batchJobService.store(file)
            return ResponseEntity.ok("Arquivo processado")
        } catch (ex: Exception) {
            return ResponseEntity.badRequest().body(ex.message)
        }
    }
}