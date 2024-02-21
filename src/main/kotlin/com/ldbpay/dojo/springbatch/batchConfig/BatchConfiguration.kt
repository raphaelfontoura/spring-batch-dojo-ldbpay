package com.ldbpay.dojo.springbatch.batchConfig

import com.ldbpay.dojo.springbatch.model.Payout
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.job.builder.JobBuilder
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.item.database.JdbcBatchItemWriter
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.sql.DataSource

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
