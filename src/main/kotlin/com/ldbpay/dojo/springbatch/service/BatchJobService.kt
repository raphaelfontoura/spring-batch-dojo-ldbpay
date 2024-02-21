package com.ldbpay.dojo.springbatch.service

import com.ldbpay.dojo.springbatch.batchConfig.JobCompletionNotificationListener
import com.ldbpay.dojo.springbatch.batchConfig.PayoutItemProcessor
import com.ldbpay.dojo.springbatch.dto.PayoutInput
import com.ldbpay.dojo.springbatch.model.Payout
import javax.sql.DataSource
import org.springframework.batch.core.Job
import org.springframework.batch.core.JobParameter
import org.springframework.batch.core.JobParameters
import org.springframework.batch.core.Step
import org.springframework.batch.core.job.builder.JobBuilder
import org.springframework.batch.core.launch.JobLauncher
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.step.builder.StepBuilder
import org.springframework.batch.item.ItemWriter
import org.springframework.batch.item.database.JdbcBatchItemWriter
import org.springframework.batch.item.database.JpaItemWriter
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder
import org.springframework.batch.item.file.FlatFileItemReader
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder
import org.springframework.context.annotation.Bean
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Service
import org.springframework.transaction.PlatformTransactionManager

@Service
class BatchJobService(
    private val job: Job,
    private val jobLauncher: JobLauncher,
    private val reader: FlatFileItemReader<PayoutInput>
) {

    fun run(fileName: String) {
        reader.setResource(ClassPathResource(fileName))
        val jobParamenter = JobParameter(fileName, String::class.java)
        val mapJobParameter = mapOf(fileName to jobParamenter)
        jobLauncher.run(job, JobParameters(mapJobParameter))
    }

}