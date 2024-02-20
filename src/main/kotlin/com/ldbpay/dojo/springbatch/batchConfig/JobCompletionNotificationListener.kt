package com.ldbpay.dojo.springbatch.batchConfig

import org.slf4j.LoggerFactory
import org.springframework.batch.core.BatchStatus
import org.springframework.batch.core.JobExecution
import org.springframework.batch.core.JobExecutionListener
import org.springframework.jdbc.core.DataClassRowMapper
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Component


@Component
class JobCompletionNotificationListener() : JobExecutionListener{

    companion object {
        private val log = LoggerFactory.getLogger(this::class.java)
    }


    override fun afterJob(jobExecution: JobExecution) {
        if (jobExecution.status == BatchStatus.COMPLETED) {
            log.info("!!! JOB FINISHED! Time to verify the results")
        }
    }
}