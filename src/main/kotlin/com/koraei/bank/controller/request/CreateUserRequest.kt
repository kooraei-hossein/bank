package com.koraei.bank.controller.request

data class CreateUserRequest(
    var firstName: String,
    var lastName: String,
    var phone: String,
    var idCard: String

)

data class UpdateUserRequest(
    var firstName: String,
    var lastName: String,
    var phone: String,

)