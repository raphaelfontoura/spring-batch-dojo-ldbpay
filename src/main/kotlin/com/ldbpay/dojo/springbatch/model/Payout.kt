package com.ldbpay.dojo.springbatch.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.UUID
import org.apache.logging.log4j.util.Strings

@Entity
@Table(name = "payouts")
data class Payout(
    @Id
    val id: UUID = UUID.randomUUID(),
    @Column(name = "amount_value")
    val value: Long = 0,
    @Enumerated(EnumType.STRING)
    val type: AccountTypeEnum = AccountTypeEnum.CC,
    val psp: String = Strings.EMPTY
)

enum class AccountTypeEnum{
    CC,
    CP
}