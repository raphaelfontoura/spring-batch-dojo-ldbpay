package com.ldbpay.dojo.springbatch.batchConfig.readerPayoutInput

import com.ldbpay.dojo.springbatch.dto.PayoutInput
import org.springframework.batch.item.file.FlatFileItemReader
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ClassPathResource

@Configuration
class ItemReaderPayoutInputConfig {
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
            }.build()
    }
}
