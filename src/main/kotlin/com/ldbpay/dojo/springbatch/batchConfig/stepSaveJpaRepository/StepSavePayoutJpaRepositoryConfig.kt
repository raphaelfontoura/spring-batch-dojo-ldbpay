package com.ldbpay.dojo.springbatch.batchConfig.stepSaveJpaRepository

import com.ldbpay.dojo.springbatch.batchConfig.PayoutItemProcessor
import com.ldbpay.dojo.springbatch.dto.PayoutInput
import com.ldbpay.dojo.springbatch.model.Payout
import jakarta.persistence.EntityManagerFactory
import org.springframework.batch.core.Step
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.step.builder.StepBuilder
import org.springframework.batch.item.database.JpaItemWriter
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder
import org.springframework.batch.item.file.FlatFileItemReader
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.PlatformTransactionManager

@Configuration
class StepSavePayoutJpaRepositoryConfig(
    private val entityManagerFactory: EntityManagerFactory
) {
    @Bean
    fun stepSavePayoutJpaRepository(
        jobRepository: JobRepository,
        transactionManager: PlatformTransactionManager,
        reader: FlatFileItemReader<PayoutInput>,
        processor: PayoutItemProcessor,
        writer: JpaItemWriter<Payout>
    ): Step {
        return StepBuilder("stepSavePayoutJpaRepository", jobRepository)
            .chunk<PayoutInput, Payout>(5, transactionManager)
            .reader(reader)
            .processor(processor)
            .writer(writer)
            .build()
    }

    @Bean
    fun writerJpa(): JpaItemWriter<Payout> {
        return JpaItemWriterBuilder<Payout>()
            .entityManagerFactory(entityManagerFactory)
            .build()
    }
}
