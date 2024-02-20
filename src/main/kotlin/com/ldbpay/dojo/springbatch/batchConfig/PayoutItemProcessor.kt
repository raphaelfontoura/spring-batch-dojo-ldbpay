package com.ldbpay.dojo.springbatch.batchConfig

import com.ldbpay.dojo.springbatch.model.Payout
import org.slf4j.LoggerFactory
import org.springframework.batch.item.ItemProcessor

class PayoutItemProcessor: ItemProcessor<Payout, Payout> {

    companion object {
        private val logger = LoggerFactory.getLogger(this::class.java)
    }


    override fun process(item: Payout): Payout {
        logger.info("Passando no item processor com item: {}",item)
        return Payout(
            value = (item.value / 100),
            type = item.type,
            psp = item.psp
        )
    }

}