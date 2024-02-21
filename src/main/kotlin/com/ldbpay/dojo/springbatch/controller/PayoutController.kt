package com.ldbpay.dojo.springbatch.controller

import com.ldbpay.dojo.springbatch.infra.PayoutRepository
import com.ldbpay.dojo.springbatch.model.Payout
import com.ldbpay.dojo.springbatch.service.BatchJobService
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.servlet.mvc.support.RedirectAttributes


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
    fun loadFileName(@PathVariable name: String) {
        batchJobService.run(name)
    }

    @PostMapping
    fun handleFileUpload(
        @RequestParam("file") file: MultipartFile,
        redirectAttributes: RedirectAttributes
    ): String {
        batchJobService.store(file)
        redirectAttributes.addFlashAttribute(
            "message",
            "You successfully uploaded " + file.originalFilename + "!"
        )

        return "redirect:/"
    }
}