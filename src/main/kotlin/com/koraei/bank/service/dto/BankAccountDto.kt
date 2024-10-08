package com.koraei.bank.service.dto

import jakarta.validation.constraints.Positive

data class BankAccountDto(
    var accountId: String,
    var accountHolderName: String,
    @field:Positive
    var balance: Double,
    var user: UserDto,

    )