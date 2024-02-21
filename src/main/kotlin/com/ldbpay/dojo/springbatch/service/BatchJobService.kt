package com.ldbpay.dojo.springbatch.service

import com.ldbpay.dojo.springbatch.dto.PayoutInput
import org.springframework.batch.core.Job
import org.springframework.batch.core.JobParameter
import org.springframework.batch.core.JobParameters
import org.springframework.batch.core.launch.JobLauncher
import org.springframework.batch.item.file.FlatFileItemReader
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

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

    fun store(file: MultipartFile) {
        reader.setResource(file.resource)
        val jobParameter = JobParameter(file.originalFilename ?: file.name, String::class.java)
        jobLauncher.run(job, JobParameters(mapOf(file.name to jobParameter)))
    }

}