package com.ldbpay.dojo.springbatch.infra

import com.ldbpay.dojo.springbatch.model.Payout
import java.util.UUID
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PayoutRepository: JpaRepository<Payout, UUID> {
}