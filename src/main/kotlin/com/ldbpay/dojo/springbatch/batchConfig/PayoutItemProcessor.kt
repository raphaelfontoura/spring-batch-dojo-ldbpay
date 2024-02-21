package com.ldbpay.dojo.springbatch.batchConfig

import com.ldbpay.dojo.springbatch.dto.PayoutInput
import com.ldbpay.dojo.springbatch.model.AccountTypeEnum
import com.ldbpay.dojo.springbatch.model.Payout
import org.slf4j.LoggerFactory
import org.springframework.batch.item.ItemProcessor

class PayoutItemProcessor: ItemProcessor<PayoutInput, Payout> {

    companion object {
        private val logger = LoggerFactory.getLogger(this::class.java)
    }

    override fun process(item: PayoutInput): Payout {
        logger.info("Passando no item processor com item: {}",item)
        val payout =  Payout(
            value = (item.value * 100).toLong(),
            type = AccountTypeEnum.valueOf(item.type.uppercase()),
            psp = item.psp
        )
        logger.info("Item após processo de adaptação: {}", payout)
        return payout
    }

}