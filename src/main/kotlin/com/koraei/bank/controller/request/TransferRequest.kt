package com.koraei.bank.controller.request

import jakarta.validation.constraints.Positive

data class TransferRequest(
    val fromAccount: String,
    val toAccount: String,
    @field:Positive
    val amount: Double,

)