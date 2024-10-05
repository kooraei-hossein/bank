package com.koraei.bank.service.dto

import jakarta.validation.constraints.Positive

data class BankAccountDto(
    var accountNumber: String,
    var accountHolderName: String,
    @field:Positive
    var balance: Double,
    var user: UserDto,

)