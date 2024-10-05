package com.koraei.bank.service

interface TransactionObserver {
    fun onTransaction(accountId: String, transactionType: String, amount: Double)
}