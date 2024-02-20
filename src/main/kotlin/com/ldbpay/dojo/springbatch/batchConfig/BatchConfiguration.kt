package com.ldbpay.dojo.springbatch.batchConfig

import com.ldbpay.dojo.springbatch.dto.PayoutInput
import com.ldbpay.dojo.springbatch.model.Payout
import javax.sql.DataSource
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.job.builder.JobBuilder
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.step.builder.StepBuilder
import org.springframework.batch.item.database.JdbcBatchItemWriter
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder
import org.springframework.batch.item.file.FlatFileItemReader
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ClassPathResource
import org.springframework.jdbc.datasource.DataSourceTransactionManager

@Configuration
class BatchConfiguration {

    @Bean
    fun reader(): FlatFileItemReader<PayoutInput> {
        return FlatFileItemReaderBuilder<PayoutInput>()
            .name("payoutItemReader")
            .resource(ClassPathResource("payout.csv"))
            .delimited()
            .names("value","type","psp")
            .fieldSetMapper {fieldSet ->
                PayoutInput(
                    value = fieldSet.readDouble("value"),
                    type = fieldSet.readString("type"),
                    psp = fieldSet.readString("psp")
                )
            }
            .build()
    }

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
        step1: Step,
        listener: JobCompletionNotificationListener
    ) : Job {
        return JobBuilder("jobPayout", jobRepository)
            .start(step1)
            .listener(listener)
            .build()
    }

    @Bean
    fun transactionManager(datasource: DataSource): DataSourceTransactionManager {
        return DataSourceTransactionManager(datasource)
    }

    @Bean
    fun step1(
        jobRepository: JobRepository,
        transactionManager: DataSourceTransactionManager,
        reader: FlatFileItemReader<PayoutInput>,
        processor: PayoutItemProcessor,
        writer: JdbcBatchItemWriter<Payout>
    ): Step {
        return StepBuilder("step1", jobRepository)
            .chunk<PayoutInput, Payout>(4, transactionManager)
            .reader(reader)
            .processor(processor)
            .writer(writer)
            .build()
    }
}