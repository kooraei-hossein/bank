package com.koraei.bank.controller.request

import jakarta.validation.constraints.Positive

data class CreateAccountRequest(
    val accountHolderName: String,
    @field:Positive
    val balance: Double,
    val userId: Long,

)

data class UpdateBalanceRequest(
    var accountNumber: String,
    @field:Positive
    var amount: Double,

)