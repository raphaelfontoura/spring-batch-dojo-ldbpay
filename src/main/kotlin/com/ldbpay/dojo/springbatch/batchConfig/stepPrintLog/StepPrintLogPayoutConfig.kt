package com.ldbpay.dojo.springbatch.batchConfig.stepPrintLog

import com.ldbpay.dojo.springbatch.batchConfig.PayoutItemProcessor
import com.ldbpay.dojo.springbatch.dto.PayoutInput
import com.ldbpay.dojo.springbatch.model.Payout
import org.springframework.batch.core.Step
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.step.builder.StepBuilder
import org.springframework.batch.item.ItemWriter
import org.springframework.batch.item.file.FlatFileItemReader
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.PlatformTransactionManager

@Configuration
class StepPrintLogPayoutConfig {

    @Bean
    fun stepPrintLogPayout(
        jobRepository: JobRepository,
        transactionManager: PlatformTransactionManager,
        reader: FlatFileItemReader<PayoutInput>,
        processor: PayoutItemProcessor
    ): Step {
        return StepBuilder("stepPrintLogPayout", jobRepository)
            .chunk<PayoutInput, Payout>(5, transactionManager)
            .reader(reader)
            .processor(processor)
            .writer(writeLog())
            .build()
    }

    fun writeLog(): ItemWriter<Payout> {
        return ItemWriter { chunk ->
            println("Tamanho do chunk: " + chunk.size())
            chunk.forEach { println(it) }
        }
    }
}
