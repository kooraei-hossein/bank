package com.koraei.bank.service

interface TransactionObserver {
    fun onTransaction(accountNumber: String, transactionType: String, amount: Double)
}