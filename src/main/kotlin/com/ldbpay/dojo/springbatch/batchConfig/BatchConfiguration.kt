package com.ldbpay.dojo.springbatch.batchConfig

import com.ldbpay.dojo.springbatch.dto.PayoutInput
import com.ldbpay.dojo.springbatch.model.Payout
import jakarta.persistence.EntityManagerFactory
import javax.sql.DataSource
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.job.builder.JobBuilder
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.step.builder.SimpleStepBuilder
import org.springframework.batch.core.step.builder.StepBuilder
import org.springframework.batch.item.ItemWriter
import org.springframework.batch.item.database.JdbcBatchItemWriter
import org.springframework.batch.item.database.JpaItemWriter
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder
import org.springframework.batch.item.file.FlatFileItemReader
import org.springframework.batch.item.file.MultiResourceItemReader
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder
import org.springframework.batch.item.file.builder.MultiResourceItemReaderBuilder
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ClassPathResource
import org.springframework.transaction.PlatformTransactionManager

@Configuration
class BatchConfiguration {

    @Bean
    fun processor(): PayoutItemProcessor {
        return PayoutItemProcessor()
    }

    @Bean
    fun writerJdbc(datasource: DataSource): JdbcBatchItemWriter<Payout> {
        return JdbcBatchItemWriterBuilder<Payout>()
            .sql("INSERT INTO payouts (id, amount_value, type, psp) VALUES(:id, :value, :type.name, :psp)")
            .dataSource(datasource)
            .beanMapped()
            .build()
    }

    @Bean
    fun job(
        jobRepository: JobRepository,
        @Qualifier("stepSavePayoutJpaRepository") step1: Step,
        @Qualifier("stepPrintLogPayout") step2: Step,
        listener: JobCompletionNotificationListener
    ) : Job {
        return JobBuilder("jobPayout", jobRepository)
            .start(step1)
            .next(step2)
            .listener(listener)
            .build()
    }

}
